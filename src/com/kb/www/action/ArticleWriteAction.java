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

//        //글 제목,내용 view에서 받아오기
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");
//
//        //vo만들어서 제목,내용 담아서 service한테 보냄
//        ArticleVo vo = svc.getArticleWrite(title,content);
        ArticleVo vo = new ArticleVo();
        vo.setArticleTitle(request.getParameter("title"));
        vo.setArticleContent(request.getParameter("content"));

        ActionForward forward = new ActionForward();
        request.setAttribute("write", vo);
        forward.setPath("/views/write.jsp");

        return forward;
    }
}
