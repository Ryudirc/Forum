package forum.board.repository.mybatisMapper;

import forum.board.domain.UploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    void saveFile(UploadFile uploadFile); // 단건 파일 저장

    void saveFiles(List<UploadFile> uploadFiles); // 여러개 파일 저장

    List<UploadFile> findFileById(Long itemId); // 파일 찾아오기


}
