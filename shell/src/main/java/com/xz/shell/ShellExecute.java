package com.xz.shell;

import jline.console.ConsoleReader;
import jline.console.history.FileHistory;
import jline.console.history.History;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Map;

/**
 * falcon -- 2017/1/4.
 */
@Configuration
@ComponentScan(basePackageClasses = {ShellExecute.class})
public class ShellExecute {
    private String historyFile = ".xzShellConf";

    public void run(String... strings) throws Exception {
        ConsoleReader reader = new ConsoleReader();
        //history {use_home}/{historyFile}
        reader.setHistoryEnabled(true);
        File fileHistory = new File(System.getProperty("user.home") + File.separator + historyFile);
        History history = new FileHistory(fileHistory);
        reader.setHistory(history);


        reader.setExpandEvents(false);
        reader.setBellEnabled(false);


    }

    public static void main(String[] args) {
        for (Map.Entry entry : System.getProperties().entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println(key + "--" + value);
        }


        new SpringApplicationBuilder(ShellExecute.class).showBanner(false).run(args);
    }
}
