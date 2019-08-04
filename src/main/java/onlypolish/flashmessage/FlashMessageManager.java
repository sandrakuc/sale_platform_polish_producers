package onlypolish.flashmessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

@Service
public class FlashMessageManager {

    public static class FlashMessage {
        public enum Type {
            INFO, ERROR;

            public String toBootstrapClass() {
                switch (this) {
                    case ERROR: return "alert-danger";
                    case INFO: return "alert-info";
                }
                return null;
            }
        }

        public String message;
        public Type type;


    }

    @Autowired
    HttpSession session;

    public void setSession(HttpSession session){
        this.session = session;
    }

    public void addMessage(String msg, FlashMessage.Type type) {
        FlashMessage flashMessage = new FlashMessage();
        flashMessage.message = msg;
        flashMessage.type = type;
        session.setAttribute("flashMessage", flashMessage);
    }

    public boolean issetMessage() {
        return (session.getAttribute("flashMessage") != null);
    }

    public FlashMessage getFlashMessage() {
        FlashMessage flashMessage = (FlashMessage)session.getAttribute("flashMessage");
        session.setAttribute("flashMessage", null);
        return flashMessage;
    }
}
