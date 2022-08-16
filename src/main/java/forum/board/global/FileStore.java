package forum.board.global;


import forum.board.domain.UploadFile;
import forum.board.domain.UploadProdFile;
import forum.board.repository.MybatisProdFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileStore {

    private final MybatisProdFileRepository prodFileRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Value("${file.dire}") //summernote 로 부터 불러들인 이미지가 저장될 경로
    private String imageFileDir;

    //상품 이미지 보관경로
    @Value("${prodFile.dir}")
    private String ProdImgFileDir;



    public String getFullPath(String fileName)
    {
        return fileDir + fileName;
    }

    public String getImgFullPath(String imgFileName)
    {
        return imageFileDir + imgFileName;
    }

    // 상품이미지 처리용 메서드
    public String getProdImgFileFullPath(String prodImgFileName) {

        //System.out.println("prodImgFullPath 출력중 = " + ProdImgFileDir + prodImgFileName);
        return ProdImgFileDir + prodImgFileName;
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


    public String storeImgFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty())
        {
            return null;
        }
        String originalImgName = multipartFile.getOriginalFilename();
        String storeImgName = createStoreFileName(originalImgName);
        multipartFile.transferTo(new File(getImgFullPath(storeImgName)));

        return storeImgName;
    }

    // 상품 이미지 저장 처리용 메서드
    public UploadProdFile storeProdImgFile(Long ProdId, MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty())
        {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getProdImgFileFullPath(storeFileName)));

        return new UploadProdFile(ProdId,originalFilename,storeFileName);
    }

    //상품 이미지 SRC 링크 불러오기 메서드
    public String getProdImgDir(String fileName)
    {
        return  getProdImgFileFullPath(prodFileRepository.findProdFileByName(fileName).getStoreFileName());

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
