<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticleVo vo = (ArticleVo) request.getAttribute("detail");
%>
<html>
<head>
    <meta charset="UTF-8">
    <style>

    </style>
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
<button onclick="history.back()">뒤로 가기</button>
</body>
</html>
