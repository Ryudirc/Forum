package forum.board.repository;

import forum.board.domain.UploadProdFile;
import forum.board.repository.mybatisMapper.ProdFileUploadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisProdFileRepository {

    private final ProdFileUploadMapper prodFileUploadMapper;

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
