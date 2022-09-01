package forum.board.repository;

import forum.board.domain.UploadFile;
import forum.board.repository.RepositoryInterface.FileRepository;
import forum.board.repository.mybatisMapper.UploadFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MybatisFileRepository implements FileRepository {

    private final UploadFileMapper uploadFileMapper;

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
