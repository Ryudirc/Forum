package forum.board.repository;

import forum.board.domain.UploadProdFile;
import forum.board.repository.mybatisMapper.prodFileUploadMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisProdFileRepository {

    private final prodFileUploadMapper prodFileUploadMapper;

    public void saveProdFile(UploadProdFile prodFile)
    {
        prodFileUploadMapper.saveProdFile(prodFile);
    }

    public UploadProdFile findProdFileByName(String fileName)
    {
        return prodFileUploadMapper.findProdFileByName(fileName);
    }

    public UploadProdFile findProdFileById(Long prodId)
    {
        return prodFileUploadMapper.findProdFileById(prodId);
    }

    public void updateImgFile(UploadProdFile updateFile, Long prodId)
    {
        prodFileUploadMapper.updateImgFile(updateFile,prodId);
    }



}
