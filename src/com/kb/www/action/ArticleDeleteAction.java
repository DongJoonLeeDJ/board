package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.common.RegExp;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.kb.www.common.RegExp.PAGE_NUM;

public class ArticleDeleteAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String num = request.getParameter("num"); //삭제할 글 번호 받아오기
        //글 번호 유효성검사(1)-형변환 전,RegExp = 글 번호 유효성 검사
        if (num == null||num.equals("")|| !RegExp.checkString(PAGE_NUM,num)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.(1)');history.back();</script>");
            out.close();
            return null;
        }

        int numInt = Integer.parseInt(num);  //유효성 검사 후 글 번호 숫자로 변환
        //글 번호 유효성검사(2)-형변환 후,글 번호 0보다 작으면 오류alert
        if(numInt<=0){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.(2)');history.back();</script>");
            out.close();
            return null;
        }

        BoardService service = new BoardService();
        //글 성공(true) , 실패(boolean)받음
        if(!service.deleteArticle(numInt)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('글 삭제에 실패했습니다.');history.back();</script>");
            out.close();
            return null;
        }
        ActionForward forward = new ActionForward();
//        request.setAttribute("detail", vo); 받아올거 없으므로 지움
        forward.setPath("/list.do");
        forward.setRedirect(true);

        return forward;
    }
}
