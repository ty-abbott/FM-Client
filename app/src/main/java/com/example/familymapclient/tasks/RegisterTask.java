package com.example.familymapclient.tasks;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.familymapclient.support.ServerProxy;

import java.io.IOException;

import requests.RegisterRequest;
import responses.FamilyResponse;
import responses.RegisterResponse;


public class RegisterTask implements Runnable{
    private String host;
    private String port;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    private final Handler messageHandler;

    public RegisterTask(String host, String port, String username, String password, String email,
                        String firstName, String lastName, String gender, Handler messageHandler) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        ServerProxy proxy = new ServerProxy(host, port);
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setEmail(email);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setGender(gender);
        try{
            RegisterResponse response = proxy.register(request);
            if(response.getSuccess()){
                FamilyResponse familyResponse = proxy.getFamily(response.getAuthtoken());
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
            messageBundle.putString("Message", "Register succeeded: " + firstName + " "+ lastName);
        }else{
            messageBundle.putString("Message", "Register failed");
        }
        message.setData(messageBundle);

        messageHandler.sendMessage(message);
    }
}
