package com.xiaoxian.puredaily.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetWork {
    public static boolean checkConnection(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        return ni!=null&&ni.isConnected();
    }

    public static void noNetwork(Context context){
        Toast.makeText(context, "喂diu，没网你不知道？？", Toast.LENGTH_SHORT).show();
    }
}
