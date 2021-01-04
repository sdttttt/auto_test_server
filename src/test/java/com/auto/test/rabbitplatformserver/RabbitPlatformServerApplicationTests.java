//package com.auto.test.rabbitplatformserver;
//
//import com.auto.test.dto.UiTemplateParams;
//import com.auto.test.service.SendMailSevice;
//import com.auto.test.service.UserService;
//import com.auto.test.service.TTestStepUiNewLogService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AutoTestServerApplicationTests {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SendMailSevice sendMailSevice;
//
//    @Autowired
//    private TTestStepUiNewLogService testStepUiNewLogService;
//
//
//    @Test
//    public void contextLoads() {
//        System.out.println("重置密码成功测试=====");
//        userService.resetPassword("test", "123456");
//    }
//
//    @Test
//    public void testReport() {
//        testStepUiNewLogService.getReportHtml(72L,"en");
//    }
//
//    @Test
//    public void setSendMailTest() {
//        try {
//            List list = new ArrayList();
////            list.add("237371257@qq.com");
//            list.add("yunlin1.luo@partner.midea.com");
//
//            UiTemplateParams uiTemplateParams = new UiTemplateParams();
//            uiTemplateParams.setJobname("测试一下本地客户端百度_190831112526");
//            uiTemplateParams.setWebip("localhost");
//            uiTemplateParams.setWebport("8889");
//            uiTemplateParams.setContextpath("/public");
//            uiTemplateParams.setBusinesscount(20);
//            uiTemplateParams.setBusinesstime(2000);
//            uiTemplateParams.setCasecount(50);
//            uiTemplateParams.setCasesuc(40);
//            uiTemplateParams.setCasefail(5);
//            uiTemplateParams.setCaseskip(5);
//            sendMailSevice.sendMailTemplate(list, "这个是一个测试邮件的主题", "ui-test.ftl", uiTemplateParams);
//            System.out.println("发送邮件成功=====");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
