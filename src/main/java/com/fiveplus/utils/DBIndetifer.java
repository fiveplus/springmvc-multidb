package com.fiveplus.utils;

public class DBIndetifer {
    private static ThreadLocal<String> dbkey = new ThreadLocal<String>();

    public static void setDBKey(final String dbKeyPara){
        dbkey.set(dbKeyPara);
    }

    public static String getDBKey(){
        return dbkey.get();
    }

}
