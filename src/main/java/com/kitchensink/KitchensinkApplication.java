package com.kitchensink;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KitchensinkApplication  {

    private final static Logger logger = LoggerFactory
            .getLogger(KitchensinkApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(KitchensinkApplication.class, args);
        logger.info("Kitchensink Application has been launched");
    }

}
