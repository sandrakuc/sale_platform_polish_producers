package onlypolish.securityalert.generatedfile;

import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum DownloadGeneratedFile {
    INSTANCE;

    public void downloader(HttpServletResponse response, String fileName) throws Docx4JException, IOException {
        Path file = Paths.get(fileName);
        ServletOutputStream outStream = response.getOutputStream();
        if(Files.exists(file)){
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            Files.copy(file, outStream);
            outStream.flush();
            outStream.close();
            return;
        }
    }
}
