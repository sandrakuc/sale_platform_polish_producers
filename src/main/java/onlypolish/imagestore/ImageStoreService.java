package onlypolish.imagestore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageStoreService {
    private static final String UPLOAD_ROOT = "C:\\Users\\Sandra\\IdeaProjects\\only_polish\\src\\main\\resources\\static";

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageStoreService(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public Resource findOneImage(String fileName){
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + fileName);
    }

    public void createImage(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_ROOT, multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
