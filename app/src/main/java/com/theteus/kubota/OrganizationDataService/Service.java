package com.theteus.kubota.OrganizationDataService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.util.Base64;

public abstract class Service extends AsyncTask<Void, Void, JSONObject> {
    public static String DEFAULT_PROTOCOL = "https";
    public static String DEFAULT_HOSTNAME = "muses.hms-cloud.com";
    public static int DEFAULT_PORT = 444;
    public static String DEFAULT_DOMAIN = "hms-cloud";
    public static String DEFAULT_USERNAME = "administrator";
    public static String DEFAULT_PASSWORD = "pass@word1";
    public static String DEFAULT_ORGANIZATION_DATA_PATH = "/Training/XRMServices/2011/OrganizationData.svc";

    private static final int SOCKET_TIMEOUT = 60000;

    private static final String DEFAULT_PROGRESS_MESSAGE = "Processing";

    protected String protocol;
    protected String hostname;
    protected int port;
    protected String domain;
    protected String username;
    protected String password;
    protected String organizationDataPath;

    protected boolean connectionState;
    protected boolean authenticationState;

    protected Socket socket;
    protected PrintWriter writer;
    protected BufferedReader reader;

    private ProgressDialog dialog;
    private AsyncResponse delegate;
    private String progressMessage;

