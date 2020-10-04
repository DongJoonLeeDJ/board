package com.kb.www.common;

import com.kb.www.action.MemberLogoutAction;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Enumeration;
import java.util.Hashtable;

public class LoginManager implements HttpSessionBindingListener {
    private static Hashtable loginUsers = new Hashtable();

    private LoginManager() {
        super();
    }

    public static LoginManager getInstance() {
        return LoginManager.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LoginManager INSTANCE = new LoginManager();
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        loginUsers.put(event.getSession(), event.getName());
    }

    //직접 로그아웃 누르지않아도
    //컴퓨터 종료,세션 만료,브라우저 종료 시 로그아웃되기 때문에 따로 만듬
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        Action action = new MemberLogoutAction();
        ((MemberLogoutAction) action).logoutProc(getMemberId(event.getSession()));
    }

    //해쉬테이블 순차적으로 배치시킴,while 키 값이 존재하는동안,ession에 키 값 받아옴
    public void removeSession(String id) {
        Enumeration e = loginUsers.keys();
        HttpSession session = null;
        while (e.hasMoreElements()) {
            session = (HttpSession) e.nextElement();

            if (loginUsers.get(session).equals(id)) {
                session.invalidate();
            }
        }
    }

    //세션을 string으로 받음,세션아이디와 발급받은 세션아이디 같으면 isLogin = true
    public boolean isLogin(String sessionId) {
        boolean isLogin = false;
        Enumeration e = loginUsers.keys();
        String key = "";
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            if (sessionId.equals(key)) {
                isLogin = true;
            }
        }
        return isLogin;
    }

    public void setSession(HttpSession session, String id) {
        session.setAttribute(id, this);
    }

    public String getMemberId(HttpSession session) {
        return (String) loginUsers.get(session);
    }
}
