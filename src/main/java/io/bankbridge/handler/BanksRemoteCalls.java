package io.bankbridge.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import io.bankbridge.model.BankModel;
import io.bankbridge.model.BankModelList;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanksRemoteCalls {

	private static HashMap config;
    public static CacheManager cacheManager;
    private static OkHttpClient client;

	public static void init() throws Exception {
		config = new ObjectMapper()
				.readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), HashMap.class);
        client  = new OkHttpClient();

        System.out.println(config.values() + "" + config.keySet());
	}
	public static String handle(Request request, Response response){

        JSONArray jsonArray = new JSONArray();
	    config.forEach((k,v)-> {
	        System.out.println(k + ""+ v);
            try {
                JSONObject jsonObject = new JSONObject(run(v.toString()));
                jsonArray.put(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

            String resultAsString = jsonArray.toString();
            return resultAsString;
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
