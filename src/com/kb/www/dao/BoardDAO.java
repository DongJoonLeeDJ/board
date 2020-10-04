package com.kb.www.dao;

import com.kb.www.vo.ArticleVo;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import javax.xml.transform.Result;

import static com.kb.www.common.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    //글 목록 쿼리
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

    //글 내용 쿼리
    public ArticleVo getArticleDetail(int numInt) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArticleVo vo = null;
        //데이터 담기
        try {
            pstmt = con.prepareStatement("select * from board where num=?");
            pstmt.setInt(1, numInt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vo = new ArticleVo();     //글 조회 돼야 인스턴스 생성
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

    //글 쓰기 쿼리
    public int insertArticle(ArticleVo vo) {
        PreparedStatement pstmt = null;
        int result = 0;
        //데이터 담기
        try {
            pstmt = con.prepareStatement("insert into board(subject,content) values (?,?) ");
            pstmt.setString(1, vo.getArticleTitle());
            pstmt.setString(2, vo.getArticleContent());
            result = pstmt.executeUpdate();  //insert된 컬럼 수를 return

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    //글 삭제 쿼리
    public int deleteArticle(int numInt) {
        PreparedStatement pstmt = null;
        int result = 0;
        //데이터 담기
        try {
            pstmt = con.prepareStatement("delete from board where num=?");
            pstmt.setInt(1, numInt);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    //글 수정 쿼리
    public int updateArticle(ArticleVo vo) {
        PreparedStatement pstmt = null;
        int result = 0;
        //데이터 담기
        try {
            pstmt = con.prepareStatement("update board set subject=?,content=?,updateDate=? where num=?");
            pstmt.setString(1, vo.getArticleTitle());
            pstmt.setString(2, vo.getArticleContent());
            pstmt.setString(3,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); //현재날짜를 수정날짜로 변경
            pstmt.setInt(4,vo.getArticleNum());
            result = pstmt.executeUpdate();  //insert된 컬럼 수를 return
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
    //조회수
    public int updateHitCount(int num) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("update board set hit=hit+1 where num=?");
            pstmt.setInt(1, num);
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    //회원가입
    public int insertMember(MemberVo vo){
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("insert into member(id,pwd) value(?,?)");
            pstmt.setString(1,vo.getId());
            pstmt.setString(2,vo.getPwd());
            count = pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int getMemberSequence(String id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int sq = 0;
        try {
            //vo의 getId에 해당하는 고유번호 조회
            pstmt = con.prepareStatement("select sq from member where id=?");
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                sq = rs.getInt("sq");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
        }
        return sq;
    }
    public int insertMemberHistory(MemberHistoryVo vo){
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("insert into member_history(mb_sq, evt_type) value(?,?)");
            pstmt.setInt(1,vo.getMb_sq());
            pstmt.setInt(2,vo.getEvt_type());
            count = pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }
}
