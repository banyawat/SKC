package com.theteus.kubota.OrganizationDataService;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RetrieveService extends Service {
    private String entityName;
    private String guid;
    private String queryString;

    public RetrieveService(Context context, AsyncResponse delegate) {
        super(context, delegate);
    }

    @Override
    protected JSONObject serviceOperation() {
        if(!connectionState || !authenticationState) {
            Log.e("Service", "Connection/Authentication State is invalid.");
            return null;
        }
        if(entityName == null) {
            Log.e("Service", "Attempt to call service operation with no entity name");
            return null;
        }

        Request request = new Request(
                "GET",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set"
                        + ((guid == null || guid.isEmpty()) ? "" : "(guid'" + guid + "')")
                        + "?"
                        + ((queryString == null || queryString.isEmpty()) ? "" : queryString),
                hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        try {
            request.writeMessageTo(writer);
            response.readMessageFrom(reader);
            return new JSONObject(response.getResponseBody());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RetrieveService setEntity(String entityName) {
        this.entityName = entityName;
        return this;
    }
    public RetrieveService setGuid(String guid) {
        this.guid = guid;
        return this;
    }
    public RetrieveService setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }
}
