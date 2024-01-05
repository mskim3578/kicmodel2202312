package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.Board;
import model.Comment;
import model.KicMember;
import mybatis.KicMemberAnno;
import mybatis.MybatisConnection;

public class BoardMybatisDao {
	
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
	SqlSession sqlSession = MybatisConnection.getConnection();  //class영역 있어야함 
	private static final String ns= "board.";
	
	public int insertBoard(Board board) throws UnsupportedEncodingException, SQLException {
		 int num = sqlSession.insert(ns+"insertBoard", board);  //dml -> commit()
		 sqlSession.commit();		
		 return num;	
	}
	
	public List<Board> boardList(int pageInt, int limit, String boardid) throws UnsupportedEncodingException, SQLException {
		
		Map map = new HashMap();
		map.put("boardid", boardid);
		map.put("start", (pageInt - 1) * limit + 1);
		map.put("end", pageInt * limit);
		return sqlSession.selectList(ns + "boardList", map);
		
	}
	
public int boardCount(String boardid) throws UnsupportedEncodingException, SQLException {
	return sqlSession.selectOne(ns + "boardCount", boardid);
}
	
	public Board oneBoard(int num) throws UnsupportedEncodingException, SQLException {
		return sqlSession.selectOne(ns + "oneBoard", num);
	}	
	
	public int updateBoard(Board board) throws UnsupportedEncodingException, SQLException {
        System.out.println(board);
		int num = sqlSession.update(ns + "updateBoard", board);
		 sqlSession.commit();		
		 return num;	
		
	}
	
	
	public int boardDelete(int num) throws UnsupportedEncodingException, SQLException {
		int num2 = sqlSession.delete(ns + "boardDelete", num);
		 sqlSession.commit();		
		 return num2;	
		
	}

		
	
	public int insertComment(String comment, int num) throws UnsupportedEncodingException, SQLException {
		Map map = new HashMap();
		map.put("comment", comment);
		map.put("num", num);
		
		int num2 = sqlSession.update(ns + "insertComment", map);
		 sqlSession.commit();		
		 return num2;	
		
	}
	
public List<Comment> commentList(int num) throws UnsupportedEncodingException, SQLException {
		
	return sqlSession.selectList(ns + "commentList", num);
}
	 
	
	
	
	
	
	
	
	
	
	
	

}
