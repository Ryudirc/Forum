package forum.board.domain;

import lombok.Data;

@Data
public class UploadProdFile {

    private Long ProdImgId; // auto_increment

    private Long ProdId; // 생성된 상품의 id를 입력받아 파일명과 매핑
    private String uploadFileName; // 어드민이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리될 파일명

    public UploadProdFile(Long prodId, String uploadFileName, String storeFileName) {
        ProdId = prodId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
