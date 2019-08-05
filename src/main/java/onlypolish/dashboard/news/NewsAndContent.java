package onlypolish.dashboard.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsAndContent implements Comparable<NewsAndContent> {
    private News news;

    private String content;

    @Override
    public int compareTo(NewsAndContent o) {
        return this.getNews().getDate().compareTo(o.getNews().getDate());
    }
}
