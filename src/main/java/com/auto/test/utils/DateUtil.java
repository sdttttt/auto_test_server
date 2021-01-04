package com.auto.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static String format(String pattern) {
    return format(new Date(), pattern);
  }
  
  /**
   * @param pattern 日期表达式
   * @return 返回对应格式的字符串
   */
  public static String format(Date date, String pattern) {
    SimpleDateFormat sdf;
    try {
      sdf = new SimpleDateFormat(pattern);
    } catch (Exception e) {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    String format = sdf.format(date);
    return format;
  }
  
  /*
   * 将时间戳转换为时间
   */
  public static String stampToDate(String s) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    return simpleDateFormat.format(date);
  }
  
}
