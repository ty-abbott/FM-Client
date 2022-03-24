package com.example.familymapclient.support;

public class DataCache {
    private static DataCache instance = new DataCache();


    public static DataCache getInstance(){
        return instance;
    }

    private DataCache(){

    }


}
