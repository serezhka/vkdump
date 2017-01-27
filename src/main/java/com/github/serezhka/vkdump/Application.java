package com.github.serezhka.vkdump;

import com.github.serezhka.vkdump.grabber.MessageGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@SpringBootApplication
public class Application {

    private final MessageGrabber messageGrabber;

    @Autowired
    public Application(MessageGrabber messageGrabber) {
        this.messageGrabber = messageGrabber;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner doIt() {
        return doIt -> messageGrabber.grabDialogs();
    }
}
