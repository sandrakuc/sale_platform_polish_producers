package onlypolish.email;

public class EmailSubjects {
    private EmailSubjects() throws Exception {
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    public static final String YOUR_ACCOUNT_IS_CREATED = "Twoje konto zostało utworzone!";
    public static final String START_OF_INVESTIGATION = "Postępowanie w sprawie Twojego zgłoszenia zostało rozpoczęte!";
    public static final String APPLICATION_REJECTED = "Twoje zgłoszenie zostało odrzucone!";
    public static final String PAYMENT_REQUEST = "Twoje zgłoszenie zostało rozpatrzone pozytywnie! Prosimy o wpłatę na podane konto!";
    public static final String APPLICATION_CONSIDERING = "Twoje zgłoszenie jest rozpatrywane!";
    public static final String BUG_IS_REPAIRED = "Zgłoszona przez Ciebie usterka została naprawiona!";
    public static final String START_BUG_REPAIRING = "Praca nad zgłoszoną przez Ciebie usterką rozpoczęta!";
}
