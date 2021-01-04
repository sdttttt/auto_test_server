package com.auto.test.service.common;//package com.ctsi.resource.service.common;
//
//import java.util.List;
//
//public interface K8sResource<T> {
//
//  //获取资源数据为json格式
//  String getResourceJSON(String namespace, String resourceName);
//
//  //获取资源数据为json格式List数据
//  String getResourceJSONList(String namespace, String fieldSelector,
//                             Boolean includeUninitialized,
//                             String labelSelector, Integer limit);
//
//  //通过url访问数据资源
//  String executeHttpGetBack(String url);
//
//  //获取资源数据为json格式
//  T getResource(String namespace, String resourceName);
//
//  //获取资源数据为json格式
//  List<T> getResourceList(String namespace, String fieldSelector,
//                          Boolean includeUninitialized,
//                          String labelSelector, Integer limit);
//
////    //以文件的方式发布资源
////    T deployResource(File file);
////
////    //以配置json数据的格式发布资源
////    T deployResource(String config);
////
////    //以对应的类型形式发布资源
////    T deployResource(T t);
////
////    //以对象的形式替换资源
////    T replaceResource(T t);
////
////    //以json格式数据替换数据
////    T replaceResource(String data);
////
////    //以对象的形式下线内容
////    T deleteResource(T t);
////
////    //以json格式数据下线内容
////    T deleteResource(String data);
//}
