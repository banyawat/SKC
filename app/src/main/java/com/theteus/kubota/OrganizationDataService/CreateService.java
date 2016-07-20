package com.theteus.kubota.OrganizationDataService;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by whorangester on 7/20/16.
 */
public class CreateService extends Service {
    private String entityName;
    private JSONObject JSONEntry;

    public CreateService(Context context, AsyncResponse delegate) {
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
        if(JSONEntry == null) {
            Log.e("Service", "Attempt to call service operation with no entry description");
            return null;
        }

        Request request = new Request(
                "POST",
                organizationDataPath
                        + "/"
                        + entityName
                        + "Set"
                , hostname, port
        );
        Response response = new Response();
        request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        request.setRequestProperty("Accept-Language", "en-us");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("Connection", "keep-alive");
        request.setRequestBody(JSONEntry.toString());
        try {
            request.writeMessageTo(writer);
            response.readMessageFrom(reader);
            return new JSONObject(response.getResponseBody());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateService setEntity(String entityName) {
        this.entityName = entityName;
        return this;
    }
    public CreateService setJSONEntry(JSONObject JSONEntry) {
        this.JSONEntry = JSONEntry;
        return this;
    }
}
