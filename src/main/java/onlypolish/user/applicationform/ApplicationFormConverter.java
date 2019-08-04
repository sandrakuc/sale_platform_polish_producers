package onlypolish.user.applicationform;

import onlypolish.shop.Shop;
import onlypolish.user.Permissions;
import onlypolish.user.User;

import java.util.Random;

public enum ApplicationFormConverter {
    INSTANCE;

    public String generatePassword(){
        Random rd = new Random();
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,!@#$%^&*()";
        String password = "";
        for(int i=0; i <16; i++){
            password += alphabet.charAt(rd.nextInt(alphabet.length()));
        }
        return password;
    }

    public String creteUserLogin(String shopName){
        String[] tokens = shopName.toLowerCase().split("\\s");
        String login = "";
        for(int i=0; i < tokens.length; i++){
            login += tokens[i];
            if(i != tokens.length - 1){
                login += "_";
            }
        }
        return login;
    }

    public User crateUserFromApplicationForm(ApplicationForm applicationForm){
        User user = User.builder()
                .name(applicationForm.getUserName())
                .surname(applicationForm.getUserSurname())
                .address(applicationForm.getUserAddress())
                .postalCode(applicationForm.getUserPostalCode())
                .city(applicationForm.getCity())
                .phoneNumber(applicationForm.getPhoneNumber())
                .email(applicationForm.getEmail())
                .login(creteUserLogin(creteUserLogin(applicationForm.getShopName())))
                .password(generatePassword())
                .permissions(Permissions.SELLER)
                .build();
        return user;
    }

    public Shop createShopFromApplicationForm(ApplicationForm applicationForm, User user){
        Shop shop = Shop.builder()
                .user(user)
                .categories(applicationForm.getCategories())
                .companyName(applicationForm.getCompanyName())
                .dateOfEntireToKrs(applicationForm.getDateOfEntireToKrs())
                .description(applicationForm.getDescription())
                .krs(applicationForm.getKrs())
                .legalForm(applicationForm.getLegalForm())
                .nip(applicationForm.getNip())
                .regon(applicationForm.getRegon())
                .shopAddress(applicationForm.getShopAddress())
                .shopCity(applicationForm.getShopCity())
                .shopName(applicationForm.getShopName())
                .shopPostalCode(applicationForm.getShopPostalCode())
                .website(applicationForm.getWebsite())
                .yearOfStarting(applicationForm.getYearOfStarting())
                .build();
        return shop;
    }
}
