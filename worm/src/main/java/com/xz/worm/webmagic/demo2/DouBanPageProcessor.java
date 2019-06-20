package com.xz.worm.webmagic.demo2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 豆瓣的简单爬虫
 * Created by xz on 2018/12/22.
 */
public class DouBanPageProcessor implements PageProcessor{
    // https://movie.douban.com/review/best/
    private static final String welcome_regex = "(.*)movie.douban.com/review/best/?(.*)";//影评欢迎页地址正则
    private static final String info_regex = "(.*)movie.douban.com/review/(.*)/";//影评明细地址正则
    // 部分一：抓取网站的相关配置，包括编码、重试次数、抓取间隔、超时时间、请求消息头、UA信息等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000).setTimeOut(6000)
            .addHeader("Accept-Encoding", "/").setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");

    private Pattern pattern = Pattern.compile("[?]") ;

    @Override
    public Site getSite() {
        return site;
    }


    @Override
    public void process(Page page) {
        try {
            //影评欢迎列表页
            if (page.getUrl().regex(welcome_regex).match()) {
                String urlStr =page.getUrl().toString();
                URL url = new URL(urlStr) ;
                String protocolDomain = HttpUrlUtils.getDomain(url);
                String agrs = url.getQuery() ;
                List<String> Urllist = getInfoUrl(protocolDomain,page,agrs);
                page.addTargetRequests(Urllist);//添加地址，根据url对该地址处理
            }else if (page.getUrl().regex(info_regex).match()){
                getInfo(page);
            }
            //可增加else if 处理不同URL地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInfo(Page page) {
        String pageStr = page.getRawText() ;
        Document document = Jsoup.parse(pageStr);
        Elements elements = document.getElementsByClass("article") ;
        for (int i = 0; i < elements.size(); i++) {
            String threadName = Thread.currentThread().getName() ;
            Elements titleElement = elements.get(i).getElementsByTag("h1").select("span") ;
            String movie = elements.get(i).getElementsByClass("main-hd").select("a").get(1).text() ;
            System.out.println(threadName+"--"+movie+"--"+titleElement.text());
            page.putField("movie",movie);
        }
    }


    private List<String> getInfoUrl(String protocolDomain,Page page ,String agrs) {
        String pageStr = page.getRawText() ;
        List<String> urlList = new ArrayList<>();
        Document document = Jsoup.parse(pageStr);
        //处理影评链接
        Elements movieElements = document.getElementsByClass("main-bd") ;
        for (int i = 0; i < movieElements.size(); i++) {
            Elements aTagElement = movieElements.get(i).getElementsByTag("h2").select("a") ;
            urlList.add(aTagElement.attr("href"));
        }
        //处理分页
        Elements pageElements = document.getElementsByClass("paginator") ;
        Elements thisPageElements = pageElements.get(0).getElementsByClass("thispage");
        Element nextElement = thisPageElements.get(0).nextElementSibling() ;
        if (nextElement.tagName().equals("a")){
            String threadName = Thread.currentThread().getName() ;
            String nextUrl =  nextElement.attr("href") ;
            String nextPageUrl = protocolDomain+nextUrl ;
            urlList.add(nextPageUrl);
            System.out.println(threadName+"++++++++++++"+nextPageUrl);
        }
        return urlList;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new DouBanPageProcessor()).addUrl("https://movie.douban.com/review/best/");
        spider.addPipeline(new DouBanPipline()) ;
        spider.thread(3).run();
    }

}
