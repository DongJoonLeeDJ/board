package com.kb.www.dao;

import com.kb.www.vo.ArticleVo;

import javax.xml.transform.Result;

import static com.kb.www.common.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {
    private Connection con;

    //싱글턴 패턴 기본생성자를 private로 생성 불가능하게 함
    private BoardDAO() {

    }

    //getInstance를 통해서만 생성 가능하게 함
    public static BoardDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    //LazyHolder.INSTANCE를 참조하는 순간 Class가 로딩되며 초기화 진행
    private static class LazyHolder {
        private static final BoardDAO INSTANCE = new BoardDAO();
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

    //글 목록 확인
    public ArrayList<ArticleVo> getArticleList() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //리스트 객체 생성
        ArrayList<ArticleVo> list = new ArrayList<ArticleVo>();

        //데이터 담기
        try {
            pstmt = con.prepareStatement("select * from board");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArticleVo vo = new ArticleVo();
                vo.setArticleNum(rs.getInt("num"));
                vo.setArticleTitle(rs.getString("subject"));
                vo.setArticleContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setWriteDate(rs.getString("writeDate"));
                vo.setUpdateDate(rs.getString("UpdateDate"));
                vo.setDeleteDate(rs.getString("DeleteDate"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return list;
    }

    public ArticleVo getArticleDetail(String num) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArticleVo vo = new ArticleVo();
        //데이터 담기
        try {
            pstmt = con.prepareStatement("select * from board where num=" + num);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vo.setArticleNum(rs.getInt("num"));
                vo.setArticleTitle(rs.getString("subject"));
                vo.setArticleContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setWriteDate(rs.getString("writeDate"));
                vo.setUpdateDate(rs.getString("UpdateDate"));
                vo.setDeleteDate(rs.getString("DeleteDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return vo;
    }
}
