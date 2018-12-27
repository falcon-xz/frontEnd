package com.xz.worm.webmagic.demo1;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.SimplePageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * falcon -- 2017/2/9.
 */
public class Demo1 {


    public static void main(String[] args) {
        Spider.create(new SimplePageProcessor("http://my.oschina.net/","http://my.oschina.net*//*//*blog*//*")).thread(1).run();
        //Spider.create(new GithubRepoPageProcessor()).addUrl(new String[]{"https://github.com/code4craft"}).thread(2).run();
    }

}
