package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    private static  HttpURLConnection connection;
    private static URL url;
    private int status;
    public HttpRequest(){

    }
    public String getApi(String httpLink){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        //http request to open weather api
        try {
            URL url = new URL(httpLink);
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            status = connection.getResponseCode();
//            System.out.println(status);

            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        String JSONData = responseContent.toString();
        return JSONData;
    }
}
