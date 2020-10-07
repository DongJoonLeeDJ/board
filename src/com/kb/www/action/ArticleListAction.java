package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.common.RegExp;
import com.kb.www.service.BoardService;
import com.kb.www.vo.ArticleVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.kb.www.common.RegExp.*;

public class ArticleListAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageNum = request.getParameter("pn");

        //숫자인지 아닌지
        if(pageNum==null||!RegExp.checkString(IS_NUMBER,pageNum)){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('페이지 넘버가 잘못됐습니다.');location.href='/login.do';</script>");
            out.close();
            return null;
        }

        int page=Integer.parseInt(pageNum);
        //페이지가 1보다 작으면
        if(page<1){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('페이지 넘버(int)가 잘못됐습니다.');location.href='/login.do';</script>");
            out.close();
            return null;
        }

        int rowNum=(page-1)*10;  //페이지넘버 정수로

        BoardService service = new BoardService();

        int articleCount = service.getArticleCount();    //글 총 갯수 불러오기
        int pageCount = (int)Math.ceil((double)articleCount/10);    //더블로 캐스팅 후 반올림 81 / 10 = 8.1 -> 9

        //service의 getArticleList로 리스트 불러오기 떠넘김
        //ArrayList<ArticleVo> articleList = service의 list
        ArrayList<ArticleVo> articleList = service.getArticleList(rowNum);

        ActionForward forward = new ActionForward();

        request.setAttribute("pageCount",pageCount);
        request.setAttribute("list",articleList);

        forward.setPath("/views/list.jsp");

        return forward;
    }
}
