<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>

<title>Insert title here</title>
</head>
<body style="overflow:hidden;">


		<h1 align="center">欢迎${user.userName}登陆玩具之家</h1>

		<a href="<%=request.getContextPath()%>/user/esc">退出</a>
	
		<div id="datetime" align="right" style="color:black">
		 <script>
	 			setInterval("document.getElementById('datetime').innerHTML=new Date().toLocaleString();", 1000);
	 	  </script>
		</div>
		
		
</body>
</html>