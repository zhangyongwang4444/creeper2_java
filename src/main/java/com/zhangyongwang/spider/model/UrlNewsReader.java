package com.zhangyongwang.spider.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

//从链接中读取News
public class UrlNewsReader {
    public static NewsWithRelated read(String url) throws IOException {
        //Jsoup 读取和解析 页面


        // 看 jsoup 文档 ，敲代码 ，预计时间 2 天
        Document doc = Jsoup.connect(url).get();
        Elements titleElements = doc.select("title");  // <head><title></title></head>
        String title = titleElements.first().text();
        String content = doc.select("meta[name=description]").attr("content");

        // select 语法
        // 1. 标签名 "title", "meta", "meta"
        // 2. class ".main___dXbgk"
        // 3. id "#root"
        // 4. 属性 "[name=xxx]"

        /*
        Elements contentElements = doc.select(".summary___1i4y3");
        String content = contentElements.first().text();
        */

        NewsWithRelated news = new NewsWithRelated(url, title, content);

        Elements relatedElements = doc.select(".row___3h219");
        for (Element element : relatedElements) {
            String relatedTitle = element.select(".url___3iQHY").text();
            Elements children = element.children();
            String relatedUrl = children.get(0).absUrl("href");

            news.addRelated(relatedTitle, relatedUrl);
        }

        return news;
    }
}
