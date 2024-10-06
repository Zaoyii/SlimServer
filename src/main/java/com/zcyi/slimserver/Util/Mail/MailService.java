package com.zcyi.slimserver.Util.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired

    @Autowired
    private MailDao mailDao;//一定要用@Autowired

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给输入的邮箱，发送验证码
     */
    public boolean sendMimeMail(String email, int type) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("邮箱验证");//主题
            //生成随机数
            String code = randomCode();
            mailMessage.setText("【GoSlim】您收到的验证码是:" + code + ",请勿提供给其他人,避免账号丢失。");
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(from);//你自己的邮箱
            mailSender.send(mailMessage);//发送
            MailVerify mailVerify = new MailVerify(email, code, type);
            mailDao.addCode(mailVerify);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     *
     * @return String code
     */
    public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 检验验证码是否一致
     */
    public boolean unVerifyEmail(String userEmail, String userCode, int type) {
        //如果email数据为空，或者不一致，注册失败
        if (!userEmail.isEmpty() && !userCode.isEmpty()) {
            MailVerify mailVerify = mailDao.selectCode(userEmail, type);
            if (mailVerify != null) {
                return !mailVerify.getCode().equals(userCode) || dateToStamp(mailVerify.getUpdateTime()) + 300000 <= System.currentTimeMillis();
            }
        }
        return true;
    }

    public static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return date.getTime();
    }

}