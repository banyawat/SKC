package com.theteus.kubota.OrganizationDataService;

import org.json.JSONObject;

public interface AsyncResponse {
    void onFinishTask(JSONObject result);
}