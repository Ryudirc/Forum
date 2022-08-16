package forum.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 주문 컨트롤러
 * - 장바구니 및 주문서 페이지를 처리함
 * - 장바구니 및 주문완료까지의 프로세스를 처리함
 */

@Controller
@RequiredArgsConstructor
public class orderController {

    @GetMapping("/bgshop/order/{userName}")
    public String getOrderForm(@PathVariable String userName)
    {
        return "shop/orderForm";
    }





}
