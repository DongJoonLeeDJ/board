package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArticleWriteAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        BoardService svc = new BoardService();
        ArticleVo vo = svc.getArticleWrite();

        ActionForward forward = new ActionForward();
        request.setAttribute("write",vo);
        forward.setPath("/views/write.jsp");

        return forward;
    }
}
