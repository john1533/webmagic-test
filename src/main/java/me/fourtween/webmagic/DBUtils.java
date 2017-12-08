package me.fourtween.webmagic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	public static Connection getConnection() throws SQLException,ClassNotFoundException {
	//第一步：加载MySQL的JDBC的驱动
		Class.forName("com.mysql.jdbc.Driver");
		
		//取得连接的url,能访问MySQL数据库的用户名,密码；studentinfo：数据库名
		String url = "jdbc:mysql://localhost:3306/marksix";        
		String username = "root";
		String password = "123456";
		
		//第二步：创建与MySQL数据库的连接类的实例
		Connection con = DriverManager.getConnection(url, username, password);        
		return con;        
	}
	
	
	public static void closeStatement(Statement stmt) {
    	try {
    		if (stmt != null)
    				stmt.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

	}

	public static void closeResultSet(ResultSet rSet) {
		try {
			if (rSet != null)
				rSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close(); //release the connection - the name is tricky but connection is not closed it is released
			   //and it will stay in pool
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
