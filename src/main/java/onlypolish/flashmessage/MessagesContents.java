package onlypolish.flashmessage;

public class MessagesContents {
    private MessagesContents() throws Exception{
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    public static final String ALREADY_LOGGED = "Jesteś już zalogowany!";
    public static final String LOGGED = "Zalogowano jako: ";
    public static final String INVALID_LOGIN_OR_PASSWORD = "Nieprawidłowy login ub hasło";
    public static final String USER_DELETED = "Użytkownik został usunięty z bazy";
    public static final String CHANGES_SAVED = "Zmiany zostały zapisane!";
    public static final String APPLICATION_ACCEPTED = "Wniosek został zaakceptowany!\n Użytkownik oraz profil sklepu został utworzony.";
    public static final String ALERT_ACCEPTED = "Wiadomość o rozpoczęciu postępowania wysłana do użytkownika";
    public static final String APPLICATION_REJECTED = "Wniosek został odrzucony!";
    public static final String PAYMENT_REQUEST_SEND = "Wniosek zmienił status na 'Do akceptacji - oczekujący na wpłatę'.\n Polecenie zapłaty zostało wysłane na podany w formularzu adres e-mail.";
    public static final String APPLICATION_CONSIDERED = "Wniosek zmienił status na 'Rozpatrywany'.\n Informacja o rozpoczęciu rozpatrywania wniosku została wysłana na podany w formularzu adres e-mail.";
    public static final String RAPORT_REPAIRED = "Informacja o naprawieniu błędu została wysłana do użytkownika.";
    public static final String RAPORT_START_REPAIRING = "Informacja o rozpoczęciu pracy nad naprawieniem błędu została wysłana do użytkownika.";
    public static final String NEWS_DELETED = "Post został usunięty!";

    public static String getLoggedMessage(String login){
        return LOGGED + login;
    }
}
