package com.walhalla.android.webview.presenter;

import android.content.res.Resources;

import com.walhalla.android.webview.ui.activities.ManifestExplorer;
import com.walhalla.utils.DLog;

public abstract class BaseManifestPresenter implements ManifestPresenter{

    protected final ManifestExplorer mView;

    public BaseManifestPresenter(ManifestExplorer m) {
        this.mView = m;
    }

    /**
     * returns the value, resolving it through the provided resources if it
     * appears to be a resource ID. Otherwise just returns what was provided.
     *
     * @param in String to resolve
     * @param r  Context appropriate resource (system for system, package's for
     *           package)
     * @return Resolved value, either the input, or some other string.
     */
    @Override
    public String resolveValue(String name, String in, Resources r) {
        if (in == null || !in.startsWith("@") || r == null)
            return in;
        int num = 0;
        try {
            num = Integer.parseInt(in.substring(1));
            if ("theme".equals(name)) {
                String tmp = r.getResourceName(num);
                if (tmp.startsWith("android:style")) {
                    return tmp;
                } else {
                    return tmp.split("/")[1];
                }
            }
            return r.getString(num);
        } catch (NumberFormatException e) {
            return in;
        } catch (RuntimeException e) {
            // formerly noted errors here, but simply not resolving works better
            DLog.d(e.getLocalizedMessage() + " " + name + " " + r.getResourceEntryName(num));
            return in;
        }
    }
}
