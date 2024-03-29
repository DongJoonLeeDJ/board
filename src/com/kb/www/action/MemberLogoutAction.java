package com.kb.www.action;

import com.kb.www.common.Action;
import com.kb.www.common.ActionForward;
import com.kb.www.common.LoginManager;
import com.kb.www.service.BoardService;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static com.kb.www.constants.Constants.MEMBER_HISTORY_EVENT_LOGOUT;
public class MemberLogoutAction implements Action {
    @Override
    public ActionForward execute
            (HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        LoginManager lm = LoginManager.getInstance();
        String id = lm.getMemberId(session);
        if (id != null) {
            lm.removeSession(id); //세션뺏음,로그아웃 처리
        }
        ActionForward forward = new ActionForward();
        forward.setPath("/");
        forward.setRedirect(true);
        return forward;
    }
    public void logoutProc(String id){
        MemberVo memberVo = new MemberVo();
        memberVo.setId(id);
        memberVo.setLgn_fl(false); //로그아웃 상태로 변경

        MemberHistoryVo memberHistoryVo = new MemberHistoryVo();
        memberHistoryVo.setEvt_type(MEMBER_HISTORY_EVENT_LOGOUT);

        BoardService service = new BoardService();
        if(!service.logoutMember(memberVo,memberHistoryVo)){ //service 호출
            System.out.println(id + "회원의 로그아웃 처리에 실패하였습니다.");
        }
    }
}
