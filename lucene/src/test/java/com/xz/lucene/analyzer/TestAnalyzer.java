package com.xz.lucene.analyzer;

import com.sun.deploy.config.DefaultConfig;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

/**
 * Created by xz on 2018/12/14.
 */
public class TestAnalyzer {
    private String content = "测试1下就好 wAiting马上";

    /**
     * 标准分词器 一个字一个字分词
     */
    @Test
    public void TestStandardAnalyzer() {
        String s = "用户名" ;
        Reader reader = new InputStreamReader(TestAnalyzer.class.getClassLoader().getResourceAsStream("black.txt"));
        Analyzer analyzer = null;
        TokenStream tokenStream = null;
        try {
            analyzer = new StandardAnalyzer(reader);
            tokenStream = analyzer.tokenStream("", new StringReader(s));
            //关键词的引用
            CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
            //偏移量的引用
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.print("[" + cta + ":" + offsetAttribute.startOffset() + "-" + offsetAttribute.endOffset() + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (analyzer!=null){
                analyzer.close();
            }
        }
    }

    /**
     * 中文分词器
     */
    @Test
    public void SmartChineseAnalyzer() {
        CharArraySet stopWords = new CharArraySet(0, false);
        stopWords.add("1");
        stopWords.add("好");
        Analyzer analyzer = new SmartChineseAnalyzer(stopWords);
        TokenStream tokenStream = null;
        try {
            tokenStream = analyzer.tokenStream("", new StringReader(content));
            //关键词的引用
            CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
            //偏移量的引用
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.print("[" + cta + ":" + offsetAttribute.startOffset() + "-" + offsetAttribute.endOffset() + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            analyzer.close();
        }
    }

    /**
     * 空格分词器
     */
    @Test
    public void WhitespaceAnalyzer() {
        Analyzer analyzer = new WhitespaceAnalyzer();
        TokenStream tokenStream = null;
        try {
            tokenStream = analyzer.tokenStream("", new StringReader(content));
            //关键词的引用
            CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
            //偏移量的引用
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            //类型的引用
            TypeAttribute type = tokenStream.addAttribute(TypeAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.print("[" + cta + ","+type.type()+":" + offsetAttribute.startOffset() + "-" + offsetAttribute.endOffset() + "]");
            }
            tokenStream.end();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            analyzer.close();
        }
    }


}
