package edu.esprit.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;



public class SendSMS {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "AC77f3dd603cffa1ea5f3051642badb4d8";
    public static final String AUTH_TOKEN = "9d36cbafbe8f3a7e75f46e9a3acda130";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+21693007457"),
                new PhoneNumber("+21693007457"),
                "Ken khtaf koli , Fadi"
        ).create();
//        Service service = Service.creator("My First Verify Service").create();
//        Verification verification = Verification.creator(
//                        "USe425987e46b97e1f7a93b1b9636ab70f",
//                        "+21694007815",
//                        "sms")
//                .create();

        System.out.println(message.getSid());
    }
}
