<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticleVo vo = (ArticleVo) request.getAttribute("write");
%>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form method="post" action="write">
    <table style="border: 1px solid cadetblue; text-align: center; margin: auto">
        <thead><td>글 쓰기</td></thead>
        <tr>
            <td><input type="text" placeholder="글 제목" name="title" maxlength="50" style="width: 400px"></td>
        </tr>

        <tr>
            <td><textarea placeholder="글 내용" name="content" maxlength="2048"
                          style="height: 350px; width: 500px"></textarea></td>
        </tr>
        <tr>
            <td><input type="submit" value="글쓰기"></td>
        </tr>
    </table>
</form>
</body>
</html>
