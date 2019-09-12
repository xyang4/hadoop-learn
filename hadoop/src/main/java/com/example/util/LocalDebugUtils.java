package com.example.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import java.util.Date;

@UtilityClass
public class LocalDebugUtils {
    /**
     * @param args
     * @param relativePath
     * @return
     */
    public String[] initInput(final String[] args, String relativePath, String outputPath) {
        if (args.length == 0) {
            initLogger();

            return new String[]{"data/input/" + relativePath, "data/output/" + (null == outputPath ? relativePath + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") : outputPath)};
        }
        return args;
    }

    public String[] initInput(final String[] args, String relativePath) {
        return initInput(args, relativePath, null);
    }

    /**
     * @link {https://www.cnblogs.com/hejianjun/p/9047154.html}
     */
    public void initLogger() {
        Appender appender = new ConsoleAppender(new PatternLayout("%d [%t] %-5p [%l] - %m%n"));
        BasicConfigurator.configure(appender);
    }

}
