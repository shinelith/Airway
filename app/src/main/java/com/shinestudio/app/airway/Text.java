package com.shinestudio.app.airway;

public class Text {
    public static final String EMPTY_SIGN = "-";

    /**
     * 空串
     *
     * @param s
     * @return
     */
    public static String str(String s) {
        return str(s, null);
    }

    /**
     * 空串默认
     *
     * @param s
     * @param def
     * @return
     */
    public static String str(String s, String def) {
        if ((s == null || s.isEmpty())) {
            return def != null ? def : "";
        } else {
            return s;
        }
    }

    /**
     * 空串默认替代
     *
     * @param s
     * @return
     */
    public static String strE(String s) {
        return str(s, EMPTY_SIGN);
    }

    /**
     * 字符串添加头尾
     *
     * @param s
     * @param head
     * @param foot
     * @return
     */
    public static String strHeaderFooter(String s, String head, String foot) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        s = head != null ? head + s : s;
        s = foot != null ? s + foot : s;
        return s;
    }

    public static String heading(int hdg) {
        return String.format("%03d", hdg);
    }
}
