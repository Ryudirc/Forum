package forum.board.domain;

import lombok.Data;

@Data
public class UploadFile {
    private Long fileId; // auto_increment

    private Long itemId; // 생성된 게시글의 id를 입력받아 파일명과 매핑
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리될 파일명

    // 고객이 같은 파일명으로 서버에 업로드한 경우, 파일 overWrite 가 발생할 가능성이 매우 높으므로
    // 서버에서는 반드시 파일명을 다르게 하여 관리한다.


    public UploadFile(Long itemId, String uploadFileName, String storeFileName) {
        this.itemId = itemId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
