package forum.board.service;

import forum.board.controller.DTO.CartSaveForm;
import forum.board.domain.Cart;
import forum.board.repository.MybatisProductsRepository;
import forum.board.repository.MybatisCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MybatisCartRepository cartRepository;



    //상품을 카트데이터베이스에 적재
    public void saveCartProd(Long prodId,Long memberId, CartSaveForm form)
    {
        cartRepository.saveCartProd(prodId,form,memberId);
    }

    public Integer getCartCnt(Long memberId)
    {
        return cartRepository.cartProdCount(memberId);
    }

    public List<Cart> findProdByMemberId(Long memberId)
    {
        return cartRepository.findByMemberId(memberId);
    }

    public int calcCartProdTotal(List<Cart> cartList)
    {
        int totalPrice = 0;
        for (Cart cart : cartList) {
            totalPrice += (cart.getProdPrice() * cart.getProdCnt());
        }

        return totalPrice;
    }

    public void updateQuantityProd(Long prodId,Long memberId,int updateQuantity)
    {
        cartRepository.updateCartProd(prodId, memberId, updateQuantity);
    }

    public void deleteProd(Long prodId,Long memberId)
    {
        cartRepository.deleteCartProd(prodId, memberId);
    }

   public void deleteMyCartAll(Long memberId) { cartRepository.deleteCartAll(memberId);}


    public void plusProdQuantity(Long memberId,Long prodId,CartSaveForm form)
    {
        cartRepository.updatePlusQuantity(prodId,memberId,form.getProdCnt());
    }

    public Cart findById(Long prodId,Long memberId)
    {
        return cartRepository.findByProdId(prodId, memberId);
    }



}
