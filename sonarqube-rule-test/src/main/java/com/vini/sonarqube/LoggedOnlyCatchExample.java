package com.vini.sonarqube;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedOnlyCatchExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedOnlyCatchExample.class);

    public void process() {
        try {
            throw new IllegalStateException("Unexpected processing error");
        } catch (IllegalStateException e) {
            LOGGER.error("Processing failed", e);
        }
    }
}
