package com.kb.www.common;

import java.util.regex.Pattern;

//정규 표현식 검사 클래스
public class RegExp {
    public static final int PAGE_NUM = 0; //상수로 사용,switch문에서 숫자로하면 뭐가뭔지 모름
    public static final int ARTICLE_TITLE = 1;
    public static final int ARTICLE_CONTENT = 2;

    public static final String EXP_PAGE_NUM = "[0-9]*$"; //숫자 비교
    public static final String EXP_ARTICLE_TITLE = "^.{1,100}$"; //글 제목 100자까지 인지
    public static final String EXP_ARTICLE_CONTENT = "^.{1,65535}$"; //글 내용

    public static boolean checkString(int type, String data) { //타입,비교할 데이터
        boolean result = false;
        //타입 검사
        switch (type) {
            case PAGE_NUM:
                result = Pattern.matches(EXP_PAGE_NUM, data);
                break;
            case ARTICLE_TITLE:
                result = Pattern.matches(EXP_ARTICLE_TITLE, data);
                break;
            case ARTICLE_CONTENT:
                result = Pattern.matches(EXP_ARTICLE_CONTENT, data);
                break;
        }
        return result;
    }
}
