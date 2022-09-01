package forum.board.global;

import forum.board.controller.DTO.CartInfo;
import forum.board.domain.Cart;
import forum.board.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class cartConvert {

    private final ProductsService productsService;

    public List<CartInfo> CartConvertToCartInfo(List<Cart> cartList)
    {
        ArrayList<CartInfo> cartInfoList = new ArrayList<>();
        for (Cart cart : cartList) {
           cartInfoList.add(new CartInfo(cart.getMemberId(),cart.getProdId(),cart.getProdName(),cart.getProdPrice(),cart.getProdCnt(),productsService.findProdById(cart.getProdId()).getProdStock()));
        }

        return cartInfoList;
    }

}
