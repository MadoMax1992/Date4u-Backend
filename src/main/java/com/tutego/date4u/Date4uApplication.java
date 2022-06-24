package com.tutego.date4u;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
//@ImportResource({"classpath*:applicationContext.xml"})
@EnableJpaAuditing
public class Date4uApplication {

    public static void main(String[] args) {
        SpringApplication.run(Date4uApplication.class, args);




//        final Logger log = LoggerFactory.getLogger(Date4uApplication.class);

//        ApplicationContext applicationContext = SpringApplication.run(Date4uApplication.class, args);
//
//        MessageProcessor userService = applicationContext.getBean(MessageProcessor.class);
//        userService.processMsg("twitter message sending ");



//        Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//        ApplicationContext ctx = SpringApplication.run( Date4uApplication.class,
//                args );
//        Arrays.stream( ctx.getBeanDefinitionNames() )
//                .sorted()
//                .forEach( log::info );

    }

}
