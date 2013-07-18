package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import redis.clients.jedis.Jedis;

public class Test {

	public static Connection connectMysql() {
		String userName = "root";
		String userPasswd = "";
		String dbName = "redis";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
		Connection conn = null;
		try {
			if (conn == null) {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, userName, userPasswd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeMysql(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void insertMysql(Connection conn, int key, int value) {
		PreparedStatement ps = null;
		String sql = "insert into test values(?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, key);
			ps.setInt(2, value);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("insert: key=" + key + " value=" + value);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = connectMysql();
		Jedis jedis = new Jedis("localhost");
		jedis.connect();
		Random rKey = new Random(47);
		Random rValue = new Random(47);
		int i = 0;
		for (; i < 500000; i++) {
			int key = rKey.nextInt(500000);
			if (key != 0) {
				int value = rValue.nextInt(1000000);
				while (value < 1000) {
					value = rValue.nextInt(1000000);
				}
				jedis.set(String.valueOf(key), String.valueOf(value));
				insertMysql(conn, key, value);
				System.out.println("i = " + i);
			}
		}
		jedis.disconnect();
	}
}
