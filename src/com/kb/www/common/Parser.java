package com.kb.www.common;

import static com.kb.www.constants.Constants.*;

public class Parser {
    public static String parseMemberEventType(int num) {
        String event = "";
        switch (num) {
            case MEMBER_HISTORY_EVENT_JOIN:
                event = "회원가입";
                break;
            case MEMBER_HISTORY_EVENT_UPDATE:
                event = "회원정보수정";
                break;
            case MEMBER_HISTORY_EVENT_LEAVE:
                event = "회원탈퇴";
                break;
            case MEMBER_HISTORY_EVENT_LOGIN:
                event = "로그인";
                break;
            case MEMBER_HISTORY_EVENT_LOGOUT:
                event = "로그아웃";
                break;
        }
        return event;
    }
}
