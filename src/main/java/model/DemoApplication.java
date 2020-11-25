package model;

import application.ApplicationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplication implements ApplicationType {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Override
    public void printApplicationType() {
        logger.info("Starting demo application");
    }
}
