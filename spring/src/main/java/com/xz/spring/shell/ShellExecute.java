package com.xz.spring.shell;

import jline.console.ConsoleReader;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * falcon -- 2017/1/4.
 */
@Configuration
@ComponentScan(basePackageClasses = {ShellExecute.class})
public class ShellExecute {

    public void run(String... strings) throws Exception {
        ConsoleReader reader = new ConsoleReader() ;
        reader.setExpandEvents(false);
        reader.setBellEnabled(false);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShellExecute.class).showBanner(false).run(args);
    }
}
