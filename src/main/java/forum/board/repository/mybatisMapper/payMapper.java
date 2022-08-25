package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.payForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface payMapper {

    void savePayBill(@Param("payForm") payForm payForm,@Param("memberId")Long memberId);

}
