package onlypolish.admin;

import onlypolish.user.BlackList;
import onlypolish.user.BlackListRepo;
import onlypolish.user.Punishment;
import onlypolish.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlackListController {

    @Autowired
    BlackListRepo blackListRepo;

    private static final String USER = "user";
    private static final String BLACK_LIST = "blackList";
    private static final String REBUKE_VARIABLE = "REBUKE";
    private static final String BAN_VARIABLE = "BAN";
    private static final String DELETED_VARIABLE = "DELETED";
    private static final String SEARCH = "search";
    private static final String PUNISHMENT = "punishment";
    private static final String DATE_TO = "dateTo";
    private static final String DATE_FROM = "dateFrom";
    private static final String VALID_DATE_TO = "validDateTo";
    private static final String VALID_DATE_FROM = "validDateFrom";

    private boolean isUnauthorized(HttpSession session){
        User user = (User) session.getAttribute(USER);
        return user == null || !user.isAdmin();
    }

    private List<BlackList> getBlackListFilteredByString(List<BlackList> blackList, String string){
        List<BlackList> filteredBlackList = blackList.stream()
                .filter(bl -> bl.getUserEmail().contains(string) || bl.getUserLogin().contains(string) || bl.getUserName().contains(string) || bl.getUserSurname().contains(string))
                .collect(Collectors.toList());
        return filteredBlackList;
    }

    private List<BlackList> getBlackListFilteredByPunishment(List<BlackList> blackList, Punishment punishment){
        List<BlackList> filteredBlackList = blackList.stream()
                .filter(bl -> punishment.equals(bl.getPunishment()))
                .collect(Collectors.toList());
        return filteredBlackList;
    }

    private List<BlackList> getBlackListFilteredByDateFrom(List<BlackList> blackList, Date dateFrom){
        List<BlackList> filteredBlackList = blackList.stream()
                .filter(bl -> dateFrom.compareTo(bl.getDate()) <= 0)
                .collect(Collectors.toList());
        return filteredBlackList;
    }

    private List<BlackList> getBlackListFilteredByDateTo(List<BlackList> blackList, Date dateTo){
        List<BlackList> filteredBlackList = blackList.stream()
                .filter(bl -> dateTo.compareTo(bl.getDate()) >= 0)
                .collect(Collectors.toList());
        return filteredBlackList;
    }

    private boolean dateIsNotFuture(Date date){
        Date now = new Date();
        return now.compareTo(date) >= 0;
    }

    private boolean dateToIsValid(Date dateFrom, Date dateTo){
        return dateTo.compareTo(dateFrom) >= 0 && dateIsNotFuture(dateTo);
    }

    private Date getDateFromString(String string) throws ParseException {
        if(!string.isEmpty()) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(string);
            return date;
        }
        else return null;
    }

    private boolean checkIfNotNull(Punishment punishment){
        return punishment != null;
    }

    private boolean checkIfNotNull(Date date){
        return date != null;
    }

    private Punishment convertToPunishment(String string){
        if(REBUKE_VARIABLE.equals(string))
            return Punishment.REBUKE;
        else if(BAN_VARIABLE.equals(string))
            return Punishment.BAN;
        else if(DELETED_VARIABLE.equals(string)){
            return Punishment.DELETED;
        }
        else {
            return null;
        }
    }

    @RequestMapping("blackList")
    public String showBlackList(HttpServletRequest request, Model model, HttpSession session){
        if(isUnauthorized(session)){
            return "forbidden";
        }
        boolean validDateFrom = true;
        boolean validDateTo = true;
        List<BlackList> blackList = (List<BlackList>) blackListRepo.findAll();
        model.addAttribute(BLACK_LIST, blackList);
        model.addAttribute(VALID_DATE_TO, validDateTo);
        model.addAttribute(VALID_DATE_FROM, validDateFrom);
        return "admin/blackListView";
    }

    @GetMapping("searchBlackList")
    public String searchBlackList(HttpServletRequest request, Model model, HttpSession session) throws ParseException {
        if(isUnauthorized(session)){
            return "forbidden";
        }
        String search = request.getParameter(SEARCH);
        Punishment punishment = convertToPunishment(request.getParameter(PUNISHMENT));
        Date dateFrom = getDateFromString(request.getParameter(DATE_FROM));
        Date dateTo = getDateFromString(request.getParameter(DATE_TO));
        boolean validDateFrom = true;
        boolean validDateTo = true;
        List<BlackList> blackList = (List<BlackList>) blackListRepo.findAll();
        if(!search.isEmpty()){
            blackList = getBlackListFilteredByString(blackList, search);
        }
        if(checkIfNotNull(punishment)){
            blackList = getBlackListFilteredByPunishment(blackList, punishment);
        }
        if(checkIfNotNull(dateFrom) && checkIfNotNull(dateTo)){
            validDateFrom = dateIsNotFuture(dateFrom);
            validDateTo = dateToIsValid(dateFrom, dateTo);
            if(validDateFrom && validDateTo){
                blackList = getBlackListFilteredByDateFrom(blackList, dateFrom);
                blackList = getBlackListFilteredByDateTo(blackList, dateTo);
            }
        }
        if(checkIfNotNull(dateFrom) && !checkIfNotNull(dateTo)){
            validDateFrom = dateIsNotFuture(dateFrom);
            if(validDateFrom){
                blackList = getBlackListFilteredByDateFrom(blackList, dateFrom);
            }
        }
        if(checkIfNotNull(dateTo) && !checkIfNotNull(dateFrom)){
            blackList = getBlackListFilteredByDateTo(blackList, dateTo);
            validDateTo = dateIsNotFuture(dateTo);
            if(validDateTo){
                blackList = getBlackListFilteredByDateFrom(blackList, dateFrom);
            }
        }
        model.addAttribute(BLACK_LIST, blackList);
        model.addAttribute(VALID_DATE_TO, validDateTo);
        model.addAttribute(VALID_DATE_FROM, validDateFrom);
        return "admin/blackListView";
    }
}
