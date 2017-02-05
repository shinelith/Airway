package com.shinestudio.app.airway.extdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouterFinderParser {
    private Document doc;
    private Elements tt;
    private Element way;

    public RouterFinderParser(String html) {
        doc = Jsoup.parse(html);
        tt = doc.getElementsByTag("tt");
        way = tt.get(1);
    }

    public ArrayList<String> getRouteWay() {
        ArrayList<String> result = new ArrayList<String>();

        String reg = null;
        Pattern pattern = null;
        Matcher matcher = null;

        ArrayList<String> l1 = new ArrayList<String>();
        reg = "<b>(.*?)</b>";
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(way.toString());
        while (matcher.find()) {
            l1.add(matcher.group(1).trim());
        }

        ArrayList<String> l2 = new ArrayList<String>();
        reg = "</b> (.*?)<b>";
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(way.toString());
        while (matcher.find()) {
            l2.add(matcher.group(1).trim());
        }

        int length = l1.size() + l2.size();
        for (int i = 0; i < length; i++) {
            String t = null;
            if (i == 0 || i % 2 == 0) {
                t = l1.get(i / 2);
                result.add(t);
            } else {
                t = l2.get(i / 2);
                if ("SID".equals(t)) {  //加入两个SID
                    result.add(t);
                }
                result.add(t);
            }
        }
        return result;
    }
}
