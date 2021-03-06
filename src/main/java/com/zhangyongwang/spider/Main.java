package com.zhangyongwang.spider;

import com.zhangyongwang.spider.model.UrlNewsReader;
import com.zhangyongwang.spider.model.Viewable;
import com.zhangyongwang.spider.view.ListViewer;
import com.zhangyongwang.spider.model.NewsWithRelated;

import java.util.*;

public class Main {

    // 本地存储新闻内容，如何在终端显示

    // 1. 抽象出 对象
    // 2. 设计 对象应该具有的属性，状态和行为
    // 3. 思考 对象之间交互
    // 4. 开始写代码

    private static final int maximumURL = 10;

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 广度优先搜索
        Queue<NewsWithRelated> newsQueue = new LinkedList<NewsWithRelated>();  // 共享

        String startUrl = "https://readhub.cn/topic/7Kwq2NDQYxp";   // 爬这里的内容 ....
        NewsWithRelated startNews = UrlNewsReader.read(startUrl);

        int count = 0;  // 共享
        Set<String> visited = new HashSet<>();     // 共享  // 标记 已经访问过的 URL
        visited.add(startUrl);
        newsQueue.add(startNews);
        ArrayList<Viewable> results = new ArrayList<>();   // 共享// 存储 扫描到的内容 （多态）

        // 广度优先搜索 的 model
        while (!newsQueue.isEmpty() && count <= maximumURL) {
            NewsWithRelated current = newsQueue.poll();
            results.add(current);
            count += 1;
            for (Map.Entry<String, String> entry : current.getRelateds().entrySet()) {
                String url = entry.getValue();

                //可以并行点
                NewsWithRelated next = UrlNewsReader.read(url);
                if (!visited.contains(url)) {
                    newsQueue.add(next);
                    visited.add(url);
                }
            }
        }


        long endTime = System.currentTimeMillis();

        new ListViewer(results).display();

       
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }
}

