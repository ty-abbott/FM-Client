package com.example.familymapclient.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.LoginRequest;
import requests.RegisterRequest;
import responses.FamilyResponse;
import responses.LoginResponse;
import responses.RegisterResponse;

import com.google.gson.Gson;
public class ServerProxy {
    private String serverHost;
    private String serverPort;

    public ServerProxy(String serverHost, String serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LoginResponse login(LoginRequest request) throws IOException {
        try {
            Gson gson = new Gson();
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            String data = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(data, reqBody);
            reqBody.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream respBody = http.getInputStream();
                String resData = readString(respBody);
                LoginResponse response = gson.fromJson(resData, LoginResponse.class);
                return response;
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                System.out.println(respData);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RegisterResponse register(RegisterRequest request) throws IOException{
        try {
            Gson gson = new Gson();
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            String data = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(data, reqBody);
            reqBody.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream respBody = http.getInputStream();
                String resData = readString(respBody);
                RegisterResponse response = gson.fromJson(resData, RegisterResponse.class);
                return response;
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                System.out.println(respData);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FamilyResponse getFamily(String authtoken) throws IOException {
        try {
            Gson gson = new Gson();
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authtoken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            if(http.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream respBody = http.getInputStream();
                String resData = readString(respBody);
                FamilyResponse response = gson.fromJson(resData, FamilyResponse.class);
                return response;
            }
            else{
                System.out.println("ERROR: " + http.getResponseMessage());
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                System.out.println(respData);
                return null;
            }

        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }
    //login
    //register
    //getPeople
    //getEvents
    //for the ticket to ride stuff there is a client folder, this should show what we need to do call the
    //server from the java program
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
