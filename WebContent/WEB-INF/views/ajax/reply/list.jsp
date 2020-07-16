<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<script type="text/javascript" src="${root}/xhr/xhr.js"></script>
<script type="text/javascript" src="${root}/javascript/reply/replyWrite.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>한줄 댓글이 가능합니다.</div>
	<br/><br/>
	
	<div>
		<input id="writeReply" type="text" name="write" size="50"/>
		<input type="button" value="한줄댓글" onclick="writeToServer('${root }')"/>
		<!-- 문자열로 들어가기때문에 root가 싱글쿼테이션 -->		
	</div>
	
	<div>
		<!-- 실시간 댓글(새로운 글) -->
		
		<!-- 기존글 -->
	</div>
</body>
</html>