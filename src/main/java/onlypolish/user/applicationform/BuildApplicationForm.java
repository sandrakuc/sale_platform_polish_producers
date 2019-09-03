package onlypolish.user.applicationform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildApplicationForm {

    private static Date getDateOfEntireToKrs(ApplicationFormDTO applicationFormDTO) throws ParseException {
        String stringDate = applicationFormDTO.getDateOfEntireToKrs();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        return date;
    }

    public ApplicationForm buildApplicationForm(ApplicationFormDTO applicationFormDTO) throws ParseException {
        ApplicationForm applicationForm = ApplicationForm.builder()
                .userName(applicationFormDTO.getUserName())
                .userSurname(applicationFormDTO.getUserSurname())
                .email(applicationFormDTO.getEmail())
                .phoneNumber(applicationFormDTO.getPhoneNumber())
                .userAddress(applicationFormDTO.getUserAddress())
                .userPostalCode(applicationFormDTO.getUserPostalCode())
                .city(applicationFormDTO.getCity())
                .coowners(applicationFormDTO.getCoowners())
                .companyName(applicationFormDTO.getCompanyName())
                .legalForm(applicationFormDTO.getLegalForm())
                .regon(applicationFormDTO.getRegon())
                .nip(applicationFormDTO.getNip())
                .krs(applicationFormDTO.getKrs())
                .yearOfStarting(applicationFormDTO.getYearOfStarting())
                .dateOfEntireToKrs(getDateOfEntireToKrs(applicationFormDTO))
                .shopName(applicationFormDTO.getShopName())
                .shopAddress(applicationFormDTO.getShopAddress())
                .shopCity(applicationFormDTO.getShopCity())
                .shopPostalCode(applicationFormDTO.getShopPostalCode())
                .website(applicationFormDTO.getWebsite())
                .categories(applicationFormDTO.getCategories())
                .description(applicationFormDTO.getDescription())
                .build();
        return applicationForm;
    }
}
