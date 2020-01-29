package com.gmail.zlotnikova.util;

public class ConvertUtil {

    private static ConvertUtil instance;

    private ConvertUtil() {
    }

    public static ConvertUtil getInstance() {
        if (instance == null) {
            instance = new ConvertUtil();
        }
        return instance;
    }

    public boolean parseBoolean(String checkboxStatus) {
        boolean status = false;
        if (checkboxStatus != null) {
            status = true;
        }
        return status;
    }

}