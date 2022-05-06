package com.example.myapplication;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mailing_service {
    final String username="tayyab222gb@gmail.com";
    final String password="proudtobemuslim";
    public String book_name,reg;
    Session session;
    Context context;
    Properties props;

    public mailing_service(Context context,String book_name,String reg) {
        this.context = context;
        this.book_name=book_name;
        this.reg=reg;
        props=new Properties();
    }

    public  void setProps()
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.host","smtp.gmail.com");
        }
        public void set_authentication(){

             session=Session.getInstance(props,new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username,password);
                }
            });
        }
        public  void sent_email(String messageString){
            try {
                Message message=new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
//                Toast.makeText(context,reg,Toast.LENGTH_LONG).show();
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(get_email_addres(reg)));
                message.setSubject("Sending Email to Student");
                message.setText(messageString);
                Transport.send(message);
//                Toast.makeText(context,"Email send Successfully",Toast.LENGTH_LONG).show();
            }catch (MessagingException e){
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                //  Toast.makeText(getApplicationContext(),_txtemail.getText().toString(),Toast.LENGTH_LONG).show();
            }
        }
    public  void sent_email(String parameter, String regs){
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
//                Toast.makeText(context,reg,Toast.LENGTH_LONG).show();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(regs));
            message.setSubject("REMINDER");
            message.setText(get_warning()
            );

            Transport.send(message);
//                Toast.makeText(context,"Email send Successfully",Toast.LENGTH_LONG).show();
        }catch (MessagingException e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            //  Toast.makeText(getApplicationContext(),_txtemail.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
public void setpermssions()
{
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
}
public String get_warning()
{
    return  "DEAR STUDENT YOUR DATE OF RETURNING BOOK IS PASSED \n YOU ARE DIRECTED TO RETURN YOUR BOOK AS SOON AS POSSIBLE" +
            "\n \n INCHARGE LIBRARY UET NWL"
            ;
}
    public String get_messaage_text(String operation) {
        if (operation=="assigned to ")
            return book_name+operation+reg+"\n\n\n\tNote:\tPlease take care of this book  and make sure to return in within time of (21 days) otherwise you will be charged Rs:10 per day in case of book lost you can face difficulty in your degree clearification" +
                "\n\nRegards: Incharge library UET LHR NWL Campus";
        else
            return book_name+"\n"+operation+"\n"+reg;
    }
    public String get_email_addres(String reg)
    {
        String x=reg.substring(0,4)+reg.substring(5,7).toLowerCase()+reg.substring(8,11)+"@student.uet.edu.pk";
//        Toast.makeText(context,x,Toast.LENGTH_LONG).show();
        return  x;
    }

}
