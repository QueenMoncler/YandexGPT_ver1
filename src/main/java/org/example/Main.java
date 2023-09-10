package org.example;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        System.out.println(YandexGPT("Привет"));
    }

    public static String YandexGPT(String message){
        String url = "https://llm.api.cloud.yandex.net/llm/v1alpha/instruct";
        String api = "y0_AgAAAAA1WnyhAATuwQAAAADsWPT37sz_QtHLRRmfbN87So-HAx6a6k0";
        String model = "b1gj4dvt80f08mhq882p";

        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer" + api);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" +message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine);
            }
            in.close();

            return (response.toString().split("\"content\":\"")[1].split("\"")[0].substring(4));

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}