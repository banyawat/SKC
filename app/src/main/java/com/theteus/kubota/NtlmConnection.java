package com.theteus.kubota;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.util.Base64;

/**
 * Created by whorangester on 7/7/16.
 */
public class NtlmConnection {
    private static final int SOCKET_TIMEOUT = 60000;

    private String protocol;
    private String hostname;
    private int port;
    private String domain;
    private String username;
    private String password;

    private boolean connectionState;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private NtlmCookie cookie;
    private Type1Message type1Message;
    private Type2Message type2Message;
    private Type3Message type3Message;

    public NtlmConnection(String protocol, String hostname, int port, String domain, String username, String password) {
        setProtocol(protocol);
        setHostname(hostname);
        setPort(port);
        setDomain(domain);
        setUsername(username);
        setPassword(password);
    }

    public void handshake() throws IOException {
        // Send Anonymous Request & Receive ReqClientId
        this.anonymousRequest();
        // Send NTLM_NEGOTIATE & Receive NTLM_CHALLENGE
        this.negotiate();
        // Send NTLM_AUTH & Receive Response
        this.authenticate();
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

    private void anonymousRequest() throws IOException {
        if(connectionState) this.connect();

        writer.println("GET / HTTP/1.1");
        writer.println("Host: " + hostname + ":" + port);
        writer.println("Accept-Encoding: gzip, deflate");
        writer.println("Accept: */*");
        writer.println("User-Agent: Java");
        writer.println("Accept-Language: en,th;q=0.8");
        writer.println("DNT: 1");
        writer.println("Connection: keep-alive");
        writer.println("");
        writer.flush();
        Log.i("HTTP MESSAGE >>>", "========== ========== ========== ========== ========== ==========");

        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) break;
            Log.i("HTTP >>>", line);
            String[] items = line.split(": ", 2);
            if (items[0].equals("Set-Cookie")) {
                cookie = new NtlmCookie(items[1]);
            }
        }

        this.disconnect();
    }

    private void negotiate() throws IOException {
        if(cookie == null) this.anonymousRequest();

        this.connect();

        type1Message = new Type1Message(0x00088207, null, null);

        writer.println("GET / HTTP/1.1");
        writer.println("Host: " + hostname + ":" + port);
        writer.println("Accept: */*");
        writer.println("Connection: keep-alive");
        writer.println("Authorization: NTLM " + Base64.encode(type1Message.toByteArray()));
        writer.println("Cookie: ReqClientId=" + cookie.getReqClientId());
        writer.println("User-Agent: Java");
        writer.println("Accept-Language: en,th;q=0.8");
        writer.println("DNT: 1");
        writer.println("Accept-Encoding: gzip, deflate");
        writer.println("");
        writer.flush();
        Log.i("HTTP MESSAGE >>>", "========== ========== ========== ========== ========== ==========");

        int contentLength = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) break;
            Log.i("HTTP >>>", line);
            String[] items = line.split(": ", 2);
            if (items[0].equals("WWW-Authenticate")) {
                type2Message = new Type2Message(Base64.decode(items[1].split("NTLM ")[1]));
            }
            if (items[0].equals("Content-Length")) contentLength = Integer.parseInt(items[1]);
        }
        char buffer[] = new char[contentLength];
        reader.read(buffer, 0, contentLength);
        //Log.i("HTTP CONTENT >>>", String.valueOf(buffer));
    }

    private void authenticate() throws IOException {
        if(type2Message == null) this.negotiate();

        type3Message = new Type3Message(type2Message, password, domain, username, null, 0x00088205);

        writer.println("GET / HTTP/1.1");
        writer.println("Host: " + hostname + ":" + port);
        writer.println("Accept: */*");
        writer.println("Connection: keep-alive");
        writer.println("Authorization: NTLM " + Base64.encode(type3Message.toByteArray()));
        writer.println("Cookie: ReqClientId=" + cookie.getReqClientId());
        writer.println("User-Agent: Java");
        writer.println("Accept-Language: en,th;q=0.8");
        writer.println("DNT: 1");
        writer.println("Accept-Encoding: gzip, deflate");
        writer.println("");
        writer.flush();
        Log.i("HTTP MESSAGE >>>", "========== ========== ========== ========== ========== ==========");

        int contentLength = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) break;
            Log.i("HTTP >>>", line);
            String[] items = line.split(": ", 2);
            if (items[0].equals("Content-Length")) contentLength = Integer.parseInt(items[1]);
        }
        char buffer[] = new char[1024];
        reader.read(buffer, 0, contentLength);
        //Log.i("HTTP CONTENT >>>", reader.readLine());

        //this.closeSocket();
    }

    public void getPath(String path) throws IOException {
        if(type3Message == null) this.connect();

        writer.println("GET " + path + " HTTP/1.1");
        writer.println("Host: " + hostname + ":" + port);
        writer.println("Content-Type: application/json; charset=utf-8");
        writer.println("Connection: keep-alive");
        //writer.println("Upgrade-Insecure-Requests: 1");
        writer.println("User-Agent: Java");
        writer.println("Accept: application/json");
        writer.println("Cookie: ReqClientId=" + cookie.getReqClientId());
        writer.println("Accept-Language: en,th;q=0.8");
        //writer.println("Accept-Encoding: gzip, deflate");
        writer.println("");
        writer.flush();
        Log.i("HTTP MESSAGE >>>", "========== ========== ========== ========== ========== ==========");

        int contentLength = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) break;
            Log.i("HTTP >>>", line);
            String[] items = line.split(": ", 2);
            if (items[0].equals("Content-Length")) contentLength = Integer.parseInt(items[1]);
        }
        //char buffer[] = new char[4096];
        //reader.read(buffer, 0, contentLength);
        for (String line; (line = reader.readLine()) != null;)
            Log.i("HTTP CONTENT >>>", line);
        //Log.i("HTTP CONTENT >>>", String.valueOf(buffer));
        //Log.i("HTTP CONTENT >>>", (int) buffer[0] + " " + (int) buffer[1] + " " + (int) buffer[2] + " " + (int) buffer[3]);
    }

    public void setProtocol(String protocol) { this.protocol = protocol; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public void setPort(int port) { this.port = port; }
    public void setDomain(String domain) { this.domain = domain; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setConnectionState(boolean state) { this.connectionState = state; }

    public NtlmCookie getCookie() { return this.cookie; }

    public class NtlmCookie {
        private String reqClientId;
        private String expires;
        private String path;

        public NtlmCookie(String setCookie) {
            String items[] = setCookie.split("; ");
            for(String item : items) {
                if(item.split("=")[0].equals("ReqClientId")) this.reqClientId = item.split("=")[1];
                else if(item.split("=")[0].equals("expires")) this.expires = item.split("=")[1];
                else if(item.split("=")[0].equals("path")) this.path = item.split("=")[1];
            }
        }

        public String getReqClientId() { return this.reqClientId; }
        public String getExpires() { return this.expires; }
        public String getPath() { return this.path; }
    }
}
