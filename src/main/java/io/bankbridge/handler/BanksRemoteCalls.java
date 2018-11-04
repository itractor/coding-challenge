package io.bankbridge.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;

public class BanksRemoteCalls {

	private static HashMap config;
    private static OkHttpClient client;

	public static void init() throws Exception {
		config = new ObjectMapper()
				.readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), HashMap.class);
        client  = new OkHttpClient();
	}
	public static String handle(Request request, Response response){

        JSONArray jsonArray = new JSONArray();
	    config.forEach((k,v)-> {
            try {
                JSONObject jsonObject = new JSONObject(run(v.toString()));
                JSONObject filteredJsonObject = new JSONObject();
                if (jsonObject.has("name")) {
                    filteredJsonObject.put("name", jsonObject.get("name"));
                }
                if (jsonObject.has("bic")) {
                    filteredJsonObject.put("id", jsonObject.get("bic"));
                }
                //TODO what to do if bic or name does not exist in remote?
                jsonArray.put(filteredJsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

            return jsonArray.toString();
	}

    /**
     * A simple "forwarder"
     * @param url
     * @return string containing response body
     * @throws IOException
     */
    private static String run(String url) throws IOException {
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url(url)
                .build();

        com.squareup.okhttp.Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
