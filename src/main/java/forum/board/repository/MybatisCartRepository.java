package forum.board.repository;

import forum.board.controller.DTO.cartSaveForm;
import forum.board.domain.Cart;
import forum.board.repository.mybatisMapper.cartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisCartRepository {

    private final cartMapper cartMapper;

    public void saveCartProd(Long prodId,cartSaveForm form,Long memberId)
    {
        cartMapper.saveCartProd(prodId,form,memberId);
    }

   public List<Cart> findByMemberId(Long memberId)
   {
        return cartMapper.findByMemberId(memberId);
   }

   public void updateCartProd(Long prodId,Long memberId,int updateQuantity)
   {
        cartMapper.updateCartProd(prodId,memberId,updateQuantity);
   }

   public void deleteCartProd(Long prodId,Long memberId)
   {
        cartMapper.deleteCartProd(prodId,memberId);
   }

   public Integer cartProdCount(Long memberId)
   {
        return cartMapper.cartProdCount(memberId);
   }

    public void deleteCartAll(Long memberId) { cartMapper.deleteCartAll(memberId);}

}
