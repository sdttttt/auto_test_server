//package com.auto.test.common.handler.typehandler;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @Author: zl
// */
//public abstract JsonTypeHandler extends BaseTypeHandler<Class> {
//
//  public abstract Class getTypeClass();
//  //private Class<T> type;
//  public JsonTypeHandler(Class  type) {
//    if (type == null) {
//      throw new IllegalArgumentException("Type argument cannot be null");
//    }
//    this.type = type;
//  }
//
//  @Override
//  public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
//                                  JdbcType jdbcType) throws SQLException {
//
//    ps.setString(i, JSON.toJSONString(parameter));
//  }
//
//  @Override
//  public T getNullableResult(ResultSet rs, String columnName)
//      throws SQLException {
//
//    return JSON.parseObject(rs.getString(columnName), getTypeClass());
//  }
//
//  @Override
//  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//
//    return JSON.parseObject(rs.getString(columnIndex), getTypeClass());
//  }
//
//  @Override
//  public T getNullableResult(CallableStatement cs, int columnIndex)
//      throws SQLException {
//
//    return JSON.parseObject(cs.getString(columnIndex), type);
//  }
//}
