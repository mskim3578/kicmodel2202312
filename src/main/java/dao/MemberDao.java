package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import model.KicMember;

public class MemberDao {
	public Connection getConnection() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "kic", "1111");
				return conn;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	

		return null;
	}
	public int insertMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {
/*
 * 
create table kicmember (
id varchar(20) primary key, 
pass varchar(20), 
name varchar(20), 
gender number(1), 
tel varchar(20), 
email varchar(50), 
picture varchar(200));
 * 
 */
		Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(" insert into kicmember  "
					+ " values (?,?,?,?,?,?, ?)");
			// mapping
			pstmt.setString(1, kicmem.getId());
			pstmt.setString(2, kicmem.getPass());
			pstmt.setString(3, kicmem.getName());
			pstmt.setInt(4, kicmem.getGender());
			pstmt.setString(5, kicmem.getTel());
			pstmt.setString(6, kicmem.getEmail());
			pstmt.setString(7, kicmem.getPicture());
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;

		
		
	}
	
	public KicMember oneMember(String id) throws SQLException {
		System.out.println(id);
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(" select * from kicmember where id = ? ");
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {			
			KicMember m = new KicMember();
			m.setId(rs.getString("id"));
			m.setPass(rs.getString("pass"));
			m.setName(rs.getString("name"));
			m.setGender(rs.getInt("gender"));
			m.setTel(rs.getString("tel"));
			m.setEmail(rs.getString("email"));
			m.setPicture(rs.getString("picture"));
			System.out.println(m);
			return m;			
		}		
		return null;
	}
	
	public int updateMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {

		Connection conn = getConnection();
		    String sql = "update kicmember set name=?, gender=?, tel=?, email=?, picture=?  where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// mapping
		
			pstmt.setString(1, kicmem.getName());
			pstmt.setInt(2, kicmem.getGender());
			pstmt.setString(3, kicmem.getTel());
			pstmt.setString(4, kicmem.getEmail());
			
			pstmt.setString(5, kicmem.getPicture());
			pstmt.setString(6, kicmem.getId());
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;

		
		
	}
	
	public int deleteMember(String id) throws UnsupportedEncodingException, SQLException {

		Connection conn = getConnection();
		    String sql = "delete kicmember  where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// mapping			
			pstmt.setString(1,id);
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;	
		
	}
	
	public int passMember(String id, String chgpass) throws UnsupportedEncodingException, SQLException {

		Connection conn = getConnection();
		    String sql = " update kicmember  set pass=? where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// mapping			
			pstmt.setString(1,chgpass);
			pstmt.setString(2,id);
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;	
		
	}
	
	
	
} //class end
