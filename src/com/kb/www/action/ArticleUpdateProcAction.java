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

        //수정할 제목,내용 view에서 불러옴
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        //글 번호 유효성검사(1)-형변환 전,RegExp = 글 번호 유효성 검사
        if (title == null || content == null
                || title.equals("") || content.equals("")
                || !RegExp.checkString(ARTICLE_TITLE, title) ||!RegExp.checkString(ARTICLE_CONTENT, content)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.');history.back();</script>");
            out.close();
            return null;
        }

        String num = request.getParameter("num"); //수정할 글 번호 불러옴
        int numInt = Integer.parseInt(num);  //유효성 검사 후 글 번호 숫자로 변환
        //글 번호 유효성검사(2)-형변환 후,글 번호 0보다 작으면 오류alert
        if(numInt<=0){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.(2)');history.back();</script>");
            out.close();
            return null;
        }

        //vo에 수정한 컬럼들 내용넣기
        ArticleVo vo = new ArticleVo();
        vo.setArticleNum(numInt);
        vo.setArticleTitle(title);
        vo.setArticleContent(content);

        BoardService service = new BoardService();
        //글수정 성공(true) , 실패(false) boolean값 받음
        if(!service.updateArticle(vo)){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('글을 수정하는데 실패하였습니다.');location.href='/';</script>");
            out.close();
            return null;
        }

        ActionForward forward = new ActionForward();
        forward.setPath("/detail.do?num="+numInt);
        forward.setRedirect(true);
        return forward;
    }
}
