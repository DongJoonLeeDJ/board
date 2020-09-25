<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticleVo vo = (ArticleVo) request.getAttribute("update");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>글쓰기</title>
    <!-- jQuery CDN -->
    <script
            src="https://code.jquery.com/jquery-3.5.1.slim.js"
            integrity="sha256-DrT5NfxfbHvMHux31Lkhxg42LY6of8TaYyK50jnxRnM="
            crossorigin="anonymous"></script>
    <script>
        function checkData() {
            var title = $('#title').val();
            if(!title) {
                alert("제목을 입력하세요");
                $('#title').focus();
                return false;
            }
            var content = $('#content').val();
            if(!content) {
                alert("내용을 입력하세요");
                $('#content').focus();
                return false;
            }
        }
    </script>
</head>
<body>
<!-- onsubmit - submit누르면 checkData함수로 감-->
<form method="post" action="/updateProc.do" onsubmit="return checkData()">
    <table style="border: 1px solid cadetblue; text-align: center; margin: auto">
        <thead><td>글 쓰기</td></thead>
        <tr>
            <td><input type="text" id="title" name="title"
                       maxlength="100" placeholder="글 제목" style="width: 400px " value="<%=vo.getArticleTitle()%>"></td>
        </tr>

        <tr>
            <td><textarea id="content" name="content" placeholder="글 내용"
                          style="height: 350px; width: 500px"><%=vo.getArticleContent()%></textarea></td>
        </tr><tr>
        <td><input type="hidden" value="<%=vo.getArticleNum()%>" name="num"></td>
    </tr>
        <tr>
            <td><input type="submit" value="수정"></td>
        </tr>
    </table>
</form>
</body>
</html>
