package com.zhangyongwang.spider.activiy;

import com.zhangyongwang.spider.model.SearchSate;
import com.zhangyongwang.spider.model.UrlNewsReader;
import com.zhangyongwang.spider.model.NewsWithRelated;

import java.io.IOException;

public class SpiderThread extends Thread {

    private String url;
    private SearchSate searchSate;

    public SpiderThread(SearchSate searchSate, String url) {
        this.url = url;
        this.searchSate = searchSate;
        //System.out.println("Start reading URL: " + this.url);
        start();
    }

    @Override
    public void run() {
        try {
            NewsWithRelated next = UrlNewsReader.read(url);  //  涉及网络访问 ，耗时
            searchSate.visit(next);
        } catch (IOException e) {
            System.out.println("Ignored an error page: " + url);
        }
    }
}
