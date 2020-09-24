package com.kb.www.service;

import com.kb.www.dao.BoardDAO;
import com.kb.www.vo.ArticleVo;
import static com.kb.www.common.JdbcUtil.close;
import static com.kb.www.common.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

public class BoardService {
    //글 목록
    public ArrayList<ArticleVo> getArticleList(){
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        //DAO한테 다시 떠넘김
        ArrayList<ArticleVo> list = dao.getArticleList();

        close(con);

        return list;
    }

    //글 내용
    public ArticleVo getArticleDetail(String num){
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        //DAO에 넘김
        ArticleVo vo = dao.getArticleDetail(num);
        close(con);

        return vo;
    }
}
