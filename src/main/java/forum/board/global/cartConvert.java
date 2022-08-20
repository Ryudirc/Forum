package forum.board.global;

import forum.board.controller.DTO.cartInfo;
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

    public List<cartInfo> CartConvertToCartInfo(List<Cart> cartList)
    {
        ArrayList<cartInfo> cartInfoList = new ArrayList<>();
        for (Cart cart : cartList) {
           cartInfoList.add(new cartInfo(cart.getMemberId(),cart.getProdId(),cart.getProdName(),cart.getProdPrice(),cart.getProdCnt(),productsService.findProdById(cart.getProdId()).getProdStock()));
        }

        return cartInfoList;
    }

}
