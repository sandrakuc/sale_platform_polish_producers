package onlypolish.securityalert;

import java.util.List;

public class SecurityAlertTemplateHelper {
    private SecurityAlertTemplateHelper() throws Exception{
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    public static final String TEMPLATE_FILE_NAME = "sample_files/security_sample.docx";

    public static final String TYPE = "Typ zgłoszenia: ";
    public static final String DATE = "Data: ";
    public static final String INFORMER_LOGIN = "Użytkownik zgłaszający: ";
    public static final String DESC = "Opis zgłoszenia: ";
    public static final String URL = "Adres URL: ";
    public static final String GUILTY_LOGIN = "Użytkownik: ";
    private static final String NOT_MADE_IN_POLAND = "Produkt nie wyprodukowany w Polsce";
    private static final String OFFENSIVE_OPINION = "Obraźliwa opinia";
    private static final String CHEAT = "Oszustwo";

    public static final String HEADING_3 = "Heading 3";


    public static String getTypeDescription(SecurityAlert securityAlert){
        if(securityAlert.isNotMadeInPolandSecurityAlert()) return NOT_MADE_IN_POLAND;
        else if(securityAlert.isOffensiveOpinionSecurityAlert()) return OFFENSIVE_OPINION;
        else return CHEAT;
    }
}
