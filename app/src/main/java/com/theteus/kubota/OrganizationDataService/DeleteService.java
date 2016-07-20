package com.theteus.kubota.OrganizationDataService;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by whorangester on 7/20/16.
 */
public class DeleteService extends Service {
    private String entityName;
    private String guid;

    public DeleteService(Activity activity, AsyncResponse delegate) {
        super(activity, delegate);
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
        if(guid == null) {
            Log.e("Service", "Attempt to call service operation with no entry GUID");
            return null;
        }
        Request request = new Request(
                "POST",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set(guid'"
                        + guid
                        + "')"
                , hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestProperty("x-http-method", "DELETE");
        request.setRequestProperty("Content-Length", "0");
        try {
            request.writeMessageTo(writer);
            response.readMessageFrom(reader);
            return new JSONObject(response.getResponseBody());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DeleteService setEntity(String entityName) {
        this.entityName = entityName;
        return this;
    }
    public DeleteService setGuid(String guid) {
        this.guid = guid;
        return this;
    }
}
