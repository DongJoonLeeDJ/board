package com.kb.www.service;

import com.kb.www.dao.BoardDAO;
import com.kb.www.vo.ArticleVo;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kb.www.common.JdbcUtil.*;

public class BoardService {

    //글 목록 메소드
    public ArrayList<ArticleVo> getArticleList() {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        ArrayList<ArticleVo> list = dao.getArticleList();   //DAO한테 다시 떠넘김

        close(con);

        return list;
    }

    //글 내용보기 메소드
    public ArticleVo getArticleDetail(int numInt) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        //DAO에 넘김
        ArticleVo vo = null;
        int count = dao.updateHitCount(numInt);
        if (count > 0) {
            commit(con);
            vo = dao.getArticleDetail(numInt);
        } else {
            rollback(con);
        }

        close(con);
        return vo;
    }

    //글쓰기 메소드
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
//            isSucess = false;
        }
        //DAO에 넘김
        close(con);
        return isSucess;
    }

    //글 삭제 메소드
    public boolean deleteArticle(int numInt) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        //그냥 count넘겨도 되지만 boolean으로 함
        boolean isSucess = false;

        int count = dao.deleteArticle(numInt);
        if (count > 0) {    //성공
            commit(con);
            isSucess = true;
        } else {          //실패
            rollback(con);
        }
        //DAO에 넘김
        close(con);
        return isSucess;
    }

    //글 수정 메소드
    public boolean updateArticle(ArticleVo vo) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        //그냥 count넘겨도 되지만 boolean으로 함
        boolean isSucess = false;

        int count = dao.updateArticle(vo);
        if (count > 0) {    //성공
            commit(con);
            isSucess = true;
        } else {          //실패
            rollback(con);
        }
        //DAO에 넘김
        close(con);
        return isSucess;
    }

    //회원가입 메소드
    public boolean joinMember(MemberVo memberVo, MemberHistoryVo memberHistoryVo) {
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        boolean isSucess = false;

        int count_01 = dao.insertMember(memberVo); //member 테이블에 id,pwd 들어가면 1
        memberHistoryVo.setMb_sq(dao.getMemberSequence(memberVo.getId()));
        int count_02 = dao.insertMemberHistory(memberHistoryVo);
        //성공
        if (count_01 > 0 && count_02 > 0) {
            commit(con);
            isSucess = true;
        } else {     //실패
            rollback(con);
        }
        close(con);
        return isSucess;
    }

}
