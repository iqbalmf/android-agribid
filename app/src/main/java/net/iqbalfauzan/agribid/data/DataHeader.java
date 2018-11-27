package net.iqbalfauzan.agribid.data;

import android.content.Context;
import android.content.SharedPreferences;

import net.iqbalfauzan.agribid.Config;

public class DataHeader {
    public String getAuthHeader(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences(Config.HEADER_AUTH, 0);
        String headerAuth = preferences.getString(Config.HEADER_AUTH, null);
        if (headerAuth != null){
            return headerAuth;
        }else {
            return "";
        }
    }
    public String getIdUser(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences(Config.ID_USER, 0);
        String idUser = preferences.getString(Config.ID_USER, null);
        if (idUser != null){
            return idUser;
        }else {
            return "";
        }
    }
}
