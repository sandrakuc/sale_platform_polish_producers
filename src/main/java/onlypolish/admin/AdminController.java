package onlypolish.admin;

import com.itextpdf.text.DocumentException;
import onlypolish.bugraport.BugRaport;
import onlypolish.bugraport.BugRaportRepo;
import onlypolish.bugraport.RaportStatus;
import onlypolish.dashboard.news.News;
import onlypolish.dashboard.news.NewsAndContent;
import onlypolish.dashboard.news.NewsAndContentOperations;
import onlypolish.dashboard.news.NewsRepo;
import onlypolish.email.Email;
import onlypolish.email.EmailContents;
import onlypolish.email.EmailSubjects;
import onlypolish.flashmessage.FlashMessageManager;
import onlypolish.flashmessage.MessagesContents;
import onlypolish.imagestore.ImageStoreService;
import onlypolish.securityalert.*;
import onlypolish.securityalert.generatedfile.GeneratedFile;
import onlypolish.securityalert.generatedfile.GeneratedFileRepo;
import onlypolish.shop.Shop;
import onlypolish.shop.ShopAndProductStatus;
import onlypolish.shop.ShopRepo;
import onlypolish.shop.order.Order;
import onlypolish.shop.order.OrderPdfGenerator;
import onlypolish.shop.order.OrderRepo;
import onlypolish.shop.product.Product;
import onlypolish.shop.product.ProductRepo;
import onlypolish.user.*;
import onlypolish.user.applicationform.*;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static onlypolish.flashmessage.FlashMessageManager.FlashMessage.Type.INFO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    FlashMessageManager flashMessageManager = new FlashMessageManager();

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApplicationFormRepo applicationFormRepo;

    @Autowired
    ShopRepo shopRepo;

    @Autowired
    SecurityAlertRepo securityAlertRepo;

    @Autowired
    BugRaportRepo bugRaportRepo;

    @Autowired
    NewsRepo newsRepo;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    GeneratedFileRepo generatedFileRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    OrderRepo orderRepo;

    private static final String USERS = "users";
    private static final String PERMISSIONS = "perms";
    private static final String SELLER = "SELLER";
    private static final String USER_ID = "userId";
    private static final String SUBSTRING = "substring";
    private static final String APPLICATION_FORMS = "applicationForms";
    private static final String APP_ID = "appId";
    private static final String SECURITY_ALERTS = "securityAlerts";
    private static final String ALERT_ID = "alertId";
    private static final String BUG_RAPORTS = "bugRaports";
    private static final String RAPORT_ID = "raportId";
    private static final String FLASH_MESSAGE_MANAGER = "flashMessageManager";
    private static final String STATUTE_CONTENT = "statuteContent";
    private static final String STATUTE_CONTENT_VALUE = "statuteContentValue";
    private static final String NEWS_AND_CONTENTS = "newsAndContents";
    private static final String NEWS_AND_CONTENT = "newsAndContent";
    private static final String NEWS_ID = "newsId";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_CONTENT = "content";
    private static final String USER = "user";
    private static final String VALID_DATE = "validDate";
    private static final String VALID_YEAR = "validYear";

    private List<Order> getNotCompletedOrders(List<Order> orders){
        List<Order> notCompletedOrders = orders.stream().filter(order -> !order.isCompleted()).collect(Collectors.toList());
        return notCompletedOrders;
    }

    private void sendEmailToClientAboutBanningShop(List<Order> orders) throws MessagingException {
        for(Order order : orders){
            new Email(order.getUserEmail(), EmailSubjects.YOUR_SHOP_IS_BANNED, EmailContents.createYourShopIsBannedEmailContent()).sendEmail();
        }
    }

    private void changeProductsStatus(Shop shop, ShopAndProductStatus status){
        List<Product> products = productRepo.getByShop(shop);
        for(Product product : products){
            product.setStatus(status);
            productRepo.save(product);
        }
    }

    private boolean isUnauthorized(HttpSession session){
        User user = (User) session.getAttribute(USER);
        return user == null || !user.isAdmin();
    }

    private boolean isNewsNull(News news){
        return news == null;
    }

    private boolean checkDateIsValid(Date date){
        Date currDate = new Date();
        return currDate.compareTo(date) > 0;
    }

    private boolean checkYearIsCorrect(String year, Date date){
        int year1 = Integer.parseInt(year);
        int year2 = 1900 + date.getYear();
        return year1 == year2;
    }

    private News buildNews(News news, String title, String imgPath, String newsContent) throws FileNotFoundException {
        Date date = new Date();
        news.setTitle(title);
        news.setDate(date);
        news.setImgPath(imgPath);
        String fileName = NewsAndContentOperations.INSTANCE.generateFileName(date);
        NewsAndContentOperations.INSTANCE.writeNewsContent(newsContent, fileName);
        news.setContentPath(fileName);
        return news;
    }

    private List<BugRaport> getNotRepairedBugRaports(List<BugRaport> bugRaports){
        List<BugRaport> notRepairedBugRaports = bugRaports.stream()
                .filter(bugRaport -> !bugRaport.isRepaired())
                .collect(Collectors.toList());
        return notRepairedBugRaports;
    }

    private List<SecurityAlert> getWaitingSecurityAlert(List<SecurityAlert> securityAlerts){
        List<SecurityAlert> waitingSecurityAlerts = securityAlerts.stream()
                .filter(securityAlert -> securityAlert.isWaiting())
                .collect(Collectors.toList());
        return waitingSecurityAlerts;
    }

    private boolean checkApplicationFormStatus(ApplicationForm applicationForm){
        return !applicationForm.isAccepted() && !applicationForm.isRejected();
    }

    private List<ApplicationForm> getNotAcceptedAndNotRejectedApplicationForms(List<ApplicationForm> applicationForms){
        List<ApplicationForm> newApplicationForms = applicationForms.stream()
                .filter(applicationForm -> checkApplicationFormStatus(applicationForm))
                .collect(Collectors.toList());
        return newApplicationForms;
    }

    private boolean doesContains(String variable, String subs){
        return variable.toLowerCase().contains(subs.toLowerCase());
    }

    private boolean findUserBySubstring(User user, String subs){
        return doesContains(user.getLogin(), subs)
                || doesContains(user.getEmail(), subs)
                || doesContains(user.getName(), subs)
                || doesContains(user.getSurname(), subs);
    }

    private List<User> getUsersWithSubstring(List<User> users, String subs){
        List<User> newUsers = users.stream()
                .filter(user -> findUserBySubstring(user, subs))
                .collect(Collectors.toList());
        return newUsers;
    }

    private Permissions convertPermissionFromStringToEnum(String permissions){
        return SELLER.equals(permissions)?Permissions.SELLER:Permissions.CLIENT;
    }

    private long getLongId(HttpServletRequest request, String parameter){
        String id = request.getParameter(parameter);
        long longId = Long.parseLong(id);
        return longId;
    }

    private List<User> filterListNoAdminUsers(List<User> users){
        List<User> noAdminUsers = users.stream()
                .filter(user -> !user.isAdmin())
                .collect(Collectors.toList());
        return noAdminUsers;
    }

    @RequestMapping("adminPage")
    public String goToAdminPage(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        List<User> users = (List) userRepo.findAll();
        users = filterListNoAdminUsers(users);
        model.addAttribute(USERS, users);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminPage";
    }

    @RequestMapping("adminViewApplicationForms")
    public String goToAdminViewApplicationForms(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        List<ApplicationForm> applicationForms = (List) applicationFormRepo.findAll();
        applicationForms = getNotAcceptedAndNotRejectedApplicationForms(applicationForms);
        model.addAttribute(APPLICATION_FORMS, applicationForms);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewApplicationForms";
    }

    @RequestMapping("adminViewSecurityAlert")
    public String goToAdminViewSecurityAlert(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        List<SecurityAlert> securityAlerts = (List) securityAlertRepo.findAll();
        securityAlerts = getWaitingSecurityAlert(securityAlerts);
        model.addAttribute(SECURITY_ALERTS, securityAlerts);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewSecurityAlert";
    }

    @RequestMapping("adminViewEditStatute")
    public String goToAdminViewEditStatute(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        InputStream is = new FileInputStream("statute.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String statuteContentValue = sb.toString();
        model.addAttribute(STATUTE_CONTENT_VALUE, statuteContentValue);
        return "admin/adminViewEditStatute";
    }

    @RequestMapping("adminViewEditNews")
    public String goToAdminViewEditNews(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        List<News> news = (List) newsRepo.findAll();
        List<NewsAndContent> newsAndContents = NewsAndContentOperations.INSTANCE.mapFromNewsList(news);
        model.addAttribute(NEWS_AND_CONTENTS, newsAndContents);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewEditNews";
    }

    @GetMapping("adminViewEditStatute")
    public String goToAdminViewEditStatute2(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        InputStream is = new FileInputStream("statute.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String statuteContentValue = sb.toString();
        model.addAttribute(STATUTE_CONTENT_VALUE, statuteContentValue);
        return "admin/adminViewEditStatute";
    }

    @RequestMapping("editStatute")
    public String goToEditStatute(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        InputStream is = new FileInputStream("statute.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String statuteContentValue = sb.toString();
        model.addAttribute(STATUTE_CONTENT_VALUE, statuteContentValue);
        return "admin/editStatute";
    }

    @RequestMapping("addNews")
    public String goToAddNews(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        News news = new News();
        NewsAndContent newsAndContent = new NewsAndContent();
        newsAndContent.setNews(news);
        model.addAttribute(NEWS_AND_CONTENT, newsAndContent);
        return "admin/addNews";
    }

    @RequestMapping("editNews")
    public String goToEditNews(HttpServletRequest request, Model model, HttpSession session) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long newsId = getLongId(request, NEWS_ID);
        News news = newsRepo.getById(newsId);
        NewsAndContent newsAndContent = NewsAndContentOperations.INSTANCE.getMappedNewsAndContent(news);
        model.addAttribute(NEWS_AND_CONTENT, newsAndContent);
        return "admin/addNews";
    }

    @RequestMapping("adminViewBugReport")
    public String goToAdminViewBugReport(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        List<BugRaport> bugRaports = (List) bugRaportRepo.findAll();
        bugRaports = getNotRepairedBugRaports(bugRaports);
        model.addAttribute(BUG_RAPORTS, bugRaports);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewBugReport";
    }

    @GetMapping("deleteAccount")
    public String deleteAccount(HttpSession session, HttpServletRequest request, Model model) throws IOException, DocumentException, MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long userId = getLongId(request, USER_ID);
        User user = userRepo.getById(userId);
        if(user.hasShop()){
            Shop shop = user.getShop();
            List<Product> products = productRepo.getByShop(shop);
            List<Order> orders = orderRepo.getByShop(shop);
            List<Order> ordersNotCompleted = getNotCompletedOrders(orders);
            if(!ordersNotCompleted.isEmpty()) {
                String fileName = OrderPdfGenerator.INSTANCE.generateOrderListPdf(ordersNotCompleted);
                Email email = new Email(user.getEmail(), EmailSubjects.SHOP_DELETED, EmailContents.createShopDeletedEmailContent());
                email.setAttachment(fileName);
                email.sendEmail();
                sendEmailToClientAboutBanningShop(ordersNotCompleted);
            }else {
                Email email = new Email(user.getEmail(), EmailSubjects.USER_ACCOUNT_DELETED, EmailContents.createUserAccountDeletedEmailContent());
                email.sendEmail();
            }
            orderRepo.deleteAll(orders);
            productRepo.deleteAll(products);
            shop.setUser(null);
            user.setShop(null);
            shopRepo.delete(shop);
        }else{
            Email email = new Email(user.getEmail(), EmailSubjects.USER_ACCOUNT_DELETED, EmailContents.createUserAccountDeletedEmailContent());
            email.sendEmail();
        }
        BlackListBuilder blackListBuilder = new BlackListBuilder();
        BlackList blackList = blackListBuilder.buildBlackList(user, Punishment.DELETED);
        blackListRepo.save(blackList);
        userRepo.delete(user);
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.USER_DELETED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }

    @GetMapping("search")
    public String search(HttpSession session, HttpServletRequest request, Model model){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        String subs = request.getParameter(SUBSTRING);
        List<User> users = (List) userRepo.findAll();
        users = getUsersWithSubstring(users, subs);
        model.addAttribute(USERS, users);
        return "admin/adminPage";
    }

    @GetMapping("statuteSaveChanges")
    public String statuteSaveChanges(HttpSession session, HttpServletRequest request, Model model) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        String statuteContent = request.getParameter(STATUTE_CONTENT);
        try (PrintWriter out = new PrintWriter("statute.txt")) {
            out.println(statuteContent);
        }
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.CHANGES_SAVED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        model.addAttribute(STATUTE_CONTENT, statuteContent);
        return "redirect:/adminViewEditStatute";
    }

    @GetMapping("saveAccountChanges")
    public String saveAccountChanges(HttpSession session, HttpServletRequest request, Model model) throws MessagingException, IOException, DocumentException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long userId = getLongId(request, USER_ID);
        User user = userRepo.getById(userId);
        if(user.hasShop()) {
            Permissions permissions = convertPermissionFromStringToEnum(request.getParameter(PERMISSIONS));
            user.setPermissions(permissions);
            Shop shop = user.getShop();
            if(permissions.equals(Permissions.CLIENT)){
                shop.setStatus(ShopAndProductStatus.BLOCKED);
                changeProductsStatus(shop, ShopAndProductStatus.BLOCKED);
                shopRepo.save(shop);
                List<Order> orders = orderRepo.getByShop(shop);
                orders = getNotCompletedOrders(orders);
                if(!orders.isEmpty()) {
                    String fileName = OrderPdfGenerator.INSTANCE.generateOrderListPdf(orders);
                    Email email = new Email(user.getEmail(), EmailSubjects.BAN, EmailContents.createBanEmailContent());
                    email.setAttachment(fileName);
                    email.sendEmail();
                    sendEmailToClientAboutBanningShop(orders);
                }else {
                    Email email = new Email(user.getEmail(), EmailSubjects.BAN, EmailContents.createBanNoOrdersEmailContent());
                    email.sendEmail();
                }
                BlackListBuilder blackListBuilder = new BlackListBuilder();
                BlackList blackList = blackListBuilder.buildBlackList(user, Punishment.BAN);
                blackListRepo.save(blackList);
            }else {
                shop.setStatus(ShopAndProductStatus.ACTIVE);
                changeProductsStatus(shop, ShopAndProductStatus.ACTIVE);
                shopRepo.save(shop);
                Email email = new Email(user.getEmail(), EmailSubjects.BAN_CANCELED, EmailContents.createBanCanceledEmailContent());
                email.sendEmail();
            }
            userRepo.save(user);
            flashMessageManager.setSession(session);
            flashMessageManager.addMessage(MessagesContents.CHANGES_SAVED, INFO);
        }
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }

    @GetMapping("acceptApplication")
    public String acceptApplication(HttpSession session, HttpServletRequest request,  Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.ACCEPTED);
        applicationFormRepo.save(applicationForm);
        User user = ApplicationFormConverter.INSTANCE.crateUserFromApplicationForm(applicationForm);
        userRepo.save(user);
        Shop shop = ApplicationFormConverter.INSTANCE.createShopFromApplicationForm(applicationForm, user);
        shopRepo.save(shop);
        Email email = new Email(user.getEmail(), EmailSubjects.YOUR_ACCOUNT_IS_CREATED, EmailContents.createAccountCreatedEmailContent(user.getLogin(), user.getPassword()));
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_ACCEPTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("acceptAlert")
    public String acceptAlert(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws JAXBException, IOException, Docx4JException, MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long alertId = getLongId(request, ALERT_ID);
        SecurityAlert securityAlert = securityAlertRepo.getById(alertId);
        securityAlert.setAlertStatus(AlertStatus.ACCEPTED);
        securityAlertRepo.save(securityAlert);
        String fileName = SecurityAlertRaportGenerator.INSTANCE.generateDoc(securityAlert);
        GeneratedFile generatedFile = GeneratedFile.builder().fileName(fileName).generatedDate(new Date()).build();
        generatedFileRepo.save(generatedFile);
        Email email = new Email(securityAlert.getInformer().getEmail(), EmailSubjects.START_OF_INVESTIGATION, EmailContents.createStartInvestigationEmailContent(securityAlert));
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.ALERT_ACCEPTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewSecurityAlert";
    }

    @GetMapping("rejectApplication")
    public String rejectApplication(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.REJECTED);
        applicationFormRepo.save(applicationForm);
        Email email = new Email(applicationForm.getEmail(), EmailSubjects.APPLICATION_REJECTED, EmailContents.createApplicationRejectedEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_REJECTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("sendPaymentRequest")
    public String sendPaymentRequest(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.WAITING_FOR_MONEY);
        applicationFormRepo.save(applicationForm);
        Email email = new Email(applicationForm.getEmail(), EmailSubjects.PAYMENT_REQUEST, EmailContents.createPaymentRequestEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.PAYMENT_REQUEST_SEND, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("startApplicationConsidering")
    public String startApplicationConsidering(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.CONSIDERED);
        applicationFormRepo.save(applicationForm);
        Email email = new Email(applicationForm.getEmail(), EmailSubjects.APPLICATION_CONSIDERING, EmailContents.createApplicationConsideringEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_CONSIDERED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("reportRepaired")
    public String reportRepaired(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long raportId = getLongId(request, RAPORT_ID);
        BugRaport raport = bugRaportRepo.getById(raportId);
        raport.setStatus(RaportStatus.REPAIRED);
        bugRaportRepo.save(raport);
        Email email = new Email(raport.getInformer().getEmail(), EmailSubjects.BUG_IS_REPAIRED, EmailContents.createBugIsRepairedEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.RAPORT_REPAIRED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewBugReport";
    }

    @GetMapping("addReportToRepair")
    public String addReportToRepair(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long raportId = getLongId(request, RAPORT_ID);
        BugRaport raport = bugRaportRepo.getById(raportId);
        raport.setStatus(RaportStatus.TO_REPAIR);
        bugRaportRepo.save(raport);
        Email email = new Email(raport.getInformer().getEmail(), EmailSubjects.START_BUG_REPAIRING, EmailContents.createStartBugRepairingEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.RAPORT_START_REPAIRING, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewBugReport";
    }

    @GetMapping("deleteNews")
    public String deleteNews(HttpSession session, HttpServletRequest request, Model model){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long newsId = getLongId(request, NEWS_ID);
        News news = newsRepo.getById(newsId);
        newsRepo.delete(news);
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.NEWS_DELETED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewEditNews";
    }

    @PostMapping(value = "newsSaveChanges", consumes = {"multipart/form-data"})
    public String newsSaveChanges(HttpSession session, HttpServletRequest request, Model model, @RequestParam MultipartFile file) throws IOException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        String newsTitle = request.getParameter(NEWS_TITLE);
        News news = newsRepo.getByTitle(newsTitle);
        String newsContent = request.getParameter(NEWS_CONTENT);
        String imgPath;
        if(isNewsNull(news)){
            news = new News();
            ImageStoreService imageStoreService = new ImageStoreService(resourceLoader);
            imageStoreService.createImage(file);
            imgPath = file.getOriginalFilename();
        }else {
            imgPath = news.getImgPath();
        }
        news = buildNews(news, newsTitle, imgPath, newsContent);
        newsRepo.save(news);
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.CHANGES_SAVED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewEditNews";
    }

    @GetMapping("createNewUserWithShop")
    public String createNewUserWithShop(ApplicationFormDTO applicationFormDTO, BindingResult bindingResult, HttpSession session, HttpServletRequest request, Model model){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        boolean validYear = true;
        boolean validDate = true;
        User user = (User) session.getAttribute(USER);
        model.addAttribute(USER, user);
        model.addAttribute(VALID_YEAR, validYear);
        model.addAttribute(VALID_DATE, validDate);
        return "registering/newShopUserApplication";
    }

    @PostMapping("newUserWithShop")
    public String newUserWithShop(@Valid ApplicationFormDTO applicationFormDTO, BindingResult bindingResult, HttpSession session, Model model) throws ParseException, MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        BuildApplicationForm buildApplicationForm = new BuildApplicationForm();
        ApplicationForm applicationForm = buildApplicationForm.buildApplicationForm(applicationFormDTO);
        boolean validDate = checkDateIsValid(applicationForm.getDateOfEntireToKrs());
        boolean validYear = checkYearIsCorrect(applicationForm.getYearOfStarting(), applicationForm.getDateOfEntireToKrs());
        if(bindingResult.hasErrors() || !validDate || !validYear){
            User user = (User) session.getAttribute(USER);
            model.addAttribute(USER, user);
            model.addAttribute(VALID_DATE, validDate);
            model.addAttribute(VALID_YEAR, validYear);
            return "registering/newShopUserApplication";
        }
        User user = ApplicationFormConverter.INSTANCE.crateUserFromApplicationForm(applicationForm);
        userRepo.save(user);
        Shop shop = ApplicationFormConverter.INSTANCE.createShopFromApplicationForm(applicationForm, user);
        shopRepo.save(shop);
        Email email = new Email(user.getEmail(), EmailSubjects.YOUR_ACCOUNT_IS_CREATED, EmailContents.createAccountCreatedEmailContent(user.getLogin(), user.getPassword()));
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_ACCEPTED, INFO);
        model.addAttribute(VALID_YEAR, validYear);
        model.addAttribute(VALID_DATE, validDate);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }

    @GetMapping("sendRebuke")
    public String sendRebuke(HttpSession session, HttpServletRequest request, Model model) throws MessagingException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        long userId = getLongId(request, USER_ID);
        User user = userRepo.getById(userId);
        BlackListBuilder blackListBuilder = new BlackListBuilder();
        BlackList blackList = blackListBuilder.buildBlackList(user, Punishment.REBUKE);
        blackListRepo.save(blackList);
        Email email = new Email(blackList.getUserEmail(), EmailSubjects.REBUKE, EmailContents.createRebukeEmailContent());
        email.sendEmail();
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.REBUKE_SENT, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }
}
