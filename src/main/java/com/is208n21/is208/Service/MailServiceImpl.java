package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.Mail;
import com.is208n21.is208.Repository.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMail(String mail) {
        return userService.findByEmail(mail) != null;
    }
}