    public Service(Activity activity, AsyncResponse delegate) {
        this.protocol = DEFAULT_PROTOCOL;
        this.hostname = DEFAULT_HOSTNAME;
        this.port = DEFAULT_PORT;
        this.domain = DEFAULT_DOMAIN;
        this.username = DEFAULT_USERNAME;
        this.password = DEFAULT_PASSWORD;
        this.organizationDataPath = DEFAULT_ORGANIZATION_DATA_PATH;
        this.progressMessage = DEFAULT_PROGRESS_MESSAGE;

        this.dialog = new ProgressDialog(activity);
        this.delegate = delegate;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage(progressMessage);
        this.dialog.show();
    }
    @Override
    protected JSONObject doInBackground(Void... args) {
        if(!connect()) return null;
        if(!authenticate()) return null;
        JSONObject result = serviceOperation();
        disconnect();
        return result;
    }
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(delegate != null) delegate.onFinishTask(result);
    }
    private boolean connect() {
        try {
            if (protocol.toLowerCase().equals("https")) {
                SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                this.socket = socketFactory.createSocket(this.hostname, this.port);
            } else if (protocol.toLowerCase().equals("http")) {
                this.socket = new Socket();
                this.socket.connect(new InetSocketAddress(hostname, port), SOCKET_TIMEOUT);
            } else {
                this.connectionState = false;
                return false;
            }
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.connectionState = true;
        return true;
    }
    private void disconnect(){
        try {
            if (this.socket != null) this.socket.close();
            if (this.writer != null) this.writer.close();
            if (this.reader != null) this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.connectionState = false;
    }
    private boolean authenticate() {
        Type1Message type1Message;
        Type2Message type2Message;
        Type3Message type3Message;

        this.authenticationState = false;

        if(!connectionState) return false;

        type1Message = new Type1Message(0x00088207, null, null);

        Request request = new Request("GET", "/", this.hostname, port);
        Response response = new Response();
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Authorization", "NTLM " + Base64.encode(type1Message.toByteArray()));
        try {
            request.writeMessageTo(writer);
            response.readMessageFrom(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //request.log();
        //response.log();

        if(response.hasProperty("WWW-Authenticate")) {
            type2Message = null;
            try {
                type2Message = new Type2Message(Base64.decode(response.getResponseProperty("WWW-Authenticate").substring(5)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else return false;

        type3Message = new Type3Message(type2Message, password, domain, username, null, 0x00088205);

        request = new Request("GET", "/", this.hostname, port);
        response = new Response();
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("Authorization", "NTLM " + Base64.encode(type3Message.toByteArray()));
        try {
            request.writeMessageTo(writer);
            response.readMessageFrom(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //request.log();
        //response.log();

        if(response.getStatusCode().equals("302")) {
            this.authenticationState = true;
            return true;
        }
        else return false;
    }
    protected abstract JSONObject serviceOperation();

    public static void setDefaultProtocol(String protocol) { DEFAULT_PROTOCOL = protocol; }
    public static void setDefaultHostname(String hostname) { DEFAULT_HOSTNAME = hostname; }
    public static void setDefaultPort(int port) { DEFAULT_PORT = port; }
    public static void setDefaultDomain(String domain) { DEFAULT_DOMAIN = domain; }
    public static void setDefaultUsername(String username) { DEFAULT_USERNAME = username; }
    public static void setDefaultPassword(String password) { DEFAULT_PASSWORD = password; }
    public static void setDefaultOrganizationDataPath(String path) { DEFAULT_ORGANIZATION_DATA_PATH = path; }

    protected class Request {
        private String requestMethod;
        private String requestUri;
        private String protocolVersion;
        private Map<String, String> requestProperties;
        private String requestBody;

        private Request() {
            this.setProtocolVersion("HTTP/1.1");
            this.requestProperties = new HashMap<>();
            this.setRequestProperty("User-Agent", "Android Application");
        }
        public Request(String requestMethod, String uri, String hostname) {
            this();
            this.setRequestMethod(requestMethod);
            this.setRequestUri(uri);
            this.requestProperties.put("Host", hostname);
        }
        public Request(String requestMethod, String uri, String hostname, int port) {
            this();
            this.setRequestMethod(requestMethod);
            this.setRequestUri(uri);
            this.requestProperties.put("Host", hostname + ":" + port);
        }

        public void setRequestMethod(String requestMethod) { this.requestMethod = requestMethod.toUpperCase(); }
        public void setRequestUri(String uri) { this.requestUri = uri; }
        public void setProtocolVersion(String protocolVersion) { this.protocolVersion = protocolVersion; }
        public void setRequestProperty(String field, String value) { this.requestProperties.put(field, value); }
        public void setRequestBody(String body) {
            this.requestBody = body;
            this.requestProperties.put("Content-Length", String.valueOf(this.requestBody.length()));
        }

        public String getRequestMethod() { return this.requestMethod; }
        public String getRequestUri() { return this.requestUri; }
        public String getProtocolVersion() { return this.protocolVersion; }
        public Map getRequestProperties() { return this.requestProperties; }
        public boolean hasProperty(String field) { return this.requestProperties.containsKey(field); }
        public String getRequestProperty(String field) { return this.requestProperties.get(field); }
        public String getRequestBody() { return this.requestBody; }

        public void removeProperty(String field) { this.requestProperties.remove(field); }
        public void writeMessageTo(PrintWriter writer) {
            writer.println(requestMethod + " " + requestUri + " " + protocolVersion);
            Iterator it = this.requestProperties.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                writer.println(pair.getKey() + ": " + pair.getValue());
            }
            writer.println("");
            if(requestBody != null) writer.println(requestBody);
            writer.println("");
            writer.flush();
            this.log();
        }
        protected void log() {
            Log.i("HTTP REQUEST >>>", "");
            Log.i("HTTP REQUEST >>>", "===== REQUEST ===== REQUEST ===== REQUEST ===== REQUEST ===== REQUEST =====");
            Log.i("HTTP REQUEST >>>", this.requestMethod + " " + this.requestUri + " " + this.protocolVersion);
            Iterator it = this.requestProperties.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Log.i("HTTP REQUEST >>>", pair.getKey() + ": " + pair.getValue());
            }
            if(requestBody != null) Log.i("HTTP REQUEST >>>", requestBody);
            Log.i("HTTP REQUEST >>>", "===== REQUEST ===== REQUEST ===== REQUEST ===== REQUEST ===== REQUEST =====");
            Log.i("HTTP REQUEST >>>", "");
        }
    }
    public class Response {
        private String protocolVersion;
        private String statusCode;
        private String statusDescription;
        private Map<String, String> responseProperties;
        private String responseBody;

        public Response() { this.responseProperties = new HashMap<>(); }

        public String getProtocolVersion() { return this.protocolVersion; }
        public String getStatusCode() { return this.statusCode; }
        public String getStatusDescription() { return this.statusDescription; }
        public Map getResponseProperties() { return this.responseProperties; }
        public boolean hasProperty(String field) { return this.responseProperties.containsKey(field); }
        public String getResponseProperty(String field) { return this.responseProperties.get(field); }
        public String getResponseBody() { return this.responseBody; }

        public void readMessageFrom(BufferedReader reader) throws IOException {
            String line = reader.readLine();
            String item[] = line.split(" ");
            this.protocolVersion = item[0];
            this.statusCode = item[1];
            this.statusDescription = item[2];
            this.responseBody = "";

            while((line = reader.readLine()) != null && line.length() != 0) {
                if (line.isEmpty()) break;
                String[] items = line.split(": ", 2);
                this.responseProperties.put(items[0], items[1]);
            }
            if(this.responseProperties.containsKey("Content-Length") && !this.responseProperties.get("Content-Length").equals("0")) {
                int length = Integer.parseInt(this.responseProperties.get("Content-Length"));
                byte buffer[] = new byte[length];
                int j = 0;
                for(int i = 0; i < length; i++) {
                    int readChar = reader.read();
                    if(readChar != 13) {
                        buffer[j] = (byte) readChar;
                        j++;
                    }
                }
                this.responseBody = new String(buffer).substring(0, j);
            }
            this.log();
        }

        public void log() {
            Log.i("HTTP RESPONSE >>>", "");
            Log.i("HTTP RESPONSE >>>", "===== RESPONSE ===== RESPONSE ===== RESPONSE ===== RESPONSE ===== RESPONSE =====");
            Log.i("HTTP RESPONSE >>>", this.getProtocolVersion() + " " + this.getStatusCode() + " " + this.getStatusDescription());
            Iterator it = this.getResponseProperties().entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Log.i("HTTP RESPONSE >>>", pair.getKey() + ": " + pair.getValue());
            }
            if(responseBody != null) Log.i("HTTP RESPONSE >>>", responseBody);
            Log.i("HTTP RESPONSE >>>", "===== RESPONSE ===== RESPONSE ===== RESPONSE ===== RESPONSE ===== RESPONSE =====");
            Log.i("HTTP RESPONSE >>>", "");
        }
    }
}
