package forum.board.controller;

import forum.board.controller.DTO.orderSaveForm;
import forum.board.domain.loginMember;
import forum.board.global.SessionConst;
import forum.board.service.ProductsService;
import forum.board.service.cartService;
import forum.board.service.orderService;
import forum.board.validation.orderValidator;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 주문 컨트롤러
 * - 장바구니 및 주문서 페이지를 처리함
 * - 장바구니 및 주문완료까지의 프로세스를 처리함
 */

@Controller
@RequiredArgsConstructor
public class orderController {

    private final cartService cartService;
    private final orderService orderService;

    private final orderValidator orderValidator;

    @GetMapping("/bgshop/order/{memberId}")
    public String getOrderForm(@PathVariable Long memberId, @SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) loginMember member, Model model)
    {
        if(member.getMemberId() == memberId) {
            int totalPrice = cartService.calcCartProdTotal(cartService.findProdByMemberId(memberId));
            int ownPoint = orderService.showMemberPoint(memberId);
            int consumePoint = ownPoint - totalPrice;
            model.addAttribute("cartList", cartService.findProdByMemberId(memberId));
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("ownPoint", ownPoint);
            model.addAttribute("consumePoint", consumePoint);
            model.addAttribute("orderSaveForm",new orderSaveForm());
            return "shop/orderForm";
        }
        return "redirect:/";
    }

    @PostMapping("/bgshop/order/{memberId}")
    public String processOrder(@PathVariable Long memberId, @Validated @ModelAttribute orderSaveForm orderSaveForm, BindingResult bindingResult, Model model)
    {
        int totalPrice = cartService.calcCartProdTotal(cartService.findProdByMemberId(memberId));
        int ownPoint = orderService.showMemberPoint(memberId);
        int consumePoint = ownPoint - totalPrice;
        orderSaveForm.setOwnPoint(ownPoint);
        orderSaveForm.setTotalPoint(totalPrice);

        //validate
        if(orderValidator.supports(orderSaveForm.getClass())) {
            orderValidator.validate(orderSaveForm,bindingResult);
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("cartList", cartService.findProdByMemberId(memberId));
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("ownPoint", ownPoint);
            model.addAttribute("consumePoint", consumePoint);
            return "shop/orderForm";
        }

        //성공시 구매처리 로직이후 마이페이지 구매내역으로 리다이렉트
        System.out.println("orderSaveForm = " + orderSaveForm);
        orderService.orderProcess(memberId,orderSaveForm);

        return "redirect:/";
    }





}
