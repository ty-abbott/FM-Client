package com.example.familymapclient.tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.familymapclient.support.ServerProxy;

import java.io.IOException;

import requests.LoginRequest;
import responses.FamilyResponse;
import responses.LoginResponse;

public class LoginTask implements Runnable{
    private String host;
    private String port;
    private String username;
    private String password;

    private final Handler messageHandler;

    public LoginTask(Handler messageHandler, String host, String port, String username, String password){
        this.messageHandler = messageHandler;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    @Override
    public void run() {
        ServerProxy proxy = new ServerProxy(host, port);
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        try {
            LoginResponse response = proxy.login(request);
            if(response.isSuccess()){
                FamilyResponse familyResponse = proxy.getFamily(response.getAuthToken());
                if(familyResponse.isSuccess()) {
                    sendMessage(true, familyResponse.getData()[0].getFirstName(), familyResponse.getData()[0].getLastName());
                }
            }else{
                sendMessage(false, "none", "none");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(boolean success, String firstName, String lastName) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        if(success) {
            messageBundle.putString("Message", "Login succeeded: " + firstName + " " + lastName);
        }else{
            messageBundle.putString("Message", "login failed");
        }
        message.setData(messageBundle);

        messageHandler.sendMessage(message);
    }
}
