package com.artozersky.HackerNewsAPI.config.impl;

import com.artozersky.HackerNewsAPI.config.LogFilePathChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LogFilePathCheckerImpl implements LogFilePathChecker {

    private static final Logger logger = LoggerFactory.getLogger(LogFilePathCheckerImpl.class);

    @Value("${logging.file.name}")
    private String logFilePath;

    @jakarta.annotation.PostConstruct
    @Override
    public void checkLogFilePath() {
        Path logPath;
        try {
            logPath = Paths.get(logFilePath);
        } catch (InvalidPathException e) {
            logger.error("Invalid log file path: {}", logFilePath, e);
            return; // Exit if path is invalid
        }
        File logFile = logPath.toFile();

        // Ensure parent directory exists
        if (!logFile.getParentFile().exists()) {
            logFile.getParentFile().mkdirs();
            logger.info("Created missing directories for log file: {}", logFile.getAbsolutePath());
        }

        // Check and create log file
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                logger.info("Created log file: {}", logFile.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Failed to create log file: {}", logFile.getAbsolutePath(), e);
            }
        } else {
            // Check file type and permissions
            if (!logFile.isFile()) {
                logger.warn("The specified log file path is not a valid file: {}", logFile.getAbsolutePath());
            } else if (!logFile.canWrite()) {
                logger.warn("No write permissions for the log file: {}", logFile.getAbsolutePath());
            } else {
                logger.info("Log file path {} is valid and writable.", logFile.getAbsolutePath());
            }
        }
    }
}
