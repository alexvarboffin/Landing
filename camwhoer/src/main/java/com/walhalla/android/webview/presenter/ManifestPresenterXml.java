package com.walhalla.android.webview.presenter;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.webkit.WebView;

import com.walhalla.android.webview.ui.activities.ManifestExplorer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ManifestPresenterXml extends BaseManifestPresenter {


    public ManifestPresenterXml(ManifestExplorer m) {
        super(m);
    }

    @Override
    public CharSequence getXMLText(XmlResourceParser xrp, Resources currentResources) {
        StringBuffer buffer = new StringBuffer();
        int indent = 0;
        try {
            int eventType = xrp.getEventType();
            String name = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                // for buffer
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if (name != null) {
                            //close previose
                            buffer.append(">");
                        }

                        indent += 1;
                        name = xrp.getName();
                        buffer.append("\n");
                        insertSpaces(buffer, indent);
                        buffer.append("<").append(name);
                        buffer.append(getAttribs(xrp, currentResources));


                        //DLog.d("1122"+xrp.getText());//null

                        break;
                    case XmlPullParser.END_TAG:

                        if (xrp.getName().equals(name)) {
                            //close previose
                            indent -= 1;
                            buffer.append(" />");
                        } else {
                            indent -= 1;
                            buffer.append("\n");
                            insertSpaces(buffer, indent);
                            buffer.append("</" + xrp.getName() + ">");
                        }
                        name = null;
                        break;

                    case XmlPullParser.TEXT:
                        buffer.append("" + xrp.getText());
                        break;

                    case XmlPullParser.CDSECT:
                        buffer.append("<!CDATA[" + xrp.getText() + "]]>");
                        break;

                    case XmlPullParser.PROCESSING_INSTRUCTION:
                        buffer.append("<?" + xrp.getText() + "?>");
                        break;

                    case XmlPullParser.COMMENT:
                        buffer.append("<!--" + xrp.getText() + "-->");
                        break;
                }
                eventType = xrp.nextToken();
            }
        } catch (IOException ioe) {
            mView.showError("Reading XML", ioe);
        } catch (XmlPullParserException xppe) {
            mView.showError("Parsing XML", xppe);
        }
        return buffer;
    }

    @Override
    public void insertSpaces(StringBuffer sb, int num) {
        if (sb == null)
            return;
        for (int i = 0; i < num; i++) {
            sb.append(" ");
        }
    }

    @Override
    public CharSequence getAttribs(XmlResourceParser xrp, Resources currentResources) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < xrp.getAttributeCount(); i++) {
            String name = xrp.getAttributeName(i);
            String value = resolveValue(name, xrp.getAttributeValue(i), currentResources);
            sb.append(" ").append(name).append("=\"").append(value).append("\"");
            //DLog.d("@@@"+(char)10 +  name+ "=\"" + value + "\"");
        }
        return sb;
    }



    @Override
    public void loadDataWithPattern(WebView view, String s) {

    }

    @Override
    public String mOutgetText(XmlResourceParser xrp, Resources currentResources) {
        return getXMLText(xrp, currentResources).toString();
    }
}
