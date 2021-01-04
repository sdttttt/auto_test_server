package com.auto.test.utils;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;

public class StringUtilHelper {
  public static String checkStringFormat(String str) {
    try {
      JSONObject.parseObject(str);
      return "json";
    } catch (Exception e) {
      try {
        JSONObject.parseArray(str);
        return "json";
      } catch (Exception e1) {
        try {
          Jsoup.parse(str);
          return "html";
        } catch (Exception ee) {
          try {
            DocumentHelper.parseText(str);
            return "xml";
          } catch (Exception eee) {
          }
        }
      }
    }
    return "text";
  }
}
