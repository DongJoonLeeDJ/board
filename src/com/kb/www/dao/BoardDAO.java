package com.kb.www.dao;

import com.kb.www.vo.ArticleVo;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.kb.www.common.JdbcUtil.close;

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

    //글 총 개수 구하기 (페이징)
    public int getArticleCount() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            pstmt = con.prepareStatement("select count(*) from board");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1); //첫번째 조회한거 = count
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return count;
    }

    //글 목록 쿼리
    public ArrayList<ArticleVo> getArticleList(int rowNum) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //리스트 객체 생성
        ArrayList<ArticleVo> list = new ArrayList<ArticleVo>();

        //데이터 담기
        try {
            pstmt = con.prepareStatement("select b.num" +
                    ",m.id" +
                    ",b.subject" +
                    ",b.content" +
                    ",b.hit" +
                    ",b.writeDate" +
                    ",b.updateDate" +
                    ",b.deleteDate " +
                    "from board b inner join member m on b.mb_sq = m.sq order by num desc " +
                    "limit ?,10 ");
            pstmt.setInt(1, rowNum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArticleVo vo = new ArticleVo();
                vo.setArticleNum(rs.getInt("num"));
                vo.setArticleTitle(rs.getString("subject"));
                vo.setArticleContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setId(rs.getString("id"));
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
            pstmt = con.prepareStatement("select b.num" +
                    ",b.mb_sq" +
                    ",b.subject" +
                    ",b.content" +
                    ",b.hit" +
                    ",b.writeDate" +
                    ",b.updateDate" +
                    ",b.deleteDate,m.id " +
                    "from board b inner join member m on b.mb_sq=m.sq " +
                    "where num=?");
            pstmt.setInt(1, numInt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vo = new ArticleVo();     //글 조회 돼야 인스턴스 생성
                vo.setArticleNum(rs.getInt("num"));
                vo.setArticleTitle(rs.getString("subject"));
                vo.setArticleContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setId(rs.getString("id"));
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
            pstmt = con.prepareStatement("insert into board(mb_sq, subject, content) values (?, ?, ?) ");
            pstmt.setInt(1, vo.getMb_sq()); //member 시퀀스번호를 글의 시퀀스번호로
            pstmt.setString(2, vo.getArticleTitle());
            pstmt.setString(3, vo.getArticleContent());
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
            pstmt.setInt(4, vo.getArticleNum());
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
        } finally {
            close(pstmt);
        }
        return count;
    }

    //회원가입
    public int insertMember(MemberVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("insert into member(id,pwd) value(?,?)");
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPwd());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count;
    }

    //아이디에 해당하는 시퀀스 가져오기
    public int getMemberSequence(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int sq = 0;
        try {
            //vo의 getId에 해당하는 고유번호 조회
            pstmt = con.prepareStatement("select sq from member where id=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sq = rs.getInt("sq");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return sq;
    }

    //회원가입,로그인할때 시퀀스,상태를 멤버히스토리에 입력
    public int insertMemberHistory(MemberHistoryVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("insert into member_history(mb_sq, evt_type) value(?,?)");
            pstmt.setInt(1, vo.getMb_sq());
            pstmt.setInt(2, vo.getEvt_type());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count;
    }

    //로그인 할 때 필요한(멤버가져오기)
    public MemberVo getMember(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MemberVo vo = null;

        try {
            pstmt = con.prepareStatement("select sq,id,pwd from member where binary(id)=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vo = new MemberVo();
                vo.setSq(rs.getInt("sq"));
                vo.setId(rs.getString("id"));
                vo.setPwd(rs.getString("pwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return vo;
    }

    //로그인 상태 변경
    public int updateLoginState(MemberVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("update member set lgn_fl=? where sq=?");
            pstmt.setBoolean(1, vo.isLgn_fl());
            pstmt.setInt(2, vo.getSq());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count;
    }

    //글 번호에 해당하는 member의 id값 가져오는 메소드
    public String getWriterId(int num) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String id = null;

        try {
            pstmt = con.prepareStatement("select m.id " +
                    "from board b " +
                    "inner join member m on b.mb_sq = m.sq " +
                    "where num=?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getString("id"); //멤버 id구해서 string에 담음
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return id;
    }

    //아직 이해못함...
    public ArrayList<MemberHistoryVo> getMemberHistory(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<MemberHistoryVo> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement("select mh.evt_type, mh.dttm " +
                    "from member m " +
                    "left join member_history mh on m.sq = mh.mb_sq " +
                    "where id=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemberHistoryVo vo = new MemberHistoryVo();
                vo.setEvt_type(rs.getInt("evt_type"));
                vo.setDttm(rs.getString("dttm"));
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
}
