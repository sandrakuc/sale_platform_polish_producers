package onlypolish.securityalert.generatedfile;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SecurityAlertFileController {

    @Autowired
    GeneratedFileRepo generatedFileRepo;

    private static final String FILE_ID = "fileId";
    private static final String SUBSTRING = "substring";

    private long getLongId(HttpServletRequest request, String parameter){
        String id = request.getParameter(parameter);
        long longId = Long.parseLong(id);
        return longId;
    }

    private List<GeneratedFile> getFilesWithSubstringInFileName(List<GeneratedFile> generatedFiles, String subs){
        List <GeneratedFile> newGeneratedFiles = generatedFiles.stream().filter(file -> file.getFileName().contains(subs)).collect(Collectors.toList());
        return getSortedFileList(newGeneratedFiles);
    }


    private List<GeneratedFile> getSortedFileList(List<GeneratedFile> generatedFiles){
        Collections.sort(generatedFiles, Collections.reverseOrder());
        return generatedFiles;
    }

    @RequestMapping("fileList")
    public String fileList(HttpServletRequest request, Model model){
        List<GeneratedFile> generatedFiles = (List<GeneratedFile>) generatedFileRepo.findAll();
        generatedFiles = getSortedFileList(generatedFiles);
        model.addAttribute("generatedFiles", generatedFiles);
        return "admin/fileList";
    }

    @GetMapping("downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, Docx4JException {
        long id = getLongId(request, FILE_ID);
        GeneratedFile generatedFile = generatedFileRepo.getById(id);
        DownloadGeneratedFile.INSTANCE.downloader(response, generatedFile.getFileName());
    }

    @GetMapping("searchFile")
    public String searchFile(HttpServletRequest request, Model model){
        List<GeneratedFile> generatedFiles = (List<GeneratedFile>) generatedFileRepo.findAll();
        String subs = request.getParameter(SUBSTRING);
        generatedFiles = getFilesWithSubstringInFileName(generatedFiles, subs);
        model.addAttribute("generatedFiles", generatedFiles);
        return "admin/fileList";
    }
}
