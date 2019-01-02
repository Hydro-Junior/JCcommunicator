package com.qbt.daoImpl;

import com.qbt.entity.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseDao {
	//与数据库建立连接，返回数据库连接对象
	public Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(Constants.DBDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(Constants.DBURL, Constants.DBUser, Constants.DBpwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 释放相应的资源
	 * @param rs
	 * @param pstmt
	 * @param coon
	 */
	public void closeAll(ResultSet rs , Statement pstmt, Connection coon){
		try {
			if(rs != null){
				rs.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(coon != null){
				coon.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 此方法可以完成增删改所有操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean operUpdate(String sql,List<Object> params){ //sql中问号表示占位符
		int res = 0;  //影响的行数
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn =getConn(); //建立数据库连接
			pstmt = conn.prepareStatement(sql); //装载sql语句
			if(params != null){
				//假如有？占位符，在执行前替换问号占位符
				for(int i = 0; i<params.size();i++){
					pstmt.setObject(i+1,params.get(i));
				}
			}
			res = pstmt.executeUpdate(); //进行增删改操作
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return res>0? true:false;
	}
	public boolean checkTableExists(String tableName){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			conn =getConn(); //建立数据库连接
			stmt = conn.createStatement(); //装载sql语句
			rs = conn.getMetaData().getTables(null, null, tableName, null);
			if (rs.next()) {
				res = true;
				System.out.println("该表已存在！");
				return res;
			}else {
				System.out.println("该表不存在！创建新表！");
				return res;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll(rs,stmt,conn);
		}
		return res;
	}
	/**
	 * 使用泛型方法和反射机制进行封装,实现查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> operQuery(String sql, List<Object> params,Class<T> cls) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> data = new ArrayList<T>();
		
		try {
			conn = getConn(); //建立数据库连接
			pstmt = conn.prepareStatement(sql); //装载sql语句
			if(params != null){
				//假如有？占位符，在执行前替换问号占位符
				for(int i = 0; i<params.size();i++){
					pstmt.setObject(i+1,params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			//把查询出来的记录封装成对应的实体类对象
			ResultSetMetaData rsd = rs.getMetaData(); //得到记录集元数据对象
			//通过此对象可以得到表的结构，包括列名、列的个数、列的数据类型
			while(rs.next()){
				T m = cls.newInstance();
				for(int i = 0; i<rsd.getColumnCount();i++){
					String col_name = rsd.getColumnName(i+1); //获得列名
					Object value = rs.getObject(col_name);//获得列对应的值
					Field field = cls.getDeclaredField(col_name);
					field.setAccessible(true);//给私有属性设置可访问权
					field.set(m,value);//给对象的私有属性赋值
				}
				data.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return data;
	}

}