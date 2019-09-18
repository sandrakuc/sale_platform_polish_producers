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
    private static final String REBUKE = "Witaj!\nDotarły do nas informacje o złamaniu przez Ciebie regulaminu naszego serwisu. To jest pierwsze ostrzeżenie. Następnym razem wyciągniemy poważniejsze konsekwencje.\nProsimy o usunięcie obraźliwej opinii, usunięcie ze sprzedaży niespełniającego zasad produktu, wpłaty zaległej kwoty dla sprzedającego lub zadośćuczynienia kupującemu.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String BAN = "Witaj!\nDotarły do nas informacje o złamaniu przez Ciebie regulaminu naszego serwisu. To nie jest pierwsze Twoje wykroczenie. Na jakiś czas wyłączamy Twój dostęp do Twojego sklepu. Zapraszamy do kupowania od innych sprzedawców. Aby przywrócić uprawnienia sprzedawcy prosimy wpłacić kwotę %s na numer konta %s.\nProsimy również uregulować sprawy dotyczące zakupów dokonanych przez kupujących. W załączniku przesłana zostanie lista transakcji oczekujących na sfinalizowanie poza naszym serwisem. W razie problemów z kupującymi, prosimy skontaktować się z nami na nasz adres mailowy: %s.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String BAN_NO_ORDERS = "Witaj!\nDotarły do nas informacje o złamaniu przez Ciebie regulaminu naszego serwisu. To nie jest pierwsze Twoje wykroczenie. Na jakiś czas wyłączamy Twój dostęp do Twojego sklepu. Zapraszamy do kupowania od innych sprzedawców. Aby przywrócić uprawnienia sprzedawcy prosimy wpłacić kwotę %s na numer konta %s.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String SHOP_DELETED = "Witaj!\nW związku z notorycznym łamaniem regulaminu naszego serwisu Twoje konto zostało usunięte.\nProsimy uregulować sprawy dotyczące zakupów dokonanych przez kupujących. W załączniku przesłana zostanie lista transakcji oczekujących na sfinalizowanie poza naszym serwisem. W razie problemów z kupującymi, prosimy skontaktować się z nami na nasz adres mailowy: %s.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String USER_ACCOUNT_DELETED = "Witaj!\nW związku z notorycznym łamaniem regulaminu naszego serwisu Twoje konto zostało usunięte.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String BAN_CANCELED = "Witaj!\nOdzyskałeś dostęp do swojego sklepu. Możesz na nowo sprzedawać swoje produkty i dbać o zadowolenie klientów.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";
    private static final String YOUR_SHOP_IS_BANNED = "Witaj!\nInformujemy, że sklep, w którym złożyłeś zamówienie został dyscyplinarnie zablokowany lub usunięty. Nie martw się jednak! Zależy nam na zadowoleniu naszych użytkowników! Wysłaliśmy do administartora profilu sklepu zawiadomienie o czekających na sfinalizowanie zamówieniach. Wśród nich jest również Twoje! Zamówienie zostanie sfinalizowane poza naszym systemem. Oczekuj na kontakt ze strony sprzedawcy. W razie problemów skontaktuj się z nami na adres email: %s.\nPozdrawiamy,\nzespół %s\n\nEmail wygenerowany automatycznie, prosimy na niego nie odpowiadać.";

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

    public static String createRebukeEmailContent(){
        return String.format(REBUKE, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createBanEmailContent(){
        return String.format(BAN, EmailVariables.PAYMENT_FOR_ACCOUNT, EmailVariables.OUR_ACCOUNT_EMAIL, EmailVariables.OUR_EMAIL, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createBanNoOrdersEmailContent(){
        return String.format(BAN_NO_ORDERS, EmailVariables.PAYMENT_FOR_ACCOUNT, EmailVariables.OUR_ACCOUNT_EMAIL, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createBanCanceledEmailContent(){
        return String.format(BAN_CANCELED, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createYourShopIsBannedEmailContent(){
        return String.format(YOUR_SHOP_IS_BANNED, EmailVariables.OUR_EMAIL, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createShopDeletedEmailContent(){
        return String.format(SHOP_DELETED, EmailVariables.OUR_EMAIL, EmailVariables.OUR_COMPANY_NAME);
    }

    public static String createUserAccountDeletedEmailContent(){
        return String.format(USER_ACCOUNT_DELETED, EmailVariables.OUR_COMPANY_NAME);
    }
}
