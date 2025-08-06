package com.walhalla.android.webview.viewmodel;


import android.webkit.ConsoleMessage;

import com.walhalla.android.R;
import com.walhalla.android.webview.adapter.ViewModel;

public class ConMsg implements ViewModel {

    public final String mMessage;
    public final int mLineNumber;
    public final String mSourceId;
    public final int icon;

    public ConMsg(String mMessage, int mLineNumber, String mSourceId, int icon) {
        this.mMessage = mMessage;
        this.mLineNumber = mLineNumber;
        this.mSourceId = mSourceId;
        this.icon = icon;
    }

    public static int image(ConsoleMessage.MessageLevel messageLevel) {
        if (messageLevel == ConsoleMessage.MessageLevel.TIP) {
            return R.drawable.ic_msg_tip;
        } else if (messageLevel == ConsoleMessage.MessageLevel.LOG) {
            return R.drawable.ic_msg_log;
        } else if (messageLevel == ConsoleMessage.MessageLevel.WARNING) {
            return R.drawable.ic_msg_warning;
        } else if (messageLevel == ConsoleMessage.MessageLevel.ERROR) {
            return R.drawable.ic_msg_error;
        } else if (messageLevel == ConsoleMessage.MessageLevel.DEBUG) {
            return R.drawable.ic_msg_debug;
        }
        return R.drawable.ic_msg_debug;
    }

    public static ConMsg wrap(ConsoleMessage m) {
        return new ConMsg(m.message(), m.lineNumber(), m.sourceId(), image(m.messageLevel()));
    }
}
