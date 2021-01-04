package com.auto.test.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class FreemarkerUtil {
  
  public static String process(String ftlFileName, Object dataModel) throws IOException, TemplateException {
    try (StringWriter stringWriter = new StringWriter()) {
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
      cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/email_template");
      cfg.setDefaultEncoding("UTF-8");
      
      Template template = cfg.getTemplate(ftlFileName);
      template.process(dataModel, stringWriter);
      
      return stringWriter.toString();
    }
  }
}
