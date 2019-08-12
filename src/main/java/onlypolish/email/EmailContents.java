package onlypolish.email;

import onlypolish.securityalert.SecurityAlert;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmailContents {
    private EmailContents() throws Exception {
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    private static final String YOUR_ACCOUNT_IS_CREATED = "Witaj!\n\nTwoja wpłata została zaksięgowana a profil Twojego sklepu został utworzony!\nTwój login: %s\nTwoje hasło: %s\nPamiętaj, aby po pierwszym zalogowaniu zmienić swoje hasło! Dla zwiększenia bezpieczeństwa polecamy zmieniać swoje hasło raz na miesiąc i nie używać tego hasła na innych serwisach i platformach.\nDziękujemy za chęć współpracy oraz zaufanie!\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String START_OF_INVESTIGATION = "Witaj!\n\nDziękujemy za pomoc w czynieniu naszego serwisu lepszym i bezpieczniejszym miejscem! Postępowanie w sprawie Twojego zgłoszenia dotyczącego %s zostało rozpoczęte! Wobec osoby zgłoszonej zostaną wyciągnięte odpowiednie konsekwencje zgodnie z naszym regulaminem!\nPozdrawiamy,\nzespół %s\n\nEmail Wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String APPLICATION_REJECTED = "Witaj!\n\nNiestety, ponieważ polityka Twojej firmy nie jest zgodna z polityką naszego serwisu lub nie otrzymaliśmy wpłaty w terminie, nie możemy utworzyć konta Twojego sklepu!\nDziękujemy za chęć współpracy!\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String PAYMENT_REQUEST = "Witaj!\nTwoje podanie o założenie konta sklepu zostało rozpatrzone pozytywnie! Polityka Twojej firmy jest zgodna z polityką naszego serwisu! Zapowiada się owocna współpraca :)\nAby ją rozpocząć, należy wpłacić kwotę w wysokości %s na nasze konto: %s. Po zaksięgowaniu wpłaty konto zostanie utworzone. Termin wpłaty: %s.\nJeżeli do tego czasu wpłata nie wpłynie na nasze konto, zgłoszenie zostanie odrzucone.\nDziękujemy za chęć współpracy oraz zaufanie.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String APPLICATION_CONSIDERING = "Witaj!\nTwoje podanie o założenie konta sklepu jest właśnie rozpatrywane! W najbliższym czasie jeden z członków naszego zespołu skontaktuje się z Tobą w sprawie spotkania, aby lepiej poznać Twoją firmę!\nDziękujemy za chęć współpracy oraz zaufanie!\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String BUG_IS_REPAIRED = "Witaj!\nDziękujemy za pomoc w usprawnianiu naszego serwisu! Zgłoszona przez Ciebie usterka została właśnie naprawiona! W razie problemów prosimy o dalszy kontakt!\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String START_BUG_REPAIRING = "Witaj!\nDziękujemy za pomoc w usprawnianiu naszego serwisu! Zgłoszona przez Ciebie usterka jest właśnie naprawiana! W razie problemów prosimy o dalszy kontakt!\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";

    private static String alertTypeToStringConvert(SecurityAlert securityAlert){
        if(securityAlert.isOffensiveOpinionSecurityAlert()) return "obraźliwej opinii";
        else if(securityAlert.isNotMadeInPolandSecurityAlert()) return "produktu nie wyprodukowanego w Polsce";
        else return "oszustwa";
    }

    private static String getPaymentDeadline(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 14);
        String paymentDeadline = sdf.format(cal.getTime());
        return paymentDeadline;
    }

    public static String createAccountCreatedEmailContent(String login, String password){
        return String.format(YOUR_ACCOUNT_IS_CREATED, login, password, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createStartInvestigationEmailContent(SecurityAlert securityAlert){
        return String.format(START_OF_INVESTIGATION, alertTypeToStringConvert(securityAlert), EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createApplicationRejectedEmailContent(){
        return String.format(APPLICATION_REJECTED, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createPaymentRequestEmailContent(){
        return String.format(PAYMENT_REQUEST, EmailVariables.PAYMENT_FOR_ACCOUNT, EmailVariables.OUR_ACCOUNT_EMAIL, getPaymentDeadline(), EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createApplicationConsideringEmailContent(){
        return String.format(APPLICATION_CONSIDERING, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createBugIsRepairedEmailContent(){
        return String.format(BUG_IS_REPAIRED, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createStartBugRepairingEmailContent(){
        return String.format(START_BUG_REPAIRING, EmailVariables.OUR_COMPANY_NAME);
    }
}
