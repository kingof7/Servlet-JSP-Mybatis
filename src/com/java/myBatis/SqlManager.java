package com.java.myBatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlManager {
	
	//공유해서 쓸거기때문에 static으로 해준다
	private static SqlSessionFactory sqlSessionFactory;
	//각각의 dao에서 읽어옴
	public static SqlSessionFactory getInstance() {	
		
		try {
			
			String resource = "com/java/myBatis/sqlConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return sqlSessionFactory;
	}
}
