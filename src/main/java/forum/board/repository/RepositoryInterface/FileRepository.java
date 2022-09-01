package forum.board.repository.RepositoryInterface;

import forum.board.domain.UploadFile;

import java.util.List;

public interface FileRepository {

    void saveFile(UploadFile uploadFile); // 단건 파일 저장

    void saveFiles(List<UploadFile> uploadFiles); // 여러개 파일 저장

    List<UploadFile> findFileById(Long itemId);

}
