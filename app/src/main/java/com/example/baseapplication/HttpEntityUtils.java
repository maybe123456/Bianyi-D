package com.example.baseapplication;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpEntityUtils {

    public static JSONObject GetHttpEntity(HttpResponse response) {

        String line = null;
        JSONObject resultJsonObject = null;
        StringBuilder entityStringBuilder = new StringBuilder();
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 8 * 1024);
            while ((line = b.readLine()) != null) {
                entityStringBuilder.append(line + "/n");
            }
//利用从HttpEntity中得到的String生成JsonObject

            resultJsonObject = new JSONObject(entityStringBuilder.toString());
        } catch (JSONException e) {
// TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
// TODO 自动生成的 catch 块
            e.printStackTrace();
        }
//	System.out.println("***httpResponse.getEntity():"+resultJsonObject);

        return resultJsonObject;

    }

}