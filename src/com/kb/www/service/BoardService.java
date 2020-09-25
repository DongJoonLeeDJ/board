package com.kb.www.service;

import com.kb.www.dao.BoardDAO;
import com.kb.www.vo.ArticleVo;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kb.www.common.JdbcUtil.*;

public class BoardService {
    //글 목록
    public ArrayList<ArticleVo> getArticleList() {
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
    public ArticleVo getArticleDetail(int numInt) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        //DAO에 넘김
        ArticleVo vo = dao.getArticleDetail(numInt);
        close(con);

        return vo;
    }

    //글쓰기
    public boolean insertArticle(ArticleVo vo) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        //그냥 count넘겨도 되지만 boolean으로 함
        boolean isSucess = false;

        int count = dao.insertArticle(vo);
        if (count > 0) {    //성공
            commit(con);
            isSucess = true;
        } else {          //실패
            rollback(con);
            isSucess = false;
        }
        //DAO에 넘김
        close(con);
        return isSucess;
    }


}
