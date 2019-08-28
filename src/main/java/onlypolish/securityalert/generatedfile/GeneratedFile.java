package onlypolish.securityalert.generatedfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="GENERATED_FILES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedFile implements Comparable<GeneratedFile> {

    public static final String DIR_NAME = "security_alerts_raports/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private Date generatedDate;

    @Override
    public int compareTo(GeneratedFile generatedFile) {
        return this.getGeneratedDate().compareTo(generatedFile.getGeneratedDate());
    }

    public String getFileNameWithoutDirectoryName(){
        String filteredFileName = fileName.replace(DIR_NAME, "");
        return filteredFileName;
    }
}
