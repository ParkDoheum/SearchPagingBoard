<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<nav>
	헤더입니다.
	</nav>
	<div>
		<jsp:include page="${target}" />
	</div>	
	<footer>
	푸터입니다.
	</footer>
</body>
</html>