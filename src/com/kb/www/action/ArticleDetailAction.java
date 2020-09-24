package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArticleDetailAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        BoardService svc = new BoardService();

        //list.jsp
        String num = request.getParameter("num");
        //글 번호 숫자로 변환
        int numInt = Integer.parseInt(num);
        //VO만들어서 글 번호를 service에 보냄
        ArticleVo vo = svc.getArticleDetail(numInt);

        ActionForward forward = new ActionForward();
        request.setAttribute("detail",vo);
        forward.setPath("/views/detail.jsp");

        return forward;


    }
}
