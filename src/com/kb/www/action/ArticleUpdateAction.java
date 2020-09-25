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

public class ArticleUpdateAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws Exception {

        String num = request.getParameter("num");   //수정할 글 번호(hidden) 받아옴
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
        ArticleVo vo = service.getArticleDetail(numInt); //action-service
        if (vo == null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.');location.href='/';</script>");
            out.close();
            return null;
        }


        ActionForward forward = new ActionForward();
        request.setAttribute("update",vo); //view에게 vo넘김
        forward.setPath("/views/update.jsp");
        return forward;
    }
}
