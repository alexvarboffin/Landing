package com.example.android.myapplicationbrowser.model;

import androidx.annotation.NonNull;

public class MProxy {

    public String host, user, password;
    public int port;

    public MProxy(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public static MProxy parse(String aa) {
        String[] _proxy_arr = aa.split("@");
        String str = ":";
        String[] _proxy_host = _proxy_arr[0].split(str);


        String _proxy_ip = _proxy_host[0];
        String _proxy_port = _proxy_host[1];


        if (_proxy_arr.length > 1) {
            String[] _proxy_auth = _proxy_arr[1].split(str);
            if (_proxy_auth.length > 0) {
                return new MProxy(_proxy_ip,
                        Integer.parseInt(_proxy_port), _proxy_auth[0], _proxy_auth[1]);
            }
        }
        return new MProxy(_proxy_ip,
                Integer.parseInt(_proxy_port), "", "");
    }

    @NonNull
    @Override
    public String toString() {
        return host + ":" + port + "@" + user + ":" + password;
    }
}