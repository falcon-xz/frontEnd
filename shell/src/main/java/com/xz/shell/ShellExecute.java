package com.xz.shell;

import com.xz.shell.zookeeper.ShellZn;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import jline.console.history.FileHistory;
import jline.console.history.History;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * falcon -- 2017/1/4.
 */
public class ShellExecute {
    private ShellZn shellZn ;
    private String historyFile = null;
    public ShellExecute(){
        this.historyFile = ".xzShellConf";
        shellZn = new ShellZn() ;
        shellZn.register();
    }


    public void run(String[] args) throws Exception {
        ConsoleReader reader = new ConsoleReader();
        //history {use_home}/{historyFile}
        reader.setHistoryEnabled(true);
        File fileHistory = new File(System.getProperty("user.home") + File.separator + historyFile);
        History history = new FileHistory(fileHistory);
        reader.setHistory(history);

        List<String> list = new ArrayList<>() ;
        list.add("hello");
        list.add("fuck");
        list.add("fuck1");
        Completer completer = new StringsCompleter(list) ;
        reader.addCompleter(completer) ;

        reader.setExpandEvents(false);
        reader.setBellEnabled(false);


        String prefix = "";
        String line;
        String tip = "xz" ;
        while ((line = reader.readLine(tip+"> ")) != null) {
            if (!prefix.equals("")) {
                prefix = prefix+'\n';
            }
            System.out.println("step3:"+line.trim().endsWith(";")+!line.trim().endsWith("\\;"));
            if ((line.trim().endsWith(";")) && (!line.trim().endsWith("\\;"))) {
                line = prefix+line;
                System.out.println(line);
                prefix = "" ;
            } else {
                prefix = prefix+line;
            }

        }
    }

    public static void main(String[] args) throws Exception {
        new ShellExecute().run(args);
    }
}
