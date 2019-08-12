package onlypolish.email;

public class EmailProperties {

    private EmailProperties() throws Exception {
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    public static final String p_host = "mail.smtp.host";
    public static final String p_email = "mail.smtp.user";
    public static final String p_port = "mail.smtp.port";
    public static final String p_starttls_enable = "mail.smtp.starttls.enable";
    public static final String p_auth = "mail.smtp.auth";
    public static final String p_trusted = "mail.smtp.ssl.trust";


    public static final String d_host = "smtp.gmail.com";
    public static final String d_port = "587";
    public static final String d_true = "true";



}
