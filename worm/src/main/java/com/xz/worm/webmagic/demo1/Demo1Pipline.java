package com.xz.worm.webmagic.demo1;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by xz on 2018/12/29.
 */
public class Demo1Pipline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("--"+((PlainText)resultItems.getAll().get("content")).get().substring(0,5));
    }
}
