package forum.board.service;

import forum.board.controller.DTO.cartSaveForm;
import forum.board.domain.Cart;
import forum.board.repository.mybatisMapper.MybatisCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class cartService {

    private final MybatisCartRepository cartRepository;

    //상품을 카트데이터베이스에 적재
    public void saveCartProd(Long prodId,Long memberId, cartSaveForm form)
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




    //장바구니에 존재하는 상품이 품절된 상품인지 아닌지를 검사하는 메서드




}
