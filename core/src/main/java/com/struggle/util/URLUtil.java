package com.struggle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author xuchengdongxcd@126.com on 2016/9/26.
 */
public final class URLUtil {

    private URLUtil() {

    }

    public static String appendParam(String url, Map<String, String> paramMap) {
        if (url == null || paramMap == null || paramMap.isEmpty()) {
            return url;
        }

        StringBuilder stringBuilder = new StringBuilder(url.length() + paramMap.size() * 30).append(url);

        if (!url.contains("?")) {
            stringBuilder.append("?");
        } else {
            stringBuilder.append("&");
        }

        boolean isFirst = true;
        String val = null;

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (!isFirst) {
                stringBuilder.append("&");
            }
            isFirst = false;
            val = entry.getValue();
            if (isUrl(val)) {
                val = encodUrl(val);
            }
            stringBuilder.append(entry.getKey()).append("=").append(val);
        }

        return stringBuilder.toString();
    }

    public static String appendParam(String url, String paramName, String val) {
        Map<String, String> paramMap = new HashMap<String, String>(1);
        paramMap.put(paramName, val);
        return appendParam(url, paramMap);
    }

    public static String encodUrl(String url) {
        return encodUrl(url, null);
    }

    public static String encodUrl(String url, String charsetName) {
        if (url == null) {
            return null;
        }

        try {
            return URLEncoder.encode(url, charsetName != null ? charsetName : "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUrl(String url) {
        return url != null && Pattern.matches("^(https|http)://.*", url);
    }

}
