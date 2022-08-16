package forum.board.controller;

import forum.board.domain.loginMember;
import forum.board.domain.test;
import forum.board.global.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
