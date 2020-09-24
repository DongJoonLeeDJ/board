package com.kb.www.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


//db접속
public class JdbcUtil {	
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			//커넥션 풀로 db연결
			DataSource ds = (DataSource) envCtx.lookup("jdbc/MysqlDB");
			con = ds.getConnection();
			con.setAutoCommit(false); //autocommit 끄고 수동커밋,롤백
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Connection 자원 해제
	public static void close(Connection con) {
		if (con!=null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//PreparedStatement 자원 해제
	public static void close(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//ResultSet 자원 해제
	public static void close(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//수동commit
	public static void commit(Connection con) {
		if (con!=null) {
			try {
				con.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//수동rollback
	public static void rollback(Connection con) {
		if (con!=null) {
			try {
				con.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
