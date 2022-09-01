package forum.board.repository;

import forum.board.domain.Item;
import forum.board.repository.mybatisMapper.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisPaginationRepository {

    private final PaginationMapper paginationMapper;

    public List<Item> findAllRows()
    {
        return paginationMapper.getTotalRow();
    }

    public List<Item> findPageSize(int start, int end)
    {
        return paginationMapper.getPageSize(start,end);
    }


}
