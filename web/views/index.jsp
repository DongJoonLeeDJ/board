<%@ page import="com.kb.www.common.LoginManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LoginManager lm = LoginManager.getInstance();
    String id = lm.getMemberId(session);
%>
<html>
<head>
</head>
<body>
<a href="/list.do?pn=1">게시판 보기</a>
<% //세션 null이면 회원가입,로그인 보이게
    if (id == null) { %>
<a href="/join.do">회원 가입</a>
<a href="/login.do">로그인</a>
<% } else { %>
<a href="/history.do">히스토리</a>
<a href="/logout.do">로그아웃</a>
<% } %>
</body>
</html>
