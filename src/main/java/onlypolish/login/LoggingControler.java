package onlypolish.login;

import onlypolish.flashmessage.FlashMessageManager;
import onlypolish.flashmessage.MessagesContents;
import onlypolish.user.Permissions;
import onlypolish.user.User;
import onlypolish.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static onlypolish.flashmessage.FlashMessageManager.FlashMessage.Type.ERROR;
import static onlypolish.flashmessage.FlashMessageManager.FlashMessage.Type.INFO;

@Controller
public class LoggingControler {

    @Autowired
    UserRepo userRepo;

    @Autowired
    FlashMessageManager flashMessageManager;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String FLASH_MESSAGE_MANAGER = "flashMessageManager";

    private boolean checkUserIsValid(User user){
        return user != null;
    }

    private boolean userIsLogged(HttpSession session){
        return session.getAttribute(USER) != null;
    }

    private String getPanel(User user){
            if(user.isAdmin())
                return "redirect:/adminPage";
            if(user.isClient())
                //client panel will be done in the future
                return "index";
            if(user.isSeller())
                //seller panel will be done in the future
                return "index";
            else return "index";
    }

    private boolean checkPasswordIsValid(HttpServletRequest request, User user){
        return request.getParameter(PASSWORD).equals(user.getPassword());
    }

    @RequestMapping("login")
    public String logAsAdmin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        flashMessageManager.setSession(session);
        if(userIsLogged(session)){
            flashMessageManager.addMessage(MessagesContents.ALREADY_LOGGED, ERROR);
            model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
            return "login";
        }else {
            String login = request.getParameter(LOGIN);
            User user = userRepo.getByLogin(login);
            if(checkUserIsValid(user) && checkPasswordIsValid(request, user)){
                flashMessageManager.addMessage(MessagesContents.getLoggedMessage(login), INFO);
                session.setAttribute(USER, user);
                model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
                return getPanel(user);
            }else {
                flashMessageManager.addMessage(MessagesContents.INVALID_LOGIN_OR_PASSWORD, ERROR);
                model.addAttribute(FLASH_MESSAGE_MANAGER, flashMessageManager);
                return "login";
            }
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        return "index";
    }

}
