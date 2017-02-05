package com.shinestudio.app.airway.extdata;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * SkyVector
 * 区域航图提供
 * http://skyvector.com/
 */
public class SkyVector {
    private String htmlCode;
    private String url;

    public void showInWeb(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(i);
    }

    public SkyVector getVFR(String latitude, String longitude) {
        htmlCode = "<div id=\"sv_6560\" style=\"width:100%; height: 332px;\">\n" +
                "<script src=\"http://skyvector.com/api/lchart?ll=" + latitude + "," + longitude + "&amp;s=2&amp;c=sv_6560&amp;i=301\" type=\"text/javascript\"></script>\n" +
                "</div>";
        url = "http://skyvector.com/?ll=" + latitude + "," + longitude + "&chart=301&zoom=2;";
        return this;
    }

    public SkyVector getIFR(String latitude, String longitude) {
        htmlCode = "<div id=\"sv_6560\" style=\"width:100%; height: 332px;\">\n" +
                "<script src=\"http://skyvector.com/api/lchart?ll=" + latitude + "," + longitude + "&amp;s=2&amp;c=sv_6560&amp;i=302\" type=\"text/javascript\"></script>\n" +
                "</div>";
        url = "http://skyvector.com/?ll=" + latitude + "," + longitude + "&chart=302&zoom=2;";
        return this;
    }

    public String getHtmlCode() {
        return htmlCode;
    }
}
