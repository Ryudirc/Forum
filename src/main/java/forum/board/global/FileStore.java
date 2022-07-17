package forum.board.global;


import forum.board.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    @Value("${file.dire}") //summernote 로 부터 불러들인 이미지가 저장될 경로
    private String imageFileDir;


    public String getFullPath(String fileName)
    {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(Long itemId,List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty())
            {
                storeFiles.add(storeFile(itemId,multipartFile));
            }
        }
        return storeFiles;
    }



    public UploadFile storeFile(Long itemId, MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty())
        {
            return null;
        }
        log.info("multipartFile = {}",multipartFile);
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(itemId,originalFilename,storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String fileExt = extracted(originalFilename);// 원본 파일명으로 부터 확장자 추출
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + fileExt;
    }

    private String extracted(String originalFilename) {
        int delimiter = originalFilename.lastIndexOf("."); // 파일명과 확장명을 구분짓는 구분자의 위치
        return originalFilename.substring(delimiter + 1);

    }

}
