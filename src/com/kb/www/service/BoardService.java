package com.kb.www.service;

import com.kb.www.dao.BoardDAO;
import com.kb.www.vo.ArticleVo;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

import static com.kb.www.common.JdbcUtil.*;

public class BoardService {
    public int getArticleCount() {
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        int count = dao.getArticleCount();
        close(con);
        return count;
    }
    //글 목록 메소드
    public ArrayList<ArticleVo> getArticleList(int rowNum) {
        //세팅
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        ArrayList<ArticleVo> list = dao.getArticleList(rowNum);   //DAO한테 다시 떠넘김

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

        int count = dao.deleteArticle(numInt);  //dao 호출
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

    //로그인할때 필요한 메소드
    public MemberVo getMember(String id) {
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        MemberVo vo = dao.getMember(id); //dao에서 시퀀스,id,pwd 가져오기
        close(con);
        return vo;
    }

    //로그인
    public boolean loginMember(MemberVo memberVo, MemberHistoryVo memberHistoryVo){
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        boolean isSucess=false;

        int count_01=dao.updateLoginState(memberVo);
        int count_02=dao.insertMemberHistory(memberHistoryVo);
        if(count_01>0&&count_02>0){
            commit(con);
            isSucess = true;
        }else{
            rollback(con);
        }
        close(con);
        return isSucess;
    }
    public boolean logoutMember(MemberVo memberVo, MemberHistoryVo memberHistoryVo){
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        boolean isSucess=false;

        memberVo.setSq(dao.getMemberSequence(memberVo.getId())); //아이디로 vo의 시퀀스가져와서
        memberHistoryVo.setMb_sq(memberVo.getSq()); //히스토리에 저장

        int count_01=dao.updateLoginState(memberVo);
        int count_02=dao.insertMemberHistory(memberHistoryVo);
        if(count_01>0&&count_02>0){
            commit(con);
            isSucess = true;
        }else{
            rollback(con);
        }
        close(con);
        return isSucess;
    }
    //글 등록할 때
    public int getMemberSequence(String id){
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        int sq = dao.getMemberSequence(id); //dao에서 가꼬옴
        close(con);
        return sq;
    }

    //작성자 id구하는 메소드
    public String getWriterId(int num){
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);

        String id=dao.getWriterId(num); //dao에서 구함

        close(con);
        return id;
    }

    //히스토리 구하는 메소드
    public ArrayList<MemberHistoryVo> getMemberHistory(String id){
        BoardDAO dao = BoardDAO.getInstance();
        Connection con = getConnection();
        dao.setConnection(con);
        ArrayList<MemberHistoryVo> list = dao.getMemberHistory(id); //dao호출
        close(con);
        return list;
    }
}
