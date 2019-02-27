package com.zhangyongwang.spider.model;

import java.util.*;

public class SearchSate {
    private final int maximumResults;

    private Queue<NewsWithRelated> newsQueue = new LinkedList<NewsWithRelated>();  // 共享
    private int count = 0;                                          // 只被主线程访问
    private Set<String> visited = new HashSet<>();                  // 共享
    private ArrayList<Viewable> results = new ArrayList<>();        // 只被主线程访问

    public SearchSate(int maxmumResults) {
        this.maximumResults = maxmumResults;
    }

    public synchronized NewsWithRelated poll() {
        return newsQueue.poll();
    }

    // 只被主线程访问  不用加  synchronized 不存在竞争
    public void addResult(NewsWithRelated result) {
        results.add(result);
        count += 1;
    }

    public synchronized void visit(NewsWithRelated news) {
        if (!visited.contains(news.getUrl())) {
            newsQueue.add(news);
            visited.add(news.getUrl());
        }
    }


    public synchronized boolean hasTarget() {
        return !newsQueue.isEmpty() && count < maximumResults;
    }

    // 只被主线程访问  不用加  synchronized 不存在竞争
    public ArrayList<Viewable> getResults() {
        return results;
    }
}
