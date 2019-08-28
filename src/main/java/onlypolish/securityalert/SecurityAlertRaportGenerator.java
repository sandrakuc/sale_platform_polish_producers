package onlypolish.securityalert;

import onlypolish.dashboard.StringFromDateGenerator;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.util.*;

import static org.docx4j.org.apache.xml.security.utils.JavaUtils.getBytesFromFile;

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
}
