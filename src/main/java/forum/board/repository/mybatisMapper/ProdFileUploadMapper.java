package forum.board.repository.mybatisMapper;


import forum.board.domain.UploadProdFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProdFileUploadMapper {

    void saveProdFile(UploadProdFile prodFile);

    UploadProdFile findProdFileByName(String fileName);

    UploadProdFile findProdFileById(Long prodId);


    void updateImgFile(@Param("updateFile")UploadProdFile updateFile,@Param("id")Long prodId);


}
