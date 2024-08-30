package com.hdteam.appquality.taq.tracking.email;

import javax.mail.PasswordAuthentication;

class SMTPAuthenticator extends javax.mail.Authenticator {
    private PasswordAuthentication authentication;

    public SMTPAuthenticator(String username, String password) {
        authentication = new PasswordAuthentication(username, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}