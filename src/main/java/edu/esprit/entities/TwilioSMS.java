/*package edu.esprit.entities;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {
    // Votre SID Twilio
    public static final String ACCOUNT_SID = "ACc889cd2b52f6a09ca714c967c8c33cd1";
    // Votre token d'authentification Twilio
    public static final String AUTH_TOKEN = "03899d558cdf5574b4566b0f644c7781";
    // Votre numéro Twilio (obtenu sur le tableau de bord Twilio)
    public static final String TWILIO_NUMBER = "+19284400733";

    public static void main(String[] args) {
        // Initialiser la configuration Twilio avec votre SID et token d'authentification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Remplacer "votre_numero_destinataire" par le numéro de téléphone du destinataire
        String destinataire = "+21655907840";
        // Remplacer "votre_message" par le message que vous voulez envoyer
        String message = "Repture de stock";

        // Envoyer le message
        Message twilioMessage = Message.creator(
                new PhoneNumber(destinataire),
                new PhoneNumber(TWILIO_NUMBER),
                message
        ).create();

        // Afficher le SID du message Twilio envoyé
        System.out.println("SID du message Twilio: " + twilioMessage.getSid());
    }
}*/
