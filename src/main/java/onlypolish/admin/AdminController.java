package onlypolish.admin;

import onlypolish.bugraport.BugRaport;
import onlypolish.bugraport.BugRaportRepo;
import onlypolish.bugraport.RaportStatus;
import onlypolish.flashmessage.FlashMessageManager;
import onlypolish.flashmessage.MessagesContents;
import onlypolish.securityalert.AlertStatus;
import onlypolish.securityalert.SecurityAlert;
import onlypolish.securityalert.SecurityAlertRepo;
import onlypolish.shop.Shop;
import onlypolish.shop.ShopRepo;
import onlypolish.user.Permissions;
import onlypolish.user.User;
import onlypolish.user.UserRepo;
import onlypolish.user.applicationform.ApplicationForm;
import onlypolish.user.applicationform.ApplicationFormConverter;
import onlypolish.user.applicationform.ApplicationFormRepo;
import onlypolish.user.applicationform.FormStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static onlypolish.flashmessage.FlashMessageManager.FlashMessage.Type.INFO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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

    List<BugRaport> getNotRepairedBugRaports(List<BugRaport> bugRaports){
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
    public String goToAdminPage(HttpServletRequest request, Model model){
        List<User> users = (List) userRepo.findAll();
        users = filterListNoAdminUsers(users);
        model.addAttribute(USERS, users);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminPage";
    }

    @RequestMapping("adminViewApplicationForms")
    public String goToAdminViewApplicationForms(HttpServletRequest request, Model model){
        List<ApplicationForm> applicationForms = (List) applicationFormRepo.findAll();
        applicationForms = getNotAcceptedAndNotRejectedApplicationForms(applicationForms);
        model.addAttribute(APPLICATION_FORMS, applicationForms);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewApplicationForms";
    }

    @RequestMapping("adminViewSecurityAlert")
    public String goToAdminViewSecurityAlert(HttpServletRequest request, Model model){
        List<SecurityAlert> securityAlerts = (List) securityAlertRepo.findAll();
        securityAlerts = getWaitingSecurityAlert(securityAlerts);
        model.addAttribute(SECURITY_ALERTS, securityAlerts);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewSecurityAlert";
    }

    @RequestMapping("adminViewEditStatute")
    public String goToAdminViewEditStatute(HttpServletRequest request, Model model) throws IOException {
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
    public String goToAdminViewEditNews(HttpServletRequest request, Model model){
        //todo: build in future
        return "admin/adminViewEditNews";
    }

    @GetMapping("adminViewEditStatute")
    public String goToAdminViewEditStatute2(HttpServletRequest request, Model model) throws IOException {
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
    public String goToEditStatute(HttpServletRequest request, Model model) throws IOException {
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
    public String goToAddNews(HttpServletRequest request, Model model){
        //todo: build in future
        return "admin/addNews";
    }

    @RequestMapping("adminViewBugReport")
    public String goToAdminViewBugReport(HttpServletRequest request, Model model){
        List<BugRaport> bugRaports = (List) bugRaportRepo.findAll();
        bugRaports = getNotRepairedBugRaports(bugRaports);
        model.addAttribute(BUG_RAPORTS, bugRaports);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewBugReport";
    }

    @GetMapping("deleteAccount")
    public String deleteAccount(HttpSession session, HttpServletRequest request, Model model){
        long userId = getLongId(request, USER_ID);
        User user = userRepo.getById(userId);
        userRepo.delete(user);
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.USER_DELETED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }

    @GetMapping("search")
    public String search(HttpSession session, HttpServletRequest request, Model model){
        String subs = request.getParameter(SUBSTRING);
        List<User> users = (List) userRepo.findAll();
        users = getUsersWithSubstring(users, subs);
        model.addAttribute(USERS, users);
        return "admin/adminPage";
    }

    @GetMapping("statuteSaveChanges")
    public String statuteSaveChanges(HttpSession session, HttpServletRequest request, Model model) throws IOException {
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
    public String saveAccountChanges(HttpSession session, HttpServletRequest request, Model model){
        long userId = getLongId(request, USER_ID);
        User user = userRepo.getById(userId);
        Permissions permissions = convertPermissionFromStringToEnum(request.getParameter(PERMISSIONS));
        user.setPermissions(permissions);
        userRepo.save(user);
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.CHANGES_SAVED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminPage";
    }

    @GetMapping("acceptApplication")
    public String acceptApplication(HttpSession session, HttpServletRequest request,  Model model){
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.ACCEPTED);
        applicationFormRepo.save(applicationForm);
        User user = ApplicationFormConverter.INSTANCE.crateUserFromApplicationForm(applicationForm);
        userRepo.save(user);
        Shop shop = ApplicationFormConverter.INSTANCE.createShopFromApplicationForm(applicationForm, user);
        shopRepo.save(shop);
        //todo in future - send email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_ACCEPTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("acceptAlert")
    public String acceptAlert(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model){
        long alertId = getLongId(request, ALERT_ID);
        SecurityAlert securityAlert = securityAlertRepo.getById(alertId);
        securityAlert.setAlertStatus(AlertStatus.ACCEPTED);
        securityAlertRepo.save(securityAlert);
        //todo in future - sending email and generate raport to .doc or .pdf (to decide)
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.ALERT_ACCEPTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewSecurityAlert";
    }

    @GetMapping("rejectApplication")
    public String rejectApplication(HttpSession session, HttpServletRequest request, Model model){
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.REJECTED);
        applicationFormRepo.save(applicationForm);
        //todo in future - send email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_REJECTED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("sendPaymentRequest")
    public String sendPaymentRequest(HttpSession session, HttpServletRequest request, Model model){
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.WAITING_FOR_MONEY);
        applicationFormRepo.save(applicationForm);
        //todo in future - add sending email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.PAYMENT_REQUEST_SEND, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("startApplicationConsidering")
    public String startApplicationConsidering(HttpSession session, HttpServletRequest request, Model model){
        long appId = getLongId(request, APP_ID);
        ApplicationForm applicationForm = applicationFormRepo.getById(appId);
        applicationForm.setFormStatus(FormStatus.CONSIDERED);
        applicationFormRepo.save(applicationForm);
        //todo in future - send email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.APPLICATION_CONSIDERED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewApplicationForms";
    }

    @GetMapping("reportRepaired")
    public String reportRepaired(HttpSession session, HttpServletRequest request, Model model){
        long raportId = getLongId(request, RAPORT_ID);
        BugRaport raport = bugRaportRepo.getById(raportId);
        raport.setStatus(RaportStatus.REPAIRED);
        bugRaportRepo.save(raport);
        //todo in future - send email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.RAPORT_REPAIRED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewBugReport";
    }

    @GetMapping("addReportToRepair")
    public String addReportToRepair(HttpSession session, HttpServletRequest request, Model model){
        long raportId = getLongId(request, RAPORT_ID);
        BugRaport raport = bugRaportRepo.getById(raportId);
        raport.setStatus(RaportStatus.TO_REPAIR);
        bugRaportRepo.save(raport);
        //todo in future - send email
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.RAPORT_START_REPAIRING, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "redirect:/adminViewBugReport";
    }

    @GetMapping("deleteNews")
    public String deleteNews(HttpSession session, HttpServletRequest request, Model model){
        //todo: build in future
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.NEWS_DELETED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewEditNews";
    }

    @GetMapping("newsSaveChanges")
    public String newsSaveChanges(HttpSession session, HttpServletRequest request, Model model){
        //todo: build in future
        flashMessageManager.setSession(session);
        flashMessageManager.addMessage(MessagesContents.CHANGES_SAVED, INFO);
        model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
        return "admin/adminViewEditNews";
    }
}
