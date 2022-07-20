package forum.board.repository.mybatisMapper;

import forum.board.domain.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface paginationMapper {

    List<Item> getTotalRow();

    List<Item> getPageSize(@Param("startRow") int startRow, @Param("endRow") int endRow);


}
