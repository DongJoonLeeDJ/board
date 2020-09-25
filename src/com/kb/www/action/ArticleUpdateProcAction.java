package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.common.RegExp;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.kb.www.common.RegExp.*;

public class ArticleUpdateProcAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws Exception {

        String num = request.getParameter("num");   //list.jsp
        //글 번호 유효성검사(1)-형변환 전,RegExp = 글 번호 유효성 검사
        if (num == null||num.equals("")|| !RegExp.checkString(PAGE_NUM,num)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.1');history.back();</script>");
            out.close();
            return null;
        }

        int numInt = Integer.parseInt(num);  //유효성 검사 후 글 번호 숫자로 변환
        //글 번호 유효성검사(2)-형변환 후,글 번호 0보다 작으면 오류alert
        if(numInt<=0){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.2');history.back();</script>");
            out.close();
            return null;
        }
        //view에서 제목,내용 받아옴
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        //글 번호 유효성검사,RegExp = 글 번호 유효성 검사
        if (title == null || content == null
                || title.equals("") || content.equals("")
                || !RegExp.checkString(ARTICLE_TITLE, title) ||!RegExp.checkString(ARTICLE_CONTENT, content)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.');history.back();</script>");
            out.close();
            return null;
        }

        //vo에 담음
        ArticleVo vo = new ArticleVo();
        vo.setArticleNum(numInt);
        vo.setArticleTitle(title);
        vo.setArticleContent(content);

        BoardService service = new BoardService();
        //글 성공(true) , 실패(boolean)받음
        if(!service.updateArticle(vo)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('글 저장에 실패했습니다.');history.back();</script>");
            out.close();
            return null;
        }

        ActionForward forward = new ActionForward();
        forward.setPath("/detail.do"); //수정한 글번호에 해당하는 글 봐야함
        forward.setRedirect(true);
        return forward;
    }
}
