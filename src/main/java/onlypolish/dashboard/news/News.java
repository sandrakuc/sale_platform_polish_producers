package onlypolish.dashboard.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="NEWS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News implements Comparable<News>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private Date date;

    private String imgPath;

    private String contentPath;

    @Override
    public int compareTo(News o) {
        return this.getDate().compareTo(o.getDate());
    }
}
