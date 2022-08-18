package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.cartSaveForm;
import forum.board.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface cartMapper {

    void saveCartProd(@Param("prodId") Long prodId, @Param("form") cartSaveForm form, @Param("memberId") Long memberId);

    List<Cart> findByMemberId(Long memberId);

    void updateCartProd(@Param("prodId") Long prodId,@Param("memberId") Long memberId, @Param("updateQuantity") int updateQuantity);

    void deleteCartProd(@Param("prodId") Long prodId, @Param("memberId") Long memberId);

    Integer cartProdCount(Long memberId);


}
