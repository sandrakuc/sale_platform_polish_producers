package onlypolish.user;

import java.util.Date;

public class BlackListBuilder {
    public BlackList buildBlackList(User user, Punishment punishment){
        BlackList blackList = BlackList.builder()
                .userName(user.getName())
                .userSurname(user.getSurname())
                .userLogin(user.getLogin())
                .userEmail(user.getEmail())
                .date(new Date())
                .punishment(punishment)
                .build();
        return blackList;
    }
}
