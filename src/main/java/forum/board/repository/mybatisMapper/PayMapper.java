package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.PayForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayMapper {

    void savePayBill(@Param("payForm") PayForm payForm, @Param("memberId")Long memberId);

}
