package com.kb.www.action;

import com.kb.www.common.*;
import com.kb.www.service.BoardService;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.kb.www.common.RegExp.MEMBER_ID;
import static com.kb.www.common.RegExp.MEMBER_PWD;
import static com.kb.www.constants.Constants.MEMBER_HISTORY_EVENT_LOGIN;

public class MemberLoginProcAction implements Action {
    @Override
    public ActionForward execute
            (HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        //아이디,패스워드 유효성 검사
        if (id == null || id.equals("")
                || !RegExp.checkString(MEMBER_ID, id)
                || pwd == null || pwd.equals("")
                || !RegExp.checkString(MEMBER_PWD, pwd)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다');location.href='/';</script>");
            out.close();
            return null;
        }
        BoardService service = new BoardService();
        MemberVo memberVo = service.getMember(id); //멤버가져오기
        //로그인폼에서 온 pwd와 Vo의 pwd비교
        if(memberVo==null||!BCrypt.checkpw(pwd,memberVo.getPwd())){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 정보를 확인해 주세요.');location.href='/';</script>");
            out.close();
            return null;
        }

        memberVo.setLgn_fl(true); //로그인 상태로 변경
        MemberHistoryVo memberHistoryVo = new MemberHistoryVo();
        memberHistoryVo.setMb_sq(memberVo.getSq()); //시퀀스 가져오기
        memberHistoryVo.setEvt_type(MEMBER_HISTORY_EVENT_LOGIN); //상태 저장

        //로그인에 실패하면 false
        if(!service.loginMember(memberVo,memberHistoryVo)){ //service 호출
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 처리에 실패하였습니다.');location.href='/';</script>");
            out.close();
            return null;
        }

        LoginManager lm = LoginManager.getInstance();
        //세션 세팅
        lm.setSession(request.getSession(),memberVo.getId());

        ActionForward forward = new ActionForward();
        forward.setPath("/");
        forward.setRedirect(true);
        return forward;
    }
}
