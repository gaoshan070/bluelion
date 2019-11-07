package com.bluelion.shared.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import java.util.Random;

/**
 * 邮件工具类
 */
public class EmailUtil {

    private static final String[] avalivleMails;
    private static final JavaMailSenderImpl mailSender;
    private static final Random random;

    private EmailUtil() {
    }

    static {
        avalivleMails = new String[]{"gaoshan070@gmail.com"};
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        Properties mailProperty = new Properties();
        mailProperty.put("mail.smtp.timeout", 25000);
        mailProperty.put("mail.smtp.starttls.enable", "true"); // 在本行添加
        mailProperty.put("mail.smtp.port", "587");
        mailProperty.put("mail.smtp.auth", "true");
        mailSender.setJavaMailProperties(mailProperty);
        mailSender.setPassword("liqun9802!");
//        avalivleMails = new String[]{"gaoshan1010@aliyun.com"};
//        mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.aliyun.com");
//        Properties mailProperty = new Properties();
//        mailProperty.put("mail.smtp.auth", true);
//        mailProperty.put("mail.smtp.timeout", 25000);
//        mailSender.setJavaMailProperties(mailProperty);
//        mailSender.setPassword("liqun9802!");
        random = new Random();
    }

    public static boolean sendEmailWithAttachment(String toEmail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        String filePath = "/Users/gaoshan/Desktop/bunny.jpeg";
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("gaoshan1010@aliyun.com");
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            System.out.println("邮件发送成功");
            return true;
        } catch (MessagingException e) {
            System.out.println("邮件发送失败");
            return false;
        }

    }

    public static boolean sendEmail(String toEmail, String title, String content) {
        String mailUser = avalivleMails[random.nextInt(avalivleMails.length)];
        mailSender.setUsername(mailUser);
        long beginTime = System.currentTimeMillis();
        SimpleMailMessage mail = new SimpleMailMessage();
        try {
            mail.setTo(toEmail);
            mail.setFrom(mailUser);
            mail.setSubject(title);
            mail.setText(content);
            mailSender.send(mail);
//            RecordLogger.mailLog(mailUser, toEmail, title, content);
        } catch (MailException e) {
//            LogContext.instance().error(e, "邮件发送失败");
            System.out.println("邮件发送失败");
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
//            RecordLogger.timeLog("java-send-mail", endTime - beginTime);
        }
        return true;
    }

    public static void main(String[] args){
        EmailUtil.sendEmailWithAttachment("gaoshan070@gmail.com","test", "attached files");
//        EmailUtil.sendEmail("gaoshan070@gmail.com","test", "attached files");
    }
}
