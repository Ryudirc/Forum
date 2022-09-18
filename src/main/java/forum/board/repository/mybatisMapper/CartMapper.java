package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.CartSaveForm;
import forum.board.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface CartMapper {

    void saveCartProd(@Param("prodId") Long prodId, @Param("form") CartSaveForm form, @Param("memberId") Long memberId);

    List<Cart> findByMemberId(Long memberId);

    Cart findByProdId(@Param("prodId")Long prodId,@Param("memberId")Long memberId);

    void updatePlusQuantity(@Param("prodId")Long prodId, @Param("memberId")Long memberId,@Param("plusQuantity")int plusQuantity);

    void updateCartProd(@Param("prodId") Long prodId,@Param("memberId") Long memberId, @Param("updateQuantity") int updateQuantity);

    void deleteCartProd(@Param("prodId") Long prodId, @Param("memberId") Long memberId);

    Integer cartProdCount(Long memberId);

    void deleteCartAll(Long memberId);


}
