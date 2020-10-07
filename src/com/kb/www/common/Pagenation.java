package com.kb.www.common;

public class Pagenation {
    private final int SHOW_ARTICLE_COUNT = 10;   //한 페이지에 보여줄 글 개수
    //현재 페이지
    private int nowPageNum;    //현재 페이지 번호
    private int totalArticleCount;   //총 글 개수
    private int startArticleNumber; //시작 글 번호 limit (?,10)
    private int totalPageCount;    //총 페이지 개수


    //시작 페이지, 끝 페이지
    private int startPage;
    private int endPage;

    public Pagenation(int nowPageNum,int totalArticleCount){
        this.nowPageNum = nowPageNum;
        this.startArticleNumber = (nowPageNum - 1)* SHOW_ARTICLE_COUNT;
        this.totalArticleCount = totalArticleCount;
//        this.totalPageCount = (int)Math.ceil()
    }

}
