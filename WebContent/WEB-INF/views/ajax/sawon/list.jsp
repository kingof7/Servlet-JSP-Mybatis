<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath }" />
<html>
<head>
<script type="text/javascript" src="${root}/javascript/sawon/sawon.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="sawonForm">
		<select name="departmentName" onchange="toServer('${root}')">
			<option>부서선택하세요.</option>
			<option value="Marketing">Marketing</option>
			<option value="IT">IT</option>
			<option value="Sales">Sales</option>
		</select>
	</form>
	<div id="resultDisp"></div>
</body>
</html>