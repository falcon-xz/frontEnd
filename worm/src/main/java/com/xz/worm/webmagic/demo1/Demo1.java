package com.xz.worm.webmagic.demo1;

import com.xz.worm.webmagic.demo2.DouBanPipline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.SimplePageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * falcon -- 2017/2/9.
 */
public class Demo1 {


    public static void main(String[] args) {
        Spider spider = Spider.create(new SimplePageProcessor("http://my.oschina.net/*/blog/*"));
        spider.addUrl("http://my.oschina.net/");
        spider.addPipeline(new Demo1Pipline()) ;
        spider.thread(3).run();
    }

}
