package com.wgh.core; //将该类保存到com.wgh.core包中

import java.sql.*; //导入java.sql包中的所有类

public class ConnDB {

	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	static{
		driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		url="jdbc:sqlserver://localhost:1433;databaseName=first";//服务器名
		user="sa";
		password="123";
		
	}
	private static Statement stmt =null;
	static Connection connection=null;
	private static ResultSet rs;
	public static Connection getConnection() {
		
		try {
			Class.forName(driver);
			connection=(Connection) DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			connection=null;
			e.printStackTrace();
		}//加载驱动
		if (connection == null) {
			System.err
					.println("警告: DbConnectionManager.getConnection() 获得数据库链接失败.\r\n\r\n链接类型:"
							+ driver
							+ "\r\n链接位置:"
							+ url
							+ "\r\n用户/密码"
							+ user + "/" + password);
		}
		return connection;
	}

	/*
	 * 功能：执行查询语句
	 */
	public ResultSet executeQuery(String sql) {
	
		try { // 捕捉异常
			connection = getConnection(); // 调用getConnection()方法构造Connection对象的一个实例conn
			 stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage()); // 输出异常信息
		}
		return rs; // 返回结果集对象
	}

	/*
	 * 功能:执行更新操作
	 */
	public int executeUpdate(String sql) {
		int result = 0; // 定义保存返回值的变量
		try { // 捕捉异常
			connection = getConnection(); // 调用getConnection()方法构造Connection对象的一个实例conn
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql); // 执行更新操作
		} catch (SQLException ex) {
			result = 0; // 将保存返回值的变量赋值为0
		}
		return result; // 返回保存返回值的变量
	}

	/*
	 * 功能:关闭数据库的连接
	 */
	public void close() {
		try { // 捕捉异常
			if (rs != null) { // 当ResultSet对象的实例rs不为空时
				rs.close(); // 关闭ResultSet对象
			}
			if (stmt != null) { // 当Statement对象的实例stmt不为空时
				stmt.close(); // 关闭Statement对象
			}
			if (connection != null) { // 当Connection对象的实例conn不为空时
				connection.close(); // 关闭Connection对象
			}
		} catch (Exception e) {
			e.printStackTrace(System.err); // 输出异常信息
		}
	}

}
