package onlypolish.dashboard.news;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public enum NewsAndContentOperations {

    INSTANCE;

    public String yearString(int year){
        year = 1900 + year;
        return String.valueOf(year);
    }

    public String getMonth(int month){
        month = month + 1;
        return getOtherTimeUnit(month);
    }

    public String getOtherTimeUnit(int timeUnit){
        if(timeUnit <= 9) return "0" + timeUnit;
        else return String.valueOf(timeUnit);
     }

    public String generateFileName(Date date) {

        String fileName = "news/news_";
        fileName += yearString(date.getYear());
        fileName += getMonth(date.getMonth());
        fileName += getOtherTimeUnit(date.getDate());
        fileName += getOtherTimeUnit(date.getHours());
        fileName += getOtherTimeUnit(date.getMinutes());
        fileName += getOtherTimeUnit(date.getSeconds());
        fileName += ".txt";
        return fileName;
    }

    public String getContent(String fileName) throws IOException {
        InputStream is = new FileInputStream(fileName);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String content = sb.toString();
        return content;
    }

    public NewsAndContent getMappedNewsAndContent(News news) throws IOException {
        String content = getContent(news.getContentPath());
        NewsAndContent newsAndContent = NewsAndContent.builder()
                .news(news)
                .content(content)
                .build();
        return newsAndContent;
    }

    public List<NewsAndContent> getSortedNewsAndContent(List <NewsAndContent> newsAndContents){
        Collections.sort(newsAndContents, Collections.reverseOrder());
        return newsAndContents;
    }

    public List<NewsAndContent> mapFromNewsList(List<News> news) throws IOException {
        List<NewsAndContent> newsAndContents = new ArrayList<>();
        for(News n : news){
            newsAndContents.add(getMappedNewsAndContent(n));
        }
        newsAndContents = getSortedNewsAndContent(newsAndContents);
        return newsAndContents;
    }

    public void writeNewsContent(String content, String fileName) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(content);
        }
    }
}
