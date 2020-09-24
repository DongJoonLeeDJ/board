package com.kb.www.controller;

import com.kb.www.action.ArticleListAction;
import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomeController extends HttpServlet {
    public HomeController() {
        super();
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        ActionForward forward = null;
        Action action = new ArticleListAction();

        //이벤트 처리 컨트롤러
        try {
            forward = action.execute(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
        //forward에 뭔가가 담겨있다면
        if (forward!=null){
            //Path구해서 이동
            if(forward.isRedirect()){
                response.sendRedirect(forward.getPath());
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
                dispatcher.forward(request,response);
            }
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }
}
