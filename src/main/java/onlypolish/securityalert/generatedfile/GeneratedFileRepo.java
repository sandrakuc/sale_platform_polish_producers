package onlypolish.securityalert.generatedfile;

import org.springframework.data.repository.CrudRepository;

public interface GeneratedFileRepo extends CrudRepository<GeneratedFile, Long> {
    GeneratedFile getByFileName(String fileName);
    GeneratedFile getById(long id);
}
