package forum.board.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리될 파일명

    // 고객이 같은 파일명으로 서버에 업로드한 경우, 파일 overWrite 가 발생할 가능성이 매우 높으므로
    // 서버에서는 반드시 파일명을 다르게 하여 관리한다.


    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
