package com.kb.www.controller;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.action.ArticleDetailAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        ActionForward forward = null;
        Action action = new ArticleDetailAction();
        // 이벤트 처리 컨트롤러
        try {
            //forward = new ActionForward();
            //action으로 떠넘긴다
            forward = action.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //forward에 뭔가가 담겨있다면
        if (forward != null) {
            if (forward.isRedirect()) {
                //Path구해서 이동
                response.sendRedirect(forward.getPath());
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
                dispatcher.forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }
}
