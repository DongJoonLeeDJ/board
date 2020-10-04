<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page import="com.kb.www.common.LoginManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticleVo vo = (ArticleVo) request.getAttribute("vo");
    LoginManager lm=LoginManager.getInstance();
    String id=lm.getMemberId(session);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>상세보기</title>
</head>
<body>
<table style="border: 1px solid cadetblue; text-align: center; margin: auto">
    <tr>
        <td>글번호</td>
        <td><%=vo.getArticleNum()%></td>
    </tr>
    <tr>
        <td>글제목</td>
        <td><%=vo.getArticleTitle()%></td>
    </tr>
    <tr>
        <td>작성일</td>
        <td><%=vo.getWriteDate()%></td>
    </tr>
    <tr>
        <td>글내용</td>
        <td><%=vo.getArticleContent()%></td>
    </tr>
</table>
<button onclick="location.href='/list.do'">뒤로 가기</button>
<% //세션아이디와 vo아이디 일치하면
    if (id.equals(vo.getId())) { %>
<button onclick="location.href='/update.do?num=<%=vo.getArticleNum()%>'">수정</button>
<button onclick="location.href='/delete.do?num=<%=vo.getArticleNum()%>'">삭제</button>
<% } %>
</body>
</html>
