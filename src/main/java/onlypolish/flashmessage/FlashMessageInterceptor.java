package onlypolish.flashmessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FlashMessageInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    FlashMessageManager flashMessageManager;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        if(modelAndView != null && modelAndView.getModelMap() != null && modelAndView.getModelMap() != null ) {
            modelAndView.getModelMap().addAttribute("flashMessageManager", flashMessageManager);
        }

    }
}