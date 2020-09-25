package com.kb.www.controller;

import com.kb.www.action.ArticleDetailAction;
import com.kb.www.action.ArticleListAction;
import com.kb.www.action.ArticleRegisterAction;
import com.kb.www.action.ArticleWriteAction;
import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
    public BoardController() {
        super();
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");

        String RequestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = RequestURI.substring(contextPath.length());

        ActionForward forward = null;
        Action action = null;

        if(command.equals("/list.do")) {
            action = new ArticleListAction();
            try{
                forward = action.execute(request,response);
            }catch (Exception e){
                e.printStackTrace();
            }
            //detailController 필요없이 조건문으로 바로 이동
        } else if(command.equals("/detail.do")) {
            action = new ArticleDetailAction();
            try{
                forward = action.execute(request,response);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(command.equals("/write.do")) {
            action = new ArticleWriteAction();
            try{
                forward = action.execute(request,response);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(command.equals("/register.do")) {
            action = new ArticleRegisterAction();
            try{
                forward = action.execute(request,response);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(forward!=null){
            if(forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
            }else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
                dispatcher.forward(request,response);
            }
        }
        //이벤트 처리 컨트롤러
       /* try {
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
        }*/
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }
}
