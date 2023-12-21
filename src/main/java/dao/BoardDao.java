package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.KicMember;

public class BoardDao {
	
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
	
	
	public int insertBoard(Board board) throws UnsupportedEncodingException, SQLException {

		Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(" insert into board   "
					+ "  values  (boardseq.nextval ,?,?,?,?,?,sysdate,0,?)");
			// mapping
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getPass());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5,board.getFile1());
			pstmt.setString(6, board.getBoardid());
		
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;

		
		
	}
	
	public List<Board> boardList() throws UnsupportedEncodingException, SQLException {
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(" select * from board");
		ResultSet rs = pstmt.executeQuery();
		List<Board>  li = new ArrayList<>();
		while(rs.next()) {
			Board m = new Board();
			m.setNum(rs.getInt("num"));
			m.setName(rs.getString("name"));
			m.setPass(rs.getString("pass"));
			m.setSubject(rs.getString("subject"));
			m.setContent(rs.getString("content"));
			m.setFile1(rs.getString("file1"));
			m.setRegdate(rs.getDate("regdate"));
			m.setReadcnt(rs.getInt("readcnt"));
			m.setBoardid(rs.getString("boardid"));
		   li.add(m);
		}
		return li;
	}
	
	public Board oneBoard(int num) throws UnsupportedEncodingException, SQLException {
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(" select * from board where num = ?");
		pstmt.setInt(1, num);
		ResultSet rs = pstmt.executeQuery();
		Board m = new Board();
		if(rs.next()) {
			m.setNum(rs.getInt("num"));
			m.setName(rs.getString("name"));
			m.setPass(rs.getString("pass"));
			m.setSubject(rs.getString("subject"));
			m.setContent(rs.getString("content"));
			m.setFile1(rs.getString("file1"));
			m.setRegdate(rs.getDate("regdate"));
			m.setReadcnt(rs.getInt("readcnt"));
			m.setBoardid(rs.getString("boardid"));
		}
		return m;
	}
	
	
	
	public int updateBoard(Board board) throws UnsupportedEncodingException, SQLException {

		Connection conn = getConnection();
		String sql = "update board set name=?, subject = ? , content = ? , file1 = ? "
				+ "  where num = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// mapping
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4,board.getFile1());
			pstmt.setInt(5,board.getNum());
		
			
			// 4) excute
			int num = pstmt.executeUpdate();
			return num;

		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
