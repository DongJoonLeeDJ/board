package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.common.BCrypt;
import com.kb.www.common.RegExp;
import com.kb.www.service.BoardService;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.kb.www.common.RegExp.MEMBER_ID;
import static com.kb.www.common.RegExp.MEMBER_PWD;
import static com.kb.www.constants.Constants.MEMBER_HISTORY_EVENT_JOIN;

public class MemberJoinProcAction implements Action {
    @Override
    public ActionForward execute
            (HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String pwd_confirm = request.getParameter("pwd_confirm");
        //아이디,패스워드 유효성 검사
        if (id==null ||  id.equals("")
                ||!RegExp.checkString(MEMBER_ID,id)
                ||pwd==null||pwd.equals("")
                ||!RegExp.checkString(MEMBER_PWD,pwd)){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다');location.href='/';</script>");
            out.close();
            return null;
        }
        //pwd,pwd확인 일치하는지 검사
        if(!pwd.equals(pwd_confirm)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다');location.href='/';</script>");
            out.close();
            return null;
        }

        //멤버vo에 값 set
        MemberVo memberVo = new MemberVo();
        memberVo.setId(id);
        memberVo.setPwd(BCrypt.hashpw(pwd,BCrypt.gensalt(12)));

        //멤버히스토리에 회원가입 상태 남김
        MemberHistoryVo memberHistoryVo = new MemberHistoryVo();
        memberHistoryVo.setEvt_type(MEMBER_HISTORY_EVENT_JOIN);

        BoardService service = new BoardService();
        //service호출
        if(!service.joinMember(memberVo,memberHistoryVo)) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('회원 가입에 실패하였습니다.');location.href='/';</script>");
            out.close();
            return null;
        }

        ActionForward forward = new ActionForward();
        forward.setPath("/");
        forward.setRedirect(true);
        return forward;
    }
}
