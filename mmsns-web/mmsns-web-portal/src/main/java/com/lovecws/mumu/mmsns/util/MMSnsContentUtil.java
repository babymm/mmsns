package com.lovecws.mumu.mmsns.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 内容工具类
 * @date 2017-12-29 09:56:
 */
public class MMSnsContentUtil {

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http[s]{0,1}:\"?(.*?)(\"|>|\\s+)";

    /**
     * 从字符串中获取首页图片url
     *
     * @param content 字符串内容
     * @return
     */
    public static String getFacadeImageFromContent(String content) {
        if (content == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            Matcher urlMatcher = Pattern.compile(IMGSRC_REG).matcher(group);
            while (urlMatcher.find()) {
                String url = urlMatcher.group();
                if (url == null || url.contains("/layui") || url.contains("/icon")) {
                    continue;
                }
                url = url.substring(0, url.length() - 1);
                return url;
            }
        }
        return null;
    }

    /**
     * 字数统计
     *
     * @param content 文本内容
     * @return
     */
    public static int wordCount(String content) {
        if (content == null) return 0;
        int wordCount = 0;
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.toString(chars[i]).matches("[\\u4E00-\\u9FA5]+")) {
                wordCount++;
            }
        }
        return wordCount;
    }
}
