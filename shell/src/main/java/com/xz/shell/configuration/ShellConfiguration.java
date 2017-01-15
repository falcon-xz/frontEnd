package com.xz.shell.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.core.JLineShellComponent;

/**
 * falcon -- 2017/1/4.
 */
@Configuration
public class ShellConfiguration {

    @Bean(name = "shell")
    JLineShellComponent shell() {
        return new JLineShellComponent();
    }

    @Bean
    CommandLine commandLine() throws Exception {
        String[] args = new String[0];
        return SimpleShellCommandLineOptions.parseCommandLine(args);
    }
}
