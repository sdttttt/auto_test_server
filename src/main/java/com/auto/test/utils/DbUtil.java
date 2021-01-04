package com.auto.test.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DbUtil {
  public static Connection createConnection(String url, String username, String password, Integer dataType) {
    Connection connection = null;
    try {
      if (dataType.equals(1)) {
        Class.forName("com.mysql.jdbc.Driver");
      } else {
        //oracle的以后弄
        Class.forName("com.mysql.jdbc.Driver");
      }
      connection = DriverManager.getConnection(url, username, password);
      return connection;
    } catch (Exception e) {
      return null;
    }
  }
  
  public static boolean testConnection(Connection connection) {
    try {
      connection.createStatement().execute("select now()");
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public static List<Map<String, String>> executeQuery(Connection connection, String sql) throws Exception {
    List list = new ArrayList();
    Statement statement = null;
    ResultSet resultSet = null;
    try {
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      resultSet = statement.executeQuery(sql);
      //将指针指向第一行之前
//        resultSet.beforeFirst();
      while (resultSet.next()) {
        Map<String, String> resultMap = new ConcurrentHashMap<String, String>();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
          String key = resultSet.getMetaData().getColumnLabel(i);
          String value = resultSet.getString(i);
//                    Object value = resultSet.getObject(i);
          if (value == null) {
            value = "";
          }
          resultMap.put(key, value);
        }
        list.add(resultMap);
      }
      return list;
    } finally {
//            log.info("关闭连接");
      if (statement != null) {
        statement.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    
  }
  
  public static Integer executeUpdate(Connection connection, String sql) throws Exception {
    int updataCount = 0;
    Statement statement = null;
    try {
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
//            log.info("开始进行insert，delete或者update=================");
      updataCount = statement.executeUpdate(sql);
      return updataCount;
    } finally {
//            log.info("关闭连接");
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
  
  public static boolean moveData(Connection orgConnect, Connection desConnect, String sql) throws Exception {
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement desStatement = null;
    String table = sql.toLowerCase().split("from")[1].split("where")[0];
    try {
      
      statement = orgConnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      resultSet = statement.executeQuery(sql);
      //结果集获取到的长度
      int size = resultSet.getMetaData().getColumnCount();
      //拼接insert into 语句
      StringBuffer sbf = new StringBuffer();
      sbf.append("insert into " + table + " values (");
      String link = "";
      for (int i = 0; i < size; i++) {
        sbf.append(link).append("?");
        link = ",";
      }
      sbf.append(")");
      desStatement = desConnect.prepareStatement(sbf.toString());
      while (resultSet.next()) {
        for (int i = 1; i <= size; i++) {
          Object param = resultSet.getObject(i);
          desStatement.setObject(i, param);
//                    if (param instanceof Integer) {
//                        int value = ((Integer) param).intValue();
//                        desStatement.setInt(i, value);
//                    } else if (param instanceof String) {
//                        String s = (String) param;
//                        desStatement.setString(i, s);
//                    } else if (param instanceof Double) {
//                        double d = ((Double) param).doubleValue();
//                        desStatement.setDouble(i, d);
//                    } else if (param instanceof Float) {
//                        float f = ((Float) param).floatValue();
//                        desStatement.setFloat(i, f);
//                    } else if (param instanceof Long) {
//                        long l = ((Long) param).longValue();
//                        desStatement.setLong(i, l);
//                    } else if (param instanceof Boolean) {
//                        boolean b = ((Boolean) param).booleanValue();
//                        desStatement.setBoolean(i, b);
//                    } else if (param instanceof Date) {
//                        Date d = (Date) param;
//                        desStatement.setDate(i, (Date) param);
//                    } else if (param == null || param instanceof Timestamp) {
//                        desStatement.setObject(i, param);
//                    } else if (param instanceof BigDecimal) {
////                        desStatement.setBigDecimal(i, (BigDecimal) param);
//                        desStatement.setString(i, param + "");
//                    } else {
//                        desStatement.setObject(i, param);
//                    }
        }
        desStatement.addBatch();
//                desStatement.execute();
      }
      desStatement.executeBatch();
      return true;
    } finally {
//            log.info("关闭连接");
      if (statement != null) {
        statement.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
      if (desStatement != null) {
        desStatement.close();
      }
      if (orgConnect != null) {
        orgConnect.close();
      }
      if (desConnect != null) {
        desConnect.close();
      }
    }
  }
}
