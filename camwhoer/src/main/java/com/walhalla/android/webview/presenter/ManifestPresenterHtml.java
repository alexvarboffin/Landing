package com.walhalla.android.webview.presenter;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.webkit.WebView;

import com.walhalla.android.webview.ui.activities.ManifestExplorer;
import com.walhalla.utils.DLog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ManifestPresenterHtml extends BaseManifestPresenter {


    public ManifestPresenterHtml(ManifestExplorer m) {
        super(m);
    }

    @Override
    public CharSequence getXMLText(XmlResourceParser xrp, Resources currentResources) {
        StringBuffer sb = new StringBuffer();
        //sb.append("<pre>");

        int indent0 = 0;
        try {
            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (XmlPullParser.START_TAG == eventType) {
                    if ((xrp.getName().equals(TAG_MANIFEST))) {
                        sb.append("<div class=\"folder\" id=\"folder0\">");
                    } else {

                    }
                    indent0 += 1;
                    sb.append("<div class=\"line " + xrp.getName() + "\"><span class=\"folder-button fold\"></span>");
                    insertSpaces(sb, indent0);
                    sb.append("<span class=\"html-tag_a\">&lt;" + xrp.getName() + "</span>");
                    sb.append("<span>" + getAttribs(xrp, currentResources) + "&gt;</span>");
                } else if (XmlPullParser.END_TAG == eventType) {

                    indent0 -= 1;
                    sb.append("\n");
                    insertSpaces(sb, indent0);
                    sb.append("<span class=\"html-tag_a\">&lt;/" + xrp.getName() + "&gt;</span>");
                    sb.append("</div>");

                    if ((xrp.getName().equals(TAG_MANIFEST))) {
                        sb.append("</div>");
                    } else {

                    }
                }
                switch (eventType) {


//                    case XmlPullParser.TEXT:
//                        sb.append("<span>" + xrp.getText() + "</span>");
//                        break;
//
//                    case XmlPullParser.CDSECT:
//                        sb.append("<span>" + xrp.getText() + "</span>");
//                        break;
//
//                    case XmlPullParser.PROCESSING_INSTRUCTION:
//                        sb.append("<span>" + xrp.getText() + "</span>");
//                        break;
//
//                    case XmlPullParser.COMMENT:
//                        sb.append("<!--" + xrp.getText() + "-->");
//                        break;
                }
                eventType = xrp.nextToken();
            }
        } catch (IOException ioe) {
            mView.showError("Reading XML", ioe);
        } catch (XmlPullParserException xppe) {
            mView.showError("Parsing XML", xppe);
        }
        return sb;
    }

    @Override
    public CharSequence getAttribs(XmlResourceParser xrp, Resources currentResources) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < xrp.getAttributeCount(); i++) {
            String name = xrp.getAttributeName(i);
            String value = resolveValue(name, xrp.getAttributeValue(i), currentResources);
            sb.append((char) 10).append("<span class=\"html-attribute\">\n" +
                    "<span class=\"html-attribute-name\">" + name + "</span>=\"<span class=\"html-attribute-value\">" + value + "</span>\"</span>");
            //DLog.d("@@@"+(char)10 +  name+ "=\"" + value + "\"");
        }
        return sb;
    }

    @Override
    public void insertSpaces(StringBuffer sb, int num) {
        if (sb == null)
            return;
        for (int i = 0; i < num; i++) {
            //sb.append(" ");
            sb.append("<span> </span>");
        }
    }
    
    @Override
    public void loadDataWithPattern(WebView view, String s) {
        try {
            InputStream inputStream = view.getContext().getAssets().open("pattern.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8 ));
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            String encoded = sb.toString();
            encoded=encoded.replace("@_DATA_@",s);
            view.loadDataWithBaseURL(null, encoded, "text/html", "UTF-8", null);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    @Override
    public String mOutgetText(XmlResourceParser xrp, Resources currentResources) {
        return null;
    }
}
