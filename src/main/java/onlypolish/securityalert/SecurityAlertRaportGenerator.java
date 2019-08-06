package onlypolish.securityalert;

import onlypolish.dashboard.StringFromDateGenerator;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public enum SecurityAlertRaportGenerator {

    INSTANCE;

    private String generateFileName(Date date) {
        String fileName = "security_alerts_raports/security_";
        fileName += StringFromDateGenerator.INSTANCE.stringFromDateGenerator(date);
        fileName += ".docx";
        return fileName;
    }

    public String generateDoc(SecurityAlert securityAlert) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText(SecurityAlertTemplateHelper.HEADING_3, SecurityAlertTemplateHelper.TYPE + SecurityAlertTemplateHelper.getTypeDescription(securityAlert));
        mainDocumentPart.addParagraphOfText(SecurityAlertTemplateHelper.INFORMER_LOGIN + securityAlert.getInformer().getLogin());
        mainDocumentPart.addParagraphOfText(SecurityAlertTemplateHelper.DATE + securityAlert.getDate().toString());
        mainDocumentPart.addParagraphOfText(SecurityAlertTemplateHelper.DESC + securityAlert.getDescription());
        mainDocumentPart.addParagraphOfText(SecurityAlertTemplateHelper.URL + securityAlert.getUrl());
        mainDocumentPart.addParagraphOfText(SecurityAlertTemplateHelper.GUILTY_LOGIN + securityAlert.getGuilty().getLogin());
        String fileName = generateFileName(securityAlert.getDate());
        File exportFile = new File(fileName);
        wordPackage.save(exportFile);
        return fileName;
    }

    public void downloader(HttpServletResponse response, SecurityAlert securityAlert) throws Docx4JException, IOException {
        String fileName = generateDoc(securityAlert);
        Path file = Paths.get(fileName);
        if(Files.exists(file)){
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            Files.copy(file, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }




}
