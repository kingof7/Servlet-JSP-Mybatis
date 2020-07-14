package com.java.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;
import com.java.myBatis.SqlManager;

public class BoardDao {
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	private SqlSession session;
	//싱글톤 패턴
	private static BoardDao instance = new BoardDao();
	
	public static BoardDao getInstance() {
		return instance;
	}
	
	public int insert(BoardDto boardDto) {

		int value = 0;				
		writeNumber(boardDto);
		
		try {						
			session = sqlSessionFactory.openSession();
			value = session.insert("board_insert", boardDto);
			session.commit();
				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return value;		
	}
	
	public void writeNumber(BoardDto boardDto) {
		// 그룹번호, 글순서, 글레벨
		
				int boardNumber = boardDto.getBoardNumber(); // 0	
				int groupNumber = boardDto.getGroupNumber(); // 1
				int sequenceNumber = boardDto.getSequenceNumber(); // 0
				int sequenceLevel = boardDto.getSequenceLevel(); // 0
				
				try {
					//루트글
					if(boardNumber == 0) {	// groupNumber, 0, 0 // ROOT : 그룹번호

						session = sqlSessionFactory.openSession();

						//rs.next가 없으니 max에 null값을 가져올 수 있기 때문에 , 쿼리에 NVL함수 써야됨
						int max = session.selectOne("board_group_number_max");// 넘어가는 값이 없음

						if(max != 0) boardDto.setGroupNumber(max+1); // 최고 그룹넘버 최대값에 1을 더함

						//자식글
					} else {	// 답글 : 글순서, 글레벨

						session = sqlSessionFactory.openSession();
						session.update("board_child", boardDto);
						session.commit();
						sequenceNumber += 1;
						sequenceLevel += 1;
						boardDto.setSequenceNumber(sequenceNumber);
						boardDto.setSequenceLevel(sequenceLevel);

						// 시퀀스 넘버가 0보다큰(=이전에 작성한글들) 것들 1 씩 모두 증가
						/*
						 * sql = "update board set sequence_number = sequence_number + 1 " +
						 * "where group_number=? and sequence_number > ?";
						 * 
						 * conn = ConnectionProvider.getConnection(); pstmt =
						 * conn.prepareStatement(sql); pstmt.setInt(1, groupNumber); pstmt.setInt(2,
						 * sequenceNumber); pstmt.executeUpdate();
						 * 
						 * //본인 것 1증가 sequenceNumber = sequenceNumber + 1; sequenceLevel = sequenceLevel
						 * + 1;
						 * 
						 * boardDto.setSequenceNumber(sequenceNumber);
						 * boardDto.setSequenceLevel(sequenceLevel);
						 */
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();			
				}				
				
		
	}
	
	// 전체 게시물 목록 뿌려주기
	public int getCount() {
			
		int value = 0;
		
		try {
			
			session = sqlSessionFactory.openSession();
			value = session.selectOne("board_count");
			session.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		
		return value;
	}
	//리스트 뿌려주기
	public List<BoardDto> getBoardList(int startRow, int endRow) {
		
		HashMap<String, Integer> hMap = new HashMap<String, Integer>();
		hMap.put("startRow", startRow);
		hMap.put("endRow", endRow);
		
		List<BoardDto> boardList = new ArrayList<BoardDto>();		
		
		try {
			
			session = sqlSessionFactory.openSession();
			boardList = session.selectList("board_list", hMap);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return boardList;
	}
	
	public BoardDto read(int boardNumber) {
		
		BoardDto boardDto = null;
		
		try {
			
			session=sqlSessionFactory.openSession();
			//조회수 카운트 올리기
			session.update("board_update", boardNumber);
			boardDto = session.selectOne("board_read", boardNumber);
			session.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
				
		return boardDto;		
	}
	
	public int delete(BoardDto boardDto) {
					
		int value = 0;		
		System.out.println(boardDto.toString());			
		try {
			session = sqlSessionFactory.openSession();
			value = session.delete("board_delete", boardDto);			
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();			
		}
		
		return value;
	}
	
	public int update(BoardDto boardDto) {
	
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			value = session.delete("board_up", boardDto);			
			session.commit();			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}		
		
		return value;		
	}	
	
}
