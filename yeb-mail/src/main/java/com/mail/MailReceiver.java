package com.mail;

import com.server.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

import static com.server.pojo.MailConstants.MAIL_EXCHANGE_NAME;
import static com.server.pojo.MailConstants.MAIL_QUEUE_NAME;

/**
 * 消息接受
 * @author: Bug
 */

@Component
public class MailReceiver {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private MailProperties mailProperties;
    @Resource
    private TemplateEngine templateEngine;

    /**
     * 监听消息
     */
    @RabbitListener(queues = MAIL_QUEUE_NAME)
    public void handler(Employee employee){
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            //主题
            helper.setSubject("入职欢迎邮件");
            //发送日期
            helper.setSentDate(new Date());
            //邮件内容
            Context context= new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJoblevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            //发送邮件
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            logger.error("邮件发送失败=====>{}", e.getMessage());
        }
    }
}

