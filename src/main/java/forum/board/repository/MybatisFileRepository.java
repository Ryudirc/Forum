package forum.board.repository;

import forum.board.domain.UploadFile;
import forum.board.repository.RepositoryInterface.fileRepository;
import forum.board.repository.mybatisMapper.uploadFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MybatisFileRepository implements fileRepository {

    private final uploadFileMapper uploadFileMapper;

    @Override
    public void saveFile(UploadFile uploadFile) {
        uploadFileMapper.saveFile(uploadFile);
    }

    @Override
    public void saveFiles(List<UploadFile> uploadFiles) {
        uploadFileMapper.saveFiles(uploadFiles);
    }

    @Override
    public List<UploadFile> findFileById(Long itemId) {
         return uploadFileMapper.findFileById(itemId);
    }


}
