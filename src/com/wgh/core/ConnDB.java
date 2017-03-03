package com.wgh.core; //�����ౣ�浽com.wgh.core����

import java.sql.*; //����java.sql���е�������

public class ConnDB {

	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	static{
		driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		url="jdbc:sqlserver://localhost:1433;databaseName=first";//��������
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
		}//��������
		if (connection == null) {
			System.err
					.println("����: DbConnectionManager.getConnection() ������ݿ�����ʧ��.\r\n\r\n��������:"
							+ driver
							+ "\r\n����λ��:"
							+ url
							+ "\r\n�û�/����"
							+ user + "/" + password);
		}
		return connection;
	}

	/*
	 * ���ܣ�ִ�в�ѯ���
	 */
	public ResultSet executeQuery(String sql) {
	
		try { // ��׽�쳣
			connection = getConnection(); // ����getConnection()��������Connection�����һ��ʵ��conn
			 stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage()); // ����쳣��Ϣ
		}
		return rs; // ���ؽ��������
	}

	/*
	 * ����:ִ�и��²���
	 */
	public int executeUpdate(String sql) {
		int result = 0; // ���屣�淵��ֵ�ı���
		try { // ��׽�쳣
			connection = getConnection(); // ����getConnection()��������Connection�����һ��ʵ��conn
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql); // ִ�и��²���
		} catch (SQLException ex) {
			result = 0; // �����淵��ֵ�ı�����ֵΪ0
		}
		return result; // ���ر��淵��ֵ�ı���
	}

	/*
	 * ����:�ر����ݿ������
	 */
	public void close() {
		try { // ��׽�쳣
			if (rs != null) { // ��ResultSet�����ʵ��rs��Ϊ��ʱ
				rs.close(); // �ر�ResultSet����
			}
			if (stmt != null) { // ��Statement�����ʵ��stmt��Ϊ��ʱ
				stmt.close(); // �ر�Statement����
			}
			if (connection != null) { // ��Connection�����ʵ��conn��Ϊ��ʱ
				connection.close(); // �ر�Connection����
			}
		} catch (Exception e) {
			e.printStackTrace(System.err); // ����쳣��Ϣ
		}
	}

}
