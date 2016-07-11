package com.theteus.kubota;

import android.support.annotation.NonNull;
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

public class NtlmConnection {
    private static final int SOCKET_TIMEOUT = 60000;

    private String protocol;
    private String hostname;
    private int port;
    private String domain;
    private String username;
    private String password;
    private String organizationDataPath;

    private boolean connectionState;
    private boolean authenticationState;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public NtlmConnection(String protocol, String hostname, int port, String domain, String username, String password, String organizationDataPath) {
        setProtocol(protocol);
        setHostname(hostname);
        setPort(port);
        setDomain(domain);
        setUsername(username);
        setPassword(password);
        setOrganizationDataPath(organizationDataPath);
    }

    public void connect() throws IOException {
        if(protocol.toLowerCase().equals("https")) {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.socket = socketFactory.createSocket(this.hostname, this.port);
        } else /* assume http */ {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(hostname, port), SOCKET_TIMEOUT);
        }
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.setConnectionState(true);
    }

    public void disconnect() throws IOException {
        if(this.socket != null) this.socket.close();
        if(this.writer != null) this.writer.close();
        if(this.reader != null) this.reader.close();
        this.setConnectionState(false);
    }

    public Response anonymousRequest() throws IOException {
        if(!connectionState) this.connect();

        Request request = new Request("GET", "/", this.hostname, this.port);
        Response response = new Response();
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        this.disconnect();

        return response;
    }

    public boolean authenticate() throws IOException {
        Type1Message type1Message;
        Type2Message type2Message;
        Type3Message type3Message;

        this.setAuthenticationState(false);

        if(!connectionState) return false;

        type1Message = new Type1Message(0x00088207, null, null);

        Request request = new Request("GET", "/", this.hostname, port);
        Response response = new Response();
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Authorization", "NTLM " + Base64.encode(type1Message.toByteArray()));
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        if(response.hasProperty("WWW-Authenticate")) {
            type2Message = new Type2Message(Base64.decode(response.getResponseProperty("WWW-Authenticate").substring(5)));
        } else return false;

        type3Message = new Type3Message(type2Message, password, domain, username, null, 0x00088205);

        request = new Request("GET", "/", this.hostname, port);
        response = new Response();
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("Authorization", "NTLM " + Base64.encode(type3Message.toByteArray()));
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        if(response.getStatusCode().equals("302")) {
            this.setAuthenticationState(true);
            return true;
        }
        else return false;
    }

    public Response create(@NonNull String entityName, @NonNull JSONObject jsonObject) throws IOException {
        if(!connectionState || !authenticationState) return null;

        Request request = new Request(
                "POST",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set"
                , this.hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestBody(jsonObject.toString());
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        return response;
    }

    public Response retrieve(@NonNull String entityName, String guid, String queryOption) throws IOException {
        if(!connectionState || !authenticationState) return null;

        Request request = new Request(
                "GET",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set"
                        + ((guid == null || guid.isEmpty()) ? "" : "(guid'" + guid + "')")
                        + "?"
                        + ((queryOption == null || queryOption.isEmpty()) ? "" : queryOption),
                this.hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        return response;
    }

    public Response update(@NonNull String entityName, @NonNull String guid, @NonNull JSONObject jsonObject) throws IOException {
        if(!connectionState || !authenticationState) return null;

        Request request = new Request(
                "POST",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set(guid'"
                        + guid
                        + "')"
                , this.hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("x-http-method", "MERGE");
        request.setRequestBody(jsonObject.toString());
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        return response;
    }

    public Response delete(@NonNull String entityName, @NonNull String guid) throws IOException {
        if(!connectionState || !authenticationState) return null;

        Request request = new Request(
                "POST",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set(guid'"
                        + guid
                        + "')"
                , this.hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("x-http-method", "DELETE");
        request.setRequestProperty("Content-Length", "0");
        request.writeMessageTo(writer);
        response.readMessageFrom(reader);

        request.log();
        response.log();

        return response;
    }

    public void setProtocol(String protocol) { this.protocol = protocol; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public void setPort(int port) { this.port = port; }
    public void setDomain(String domain) { this.domain = domain; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setOrganizationDataPath(String path) { this.organizationDataPath = path; }
    public void setConnectionState(boolean state) { this.connectionState = state; }
    public void setAuthenticationState(boolean state) { this.authenticationState = state; }

    public boolean getConnectionState() { return this.connectionState; }
    public boolean getAuthenticationState() { return this.authenticationState; }

    public class Request {
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
        }
        private void log() {
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
