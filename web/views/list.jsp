<%@ page import="java.util.ArrayList" %>
<%@ page import="com.kb.www.vo.ArticleVo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<ArticleVo> list = (ArrayList<ArticleVo>) request.getAttribute("list");
    int pageCount = (int) request.getAttribute("pageCount");
    String nowPage = request.getParameter("pn");    //현재 페이지 들고 가야함
%>
<html>
<head>
    <meta charset="UTF-8">
    <script>
        function showDetail(num) {
            location.href = "/detail.do?pn=" + <%=nowPage%> + "&num=" + num;
        }

    </script>
</head>
<body>
<table style="border: 1px solid cadetblue; text-align: center; margin: auto">
    <tr>
        <td>번호</td>
        <td>제목</td>
        <td>조회수</td>
        <td>작성자</td>
        <td>작성일</td>
    </tr>
    <!-- 데이터 있을때만 보이게 -->
    <% if (list.size() > 0) {%>
    <% for (int i = 0; i < list.size(); i++) { %>
    <tr>
        <td><%=list.get(i).getArticleNum()%>
        </td>
        <td onclick="showDetail(<%=list.get(i).getArticleNum()%>)"><%=list.get(i).getArticleTitle()%>
        </td>
        <td><%=list.get(i).getHit()%>
        </td>
        <td><%=list.get(i).getId() %>
        </td>
        <td><%=list.get(i).getWriteDate()%>
        </td>
    </tr>
    <% } %>
    <% } else { %>
    <tr>
        <td>게시글이 없습니다.</td>
    </tr>
    <% } %>
    <%
        //페이지번호 1부터 페이지번호까지
        for (int i = 1; i <= pageCount; i++) {
    %>
    <span><a href="/list.do?pn=<%=i%>"><%=i%></a>
    </span>
    <% } %>


    <tr>
        <td>
            <button onclick="location.href='/write.do'">글쓰기</button>
        </td>
    </tr>
</table>
<a href="write.do">a 태그 글쓰기</a>
<button onclick="location.href='/'">홈으로</button>

</body>
</html>
