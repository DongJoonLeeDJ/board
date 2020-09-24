package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArticleWriteAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        BoardService svc = new BoardService();

        ActionForward forward = new ActionForward();
        //request.setAttribute("detail",vo);
        forward.setPath("/views/write.jsp");

        return forward;
    }
}
