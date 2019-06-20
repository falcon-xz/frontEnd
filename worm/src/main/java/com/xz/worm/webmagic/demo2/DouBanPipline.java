package com.xz.worm.webmagic.demo2;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by xz on 2018/12/29.
 */
public class DouBanPipline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {

        System.out.println("---"+((String)resultItems.getAll().get("movie")));
    }
}
