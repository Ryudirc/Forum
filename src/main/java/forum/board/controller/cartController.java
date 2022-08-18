package forum.board.controller;

import forum.board.controller.DTO.cartSaveForm;
import forum.board.controller.DTO.cartUpdateForm;
import forum.board.domain.Cart;
import forum.board.domain.loginMember;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.service.cartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 컨트롤러
 * - 장바구니 페이지 처리
 * - 장바구니 로직 처리
 */

@Controller
@RequiredArgsConstructor
public class cartController {

    private final cartService cartService;

    //장바구니 담기 선택시 동작하는 컨트롤러
    @PostMapping("/prodDetail/sendProd/{prodId}/{memberId}")
    public String sendProdToCart(@PathVariable Long prodId,@PathVariable Long memberId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) loginMember member, @ModelAttribute cartSaveForm cartSaveForm, Model model)
    {
        //장바구니 데이터베이스에 상품정보와 멤버정보를 비롯한 정보를 적재하고 쿼리문의 카운트 속성을 통해 장바구니에 담긴 상품의 개수를 리턴하여 모델에 담고 다시 상품상세로 이동한다.
        //validate - 회원이 로그인중인 상태인지 확인한다.

        //validate 성공 시 DB 적재 로직
        if(memberId == member.getMemberId()) {
            cartService.saveCartProd(prodId, member.getMemberId(), cartSaveForm);
            return "redirect:/bgshop/prodDetail/{prodId}";
        }
        return "redirect:/";
    }

    //장바구니 페이지 동작 컨트롤러
    @GetMapping("/bgshop/prodCart/{memberId}")
    public String getCartPage(@PathVariable Long memberId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember member,Model model)
    {
        //memberId 를 파라미터로 받는데 session 에서 멤버객체를 가져오는 이유는, 장바구니에 접속하고자 하는 멤버의 id와 현재 세션에 있는 멤버의 id가 일치하는지 보기 위함이다.
        // 파라미터의 memberId 와 세션에 있는 memberId 가 서로 다르면 장바구니를 보여줄수 없다.
        // memberId 를 통해 cart 에서 List 형태로 받아와 노출한다.

        List<Cart> cartList = new ArrayList<>();
        int cartCnt = 0;
        if(cartService.getCartCnt(memberId) != null) {
            cartCnt = (int) cartService.getCartCnt(memberId);
        }

        if(member.getMemberId() == memberId) {
            cartList = cartService.findProdByMemberId(memberId);
        }

        int totalPrice = cartService.calcCartProdTotal(cartList);

        model.addAttribute("member",member);
        model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
        model.addAttribute("cartList",cartList);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("cartCnt",cartCnt);

        return "shop/cart";
    }

    //장바구니에서 주문폼으로 데이터 전달시 동작하는 컨트롤러
    @PostMapping("/bgshop/sendOrder/{memberId}")
    public String sendCartToOrder(@PathVariable Long memberId,@SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false)loginMember member)
    {
        // 장바구니에서 checkout 을 누르면 장바구니에 담긴 상품정보와 멤버정보를 주문쪽 컨트롤러로 redirect 시키는 동작을 수행하는 컨트롤러
        // 마찬가지로 장바구니에 상품을 담은 멤버와 주문을 시도하려는 멤버가 서로 동일해야 정상적인 주문이 이뤄질 수 있으므로, 세션에 있는 멤버의 ID 값과 현재 주문을 시도하려는
        // 사용자의 멤버 ID 값을 비교하여 같으면 주문 진행이 이뤄질 수 있도록 한다.

        return "redirect:/bgshop/order/{memberId}";
    }

    //장바구니에서 수량업데이트 시 동작하는 컨트롤러
    @PostMapping("/bgshop/prodCart/quantityUpdate/{memberId}")
    public String updateQuantityProd(@PathVariable Long memberId, @ModelAttribute cartUpdateForm cartUpdateForm)
    {
        //memberId 와 쿼리파라미터로 날아온 prodId 값을 통해 cart 데이터베이스에서 상품을 찾아낸 뒤 수량을 업데이트한다.
        cartService.updateQuantityProd(cartUpdateForm.getProdId(), memberId, cartUpdateForm.getProdCnt());

        return "redirect:/bgshop/prodCart/{memberId}";
    }

    //장바구니에서 상품 삭제 시 동작하는 컨트롤러
    @GetMapping("/bgshop/prodCart/deleteProd/{memberId}/{prodId}")
    public String deleteProd(@PathVariable Long memberId, @PathVariable Long prodId)
    {
        cartService.deleteProd(prodId,memberId);
        //memberId 와 prodId 값을 통해 cart 데이터베이스에서 상품을 찾아내 삭제한다.

        return "redirect:/bgshop/prodCart/{memberId}";
    }


}
