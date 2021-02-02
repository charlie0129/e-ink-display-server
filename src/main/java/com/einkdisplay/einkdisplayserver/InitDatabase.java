package com.einkdisplay.einkdisplayserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EInkDisplayRepository eInkDisplayRepository) {

        return args -> {
            LOGGER.info("Preloading general display "
                        + eInkDisplayRepository.save(new EInkDisplay(0L,
                                                                     "General Display",
                                                                     0.0,
                                                                     0.0)));
        };
    }
}
