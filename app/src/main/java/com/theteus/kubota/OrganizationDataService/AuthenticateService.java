package com.theteus.kubota.OrganizationDataService;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticateService extends Service {
    public AuthenticateService(Context context, AsyncResponse delegate) { super(context, delegate); }

    @Override
    protected JSONObject serviceOperation() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OK", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(authenticationState)
            return jsonObject;
        return null;
    }
}
