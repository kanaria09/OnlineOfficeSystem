package com.mail;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import static com.server.pojo.MailConstants.MAIL_EXCHANGE_NAME;
import static com.server.pojo.MailConstants.MAIL_QUEUE_NAME;


/**
 * @author 神様
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class,args);
    }

    @Bean
    public Queue queue(){
        return new Queue(MAIL_QUEUE_NAME);
    }
}
