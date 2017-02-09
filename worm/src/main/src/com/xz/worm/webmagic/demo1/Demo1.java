package com.xz.worm.webmagic.demo1;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.SimplePageProcessor;

/**
 * falcon -- 2017/2/9.
 */
public class Demo1 {
    public static void main(String[] args) {
        Spider.create(new SimplePageProcessor("http://my.oschina.net/",
                "http://my.oschina.net/*/blog/*")).thread(2).run();
    }

}
