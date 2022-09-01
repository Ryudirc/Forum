package forum.board.controller;

import forum.board.controller.DTO.OrderSaveForm;
import forum.board.domain.LoginMember;
import forum.board.global.SessionConst;
import forum.board.service.CartService;
import forum.board.service.OrderService;
import forum.board.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
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
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    private final OrderValidator orderValidator;

    @GetMapping("/bgshop/order/{memberId}")
    public String getOrderForm(@PathVariable Long memberId, @SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        if(member.getMemberId() == memberId) {
            int totalPrice = cartService.calcCartProdTotal(cartService.findProdByMemberId(memberId));
            int ownPoint = orderService.showMemberPoint(memberId);
            int consumePoint = ownPoint - totalPrice;
            model.addAttribute("cartList", cartService.findProdByMemberId(memberId));
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("ownPoint", ownPoint);
            model.addAttribute("consumePoint", consumePoint);
            model.addAttribute("orderSaveForm",new OrderSaveForm());
            return "shop/orderForm";
        }
        return "redirect:/";
    }

    @PostMapping("/bgshop/order/{memberId}")
    public String processOrder(@PathVariable Long memberId, @Validated @ModelAttribute OrderSaveForm orderSaveForm, BindingResult bindingResult, Model model)
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
        orderService.orderProcess(memberId,orderSaveForm);

        return "redirect:/profile/myPage/order/{memberId}";
    }





}
