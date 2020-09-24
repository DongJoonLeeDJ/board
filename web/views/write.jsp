<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
<style>
    h1 {
        width: 200px;
        margin-left: auto;
        margin-right: auto;
    }
</style>
</head>
<body>
<h1>글 쓰기</h1>
<form method="post" action="list">
    <table style="border: 1px solid cadetblue; text-align: center; margin: auto">
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
