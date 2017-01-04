package com.xz.spring.shell;

import jline.console.ConsoleReader;
import jline.console.completer.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * falcon -- 2017/1/4.
 */
@Configuration
@ComponentScan(basePackageClasses = {ShellExecute.class})
public class ShellExecute {

    public void run(String... jline) throws Exception {
        ConsoleReader reader = new ConsoleReader() ;

        List<Completer> completors = new ArrayList<>();
        completors.add(new FileNameCompleter());
        completors.add(new StringsCompleter("echo","fuck"));
        completors.add(new NullCompleter()) ;
        completors.add(new ArgumentCompleter()) ;
        reader.addCompleter(new ArgumentCompleter(completors)) ;

        reader.setExpandEvents(false);
        reader.setBellEnabled(false);
        //Command History
        String historyPath = System.getProperty("") ;
        reader.setHistoryEnabled(true);
        String line = null;
        do
        {
            line = reader.readLine("input>");
            if(line != null)
            {
                System.out.println(line);
            }
        }
        while(/*line!=null && */!line.equals("exist")) ;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShellExecute.class).showBanner(false).run(args);
    }
}
