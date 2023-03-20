package com.is208n21.is208.Repository;


import com.is208n21.is208.Entity.Mail;

public interface MailService {
    public void sendEmail(Mail mail);

    public boolean checkMail(String mail);
}
