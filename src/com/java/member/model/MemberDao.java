package com.java.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
///import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;
import com.java.myBatis.SqlManager;

//Spring에서 Mapper 역할 - 쿼리실행
public class MemberDao { // Data Access Object
	
	//SqlManager.java의 sqlSessionFactory 객체를 가져다 씀 = DB연결  // 			↓ 초기화
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	//					↓ 계속바뀔거기 때문에 초기화 x
	private SqlSession session;
	
	//singleton pattern : 단 한개의 객체만을 가지고 구현(설계)한다.
	private static MemberDao instance = new MemberDao(); //메모리공간 절약
	
	//MemberDao.getInstance()로 쓸 수 있음
	public static MemberDao getInstance() {
		return instance;
	}
						//value를 받아야하므로 dto 받아야함
	public int insert(MemberDto memberDto) {
	
		int value = 0;
		
		try {	
			
			System.out.println(memberDto.toString());
			session = sqlSessionFactory.openSession();	// 열어라		
			value = session.insert("member_insert", memberDto);
			session.commit(); // 자동 커밋이 안되므로
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();	
		}
		
		
		return value;
	}

	public int idCheck(String id) {
				
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			String checkId = session.selectOne("id_check", id);		
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
			
		}
		
		return value;
	}
	
	public List<ZipcodeDto> zipcodeReader(String checkDong){
				
		List<ZipcodeDto> arrayList = null;

		try {
			session = sqlSessionFactory.openSession();
			arrayList = session.selectList("member_zipcode", checkDong);			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return arrayList;
	}

	public String loginCheck(String id, String password) {
		
		String value = null;
		
		try {
			HashMap<String, String> hMap = new HashMap<String, String>();
			hMap.put("id", id);
			hMap.put("password", password);
			
			session = sqlSessionFactory.openSession();
			value = session.selectOne("member_login", hMap);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}
	
	public MemberDto updateId(String id) {
		
		MemberDto memberDto = null;		
		
		try {
			session = sqlSessionFactory.openSession();
			memberDto = session.selectOne("member_select", id);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return memberDto;
		
		
	}
	
	public int update(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int value = 0;
		
		try {
			String sql = "update member set password=?, email=?, zipcode=?, address=?, job=?, mailing=?, interest=? where num = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberDto.getPassword());
			pstmt.setString(2, memberDto.getEmail());
			pstmt.setString(3, memberDto.getZipcode());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getJob());
			pstmt.setString(6, memberDto.getMailing());
			pstmt.setString(7, memberDto.getInterest());
			pstmt.setInt(8, memberDto.getNum());
			
			value = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;	
		
	}
	
	public int delete(String id, String password) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try {
			String sql = "delete from member where id=? and password=?";
			
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			value = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}

}
