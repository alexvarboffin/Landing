package com.advear.client.newweb;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class SessionConfig implements Parcelable {

    private final String url;
    private final String sessionName;
    public final int port;

    public SessionConfig(String url, String sessionName, int port) {
        this.url = url;
        this.sessionName = sessionName;
        this.port = port;
    }

    protected SessionConfig(Parcel in) {
        url = in.readString();
        sessionName = in.readString();
        port = in.readInt();
    }

    public static final Creator<SessionConfig> CREATOR = new Creator<SessionConfig>() {
        @Override
        public SessionConfig createFromParcel(Parcel in) {
            return new SessionConfig(in);
        }

        @Override
        public SessionConfig[] newArray(int size) {
            return new SessionConfig[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public String getSessionName() {
        return sessionName;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(sessionName);
        dest.writeInt(port);
    }
}
