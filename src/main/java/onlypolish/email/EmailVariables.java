package onlypolish.email;

public class EmailVariables {
    private EmailVariables() throws Exception {
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    //sample variables for test only! To change before implementation!

    public static final String OUR_EMAIL = "testertestorowskisender@gmail.com";
    public static final String OUR_ACCOUNT_EMAIL = "39 1140 0000 7283 7809 9355 4126";
    public static final String PAYMENT_FOR_ACCOUNT = "100,00 PLN";
    public static final String OUR_COMPANY_NAME = "Our Company Name";
}
