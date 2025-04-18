package dev.asjordi;

import dev.asjordi.logging.LoggerConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = LoggerConfig.getLogger();

    public static void main(String[] args) {
        LoggerConfig.setupLogger();
        LOGGER.log(Level.INFO, () -> "Starting application with arguments: " + String.join(", ", args));
        CommandLine.processArguments(args);
    }

}
