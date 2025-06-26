package com.example.demo;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.search.FlagTerm;
import org.eclipse.angus.mail.pop3.POP3Folder;
import org.eclipse.angus.mail.pop3.POP3Store;
import org.eclipse.angus.mail.util.BASE64DecoderStream;
import org.eclipse.angus.mail.util.MailSSLSocketFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MailTest {

    @Disabled
    @Test
    public void mailTest() throws Exception {

        POP3Store pop3Store = null;
        try {
            Session session = setCollectProperties();
            pop3Store = (POP3Store) session.getStore("pop3");
            pop3Store.connect("mail.xxx.com", 995, "xxx@xxx.com", "xxx");
            POP3Folder pop3Folder = (POP3Folder) pop3Store.getFolder("INBOX");
            pop3Folder.open(Folder.READ_ONLY); //打开收件箱
            FetchProfile fetchProfile = new FetchProfile();
            fetchProfile.add(FetchProfile.Item.ENVELOPE);
            FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = pop3Folder.search(flagTerm);
            pop3Folder.fetch(messages, fetchProfile);
            int length = messages.length;
            System.out.println("收件箱的邮件数：" + length);
            Folder folder = pop3Folder.getStore().getDefaultFolder();
            Folder[] folders = folder.list();

            for (int i = 0; i < folders.length; i++) {
                System.out.println("名称：" + folders[i].getName());
            }

            for (int i = 0; i < length; i++) {
                MimeMessage msg = (MimeMessage) messages[i];
                String from = MimeUtility.decodeText(messages[i].getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                System.out.println("发件人：" + ia.getPersonal() + '(' + ia.getAddress() + ')');
                System.out.println("主题：" + messages[i].getSubject());
                System.out.println("邮件发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(messages[i].getSentDate()));
                boolean isContainerAttachment = isContainAttachment(msg, messages[i].getSubject());
                System.out.println("是否包含附件：" + isContainerAttachment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pop3Store != null) {
                pop3Store.close();
            }
        }
    }

    /**
     * 设置连接邮箱属性
     *
     * @return
     * @throws GeneralSecurityException
     */
    private static Session setCollectProperties() throws GeneralSecurityException {
        Properties props = new Properties();
        props.setProperty("mail.popStore.protocol", "pop3");       // 使用pop3协议
        props.setProperty("mail.pop3.port", "995");           // 端口

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.pop3.ssl.enable", true);
        props.put("mail.pop3.ssl.socketFactory", sf);
        props.setProperty("mail.pop3.host", "pop.xxx.com");

        return Session.getInstance(props);
    }

    public static boolean isContainAttachment(Part part, String subject) throws Exception {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            System.out.println("multipart/*");
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart, subject);
                } else if (bodyPart.isMimeType("text/calendar")) {
                    System.out.println("text/calendar:text/calendar");
                    String contentType = bodyPart.getContentType();
                    System.out.println("contentType:" + contentType);
                    Object content = bodyPart.getContent();
                    System.out.println("content:" + content);
                    BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) content;
                    byte[] bytes = new byte[0];
                    bytes = new byte[base64DecoderStream.available()];
                    base64DecoderStream.read(bytes);
                    String str = new String(bytes);
                    System.out.println("str:" + str);
                } else if (bodyPart.isMimeType("text/*")) {
                    System.out.println("text/*:text/*");
                    String contentType = bodyPart.getContentType();
                    System.out.println("contentType:" + contentType);
                } else {
                    String contentType = bodyPart.getContentType();
                    System.out.println("contentType:" + contentType);
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            System.out.println("message/rfc822");
        } else if (part.isMimeType("text/calendar")) {
            System.out.println("text/calendar");
        } else if (part.isMimeType("text/*")) {
            System.out.println("text/*");
        }
        return flag;
    }

}
