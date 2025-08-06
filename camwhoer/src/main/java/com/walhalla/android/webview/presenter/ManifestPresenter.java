package com.walhalla.android.webview.presenter;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.webkit.WebView;

public interface ManifestPresenter {

    String TAG_MANIFEST = "manifest";

    CharSequence getXMLText(XmlResourceParser xrp, Resources currentResources);

    void insertSpaces(StringBuffer sb, int num);

    CharSequence getAttribs(XmlResourceParser xrp, Resources currentResources);

    String resolveValue(String name, String in, Resources r);

    void loadDataWithPattern(WebView webView, String toString);

    String mOutgetText(XmlResourceParser xrp, Resources currentResources);
}
