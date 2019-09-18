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
    public static final String REBUKE = "Upomnienie";
    public static final String BAN = "Dostęp do sklepu utracony do odwołania";
    public static final String BAN_CANCELED = "Dostęp do sklepu odzyskany";
    public static final String YOUR_SHOP_IS_BANNED = "Twoje zamówienie zostanie zrealizowane poza systemem.";
    public static final String SHOP_DELETED = "Twoje konto użytkownika zostało usunięte.";
    public static final String USER_ACCOUNT_DELETED = "Konto użytkownika zostało usunięte.";
}
