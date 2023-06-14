package com.example.demo.user;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    private String birthday;

    @Lob
    @Column(name = "profilePicture")
    private byte[] profilePicture; //for testing

    //private Image profileImage;

    //for Authentification

    //for Authorization
    @Column(name = "twoFactorCode")
    private int twoFactorCode;


    public Users(String firstname, String lastname, String email, String password, String birthday){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        //damit das profilbild nie leer ist.
        this.profilePicture = new byte[]{
                (byte) 0x05
        };

    }

    public Users() {

    }

    public void receiveFriendRequest(Users sender){
        sendEmailNotification("Freundschaftsanfrage erhalten", "Sie haben eine Freundschaftsanfrage von " + sender.getFirstname() + sender.getLastname() + " erhalten");
    }
    public void sendEmailNotification(String subject, String message){
        String to = getEmail();
        String from = "gruppe.i1234@gmail.com";
        String smtpHost = "smtp.gmail.com";
        String smtpUsername = "gruppe.i1234@gmail.com";
        String smtpPassword = "rnpihznbwuwougdg";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host",smtpHost);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Freundschaftsanfrage wurde per E-Mail versendet.");
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

}