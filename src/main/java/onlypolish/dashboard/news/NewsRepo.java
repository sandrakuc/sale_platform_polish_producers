package onlypolish.dashboard.news;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface NewsRepo extends CrudRepository<News, Long> {
    News getById(long id);
    News getByTitle(String title);
    News getByDate(Date date);
}
