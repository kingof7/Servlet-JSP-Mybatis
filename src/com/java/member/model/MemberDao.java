package com.java.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
///import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int value = 0;
		
		try {
			String sql = "select id from member where id = ?";
			conn = ConnectionProvider.getConnection();
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs= pstmt.executeQuery();
			
			if(rs.next()) value=1;				
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}
	
	public ArrayList<ZipcodeDto> zipcodeReader(String checkDong){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ZipcodeDto> arrayList = null;

		try {
			String sql = "select * from zipcode where dong = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkDong); // 1번째 물음표
			rs = pstmt.executeQuery();
			
			arrayList = new ArrayList<ZipcodeDto>();
			while(rs.next()) { // 넘어온게 있니? 한행씩 넘어옴
				
				ZipcodeDto address = new ZipcodeDto();
				
				//rs가 오라클의 데이터를 자바형으로 바꿔줌
				//(오라클->자바)날짜는 시간으로 바꿔서 Date클래ㅡ로 바꿔줌
				//(자바->오라클)Date클래스로 담아서 
				address.setZipcode(rs.getString("zipcode"));
				address.setSido(rs.getString("sido"));
				address.setGugun(rs.getString("gugun"));
				address.setDong(rs.getString("dong"));
				address.setRi(rs.getString("ri"));
				address.setBunji(rs.getString("bunji"));				
				
				arrayList.add(address); // 객체를 N번지에 넣어줌
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return arrayList;
	}

	public String loginCheck(String id, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String value = null;
		
		try {
			String sql = "select member_level from member where id = ? and password=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) value = rs.getString("member_level");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}
	
	public MemberDto updateId(String id) {
		Connection conn = null;
		MemberDto memberDto = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "select * from member where id = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberDto = new MemberDto();
				memberDto.setNum(rs.getInt("num"));
				memberDto.setId(rs.getString("id"));
				memberDto.setPassword(rs.getString("password"));
				memberDto.setName(rs.getString("name"));
				memberDto.setJumin1(rs.getString("jumin1"));
				memberDto.setJumin2(rs.getString("jumin2"));
				
				memberDto.setEmail(rs.getString("email"));
				memberDto.setZipcode(rs.getString("zipcode"));
				memberDto.setAddress(rs.getString("address"));
				memberDto.setJob(rs.getString("job"));
				memberDto.setInterest(rs.getString("interest"));
				memberDto.setMailing(rs.getString("mailing"));
				memberDto.setMemberLevel(rs.getString("member_level"));
			
			/*
			 * Timestamp ts = rs.getTimestamp("register_date");
				long time = ts.getTime();
				Date date = new Data(time);
				memberDto.setRegisterDate(date);
			 * */
				
				memberDto.setRegisterDate(new Date(rs.getTimestamp("register_date").getTime()));
				
				
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
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
