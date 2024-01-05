package dao;

import java.io.UnsupportedEncodingException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



import org.apache.ibatis.session.SqlSession;

import model.KicMember;
import mybatis.KicMemberAnno;
import mybatis.MybatisConnection;


public class MemberMybatisDao {
	
	SqlSession sqlSession = MybatisConnection.getConnection();  //class영역 있어야함 
	//MybatisConnection.close(sqlSession) 삭제 
	private static final String ns= "kicmember.";
	public int insertMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {
         System.out.println("mybatis insertMember");
		 int num = sqlSession.getMapper(KicMemberAnno.class).insertMember(kicmem);  //dml -> commit()
		 sqlSession.commit();		
		 return num;		
	}
	
	public KicMember oneMember(String id) throws SQLException {
		System.out.println(id);
		 KicMember mem = sqlSession.getMapper(KicMemberAnno.class).oneMember(id) ; //dml -> commit()
		 return mem;	
		
	}
	
	public int updateMember(KicMember kicmem) throws UnsupportedEncodingException, SQLException {
		 int num = sqlSession.getMapper(KicMemberAnno.class).memberUpdate(kicmem); //dml -> commit()
		sqlSession.commit();
	
			return num;

		
		
	}
	
	public int deleteMember(String id) throws UnsupportedEncodingException, SQLException {
		
		 int num = sqlSession.getMapper(KicMemberAnno.class).deleteMember(id); //dml -> commit()
			sqlSession.commit();
		return num;
		
	}
	
	public int passMember(String id, String chgpass) throws UnsupportedEncodingException, SQLException {
		//mapper의  parameter type은 한개임
		Map map = new HashMap();
		map.put("id", id);
		map.put("pass", chgpass);
	
		 int num = sqlSession.getMapper(KicMemberAnno.class).passMember(map); //dml -> commit()
			sqlSession.commit();
		return num;
		
	}
	
	
	
} //class end
