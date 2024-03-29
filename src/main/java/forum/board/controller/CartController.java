package forum.board.controller;

import forum.board.controller.DTO.CartInfo;
import forum.board.controller.DTO.CartSaveForm;
import forum.board.controller.DTO.CartUpdateForm;
import forum.board.domain.Cart;
import forum.board.domain.LoginMember;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.global.cartConvert;
import forum.board.repository.MybatisMemberRepository;
import forum.board.service.StockCheckService;
import forum.board.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 장바구니 컨트롤러
 * - 장바구니 페이지 처리
 * - 장바구니 로직 처리
 */

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final cartConvert cartConvert;

    private final StockCheckService stockCheckService;
    private final MybatisMemberRepository memberRepository;




    //장바구니 담기 선택시 동작하는 컨트롤러
    @PostMapping("/prodDetail/sendProd/{prodId}")
    public String sendProdToCart(@PathVariable Long prodId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)LoginMember member,@ModelAttribute CartSaveForm cartSaveForm)
    {

        if(member.getMemberId() != null) {

            Cart cart = cartService.findById(prodId, member.getMemberId());
            System.out.println("prodId = " + prodId);
            System.out.println("member.getMemberId() = " + member.getMemberId());
            System.out.println("cart = " + cart);



            if(cart != null && cart.getProdId().equals(prodId)){
                cartService.plusProdQuantity(member.getMemberId(),prodId,cartSaveForm);
            }else {
                cartService.saveCartProd(prodId, member.getMemberId(), cartSaveForm);
            }
            return "redirect:/bgshop/prodDetail/{prodId}";
        }

        return "redirect:/";
    }



    //장바구니 페이지 동작 컨트롤러
    @GetMapping("/bgshop/prodCart/{memberId}")
    public String getCartPage(@PathVariable Long memberId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        //memberId 를 파라미터로 받는데 session 에서 멤버객체를 가져오는 이유는, 장바구니에 접속하고자 하는 멤버의 id와 현재 세션에 있는 멤버의 id가 일치하는지 보기 위함이다.
        // 파라미터의 memberId 와 세션에 있는 memberId 가 서로 다르면 장바구니를 보여줄수 없다.

        if(member.getMemberId().equals(memberId)) {

            List<Cart> cartList;
            List<CartInfo> cartInfoList;
            int cartCnt = 0;
            if (cartService.getCartCnt(memberId) != null) {
                cartCnt = cartService.getCartCnt(memberId);
            }

            cartList = cartService.findProdByMemberId(memberId);
            cartInfoList = cartConvert.CartConvertToCartInfo(cartList);

            int totalPrice = cartService.calcCartProdTotal(cartList);

            for (CartInfo cartInfo : cartInfoList) {
                System.out.println("cartInfo = " + cartInfo);
            }

            Boolean isSoldOut = stockCheckService.isSoldOut(cartInfoList);
            Boolean isMoreThanStock = stockCheckService.isMoreThanStock(cartInfoList);
            Boolean isEmptyCart = stockCheckService.isEmptyCart(cartInfoList);

            /*System.out.println("isSoldOut 값은 = " + isSoldOut);
            System.out.println("isMoreThanStock 값은 = " + isMoreThanStock);*/

            model.addAttribute("member", memberRepository.findById(memberId));
            model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
            model.addAttribute("cartList", cartInfoList);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartCnt", cartCnt);
            model.addAttribute("isSoldOut", isSoldOut);
            model.addAttribute("isMoreThanStock", isMoreThanStock);
            model.addAttribute("isEmptyCart",isEmptyCart);
            return "shop/cart";
        }
        return "redirect:/";
    }

    //장바구니에서 주문폼으로 데이터 전달시 동작하는 컨트롤러
    @PostMapping("/bgshop/sendCart/{memberId}")
    public String sendCartToOrder(@PathVariable Long memberId,@SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) LoginMember member)
    {
        if(member.getMemberId().equals(memberId)) {

            List<CartInfo> cartInfoList = cartConvert.CartConvertToCartInfo(cartService.findProdByMemberId(memberId));
            Boolean isSoldOut = stockCheckService.isSoldOut(cartInfoList);
            Boolean isMoreThanStock = stockCheckService.isMoreThanStock(cartInfoList);
            Boolean emptyCart = stockCheckService.isEmptyCart(cartInfoList);
            if (isSoldOut || isMoreThanStock || emptyCart) {
                return "redirect:/bgshop/prodCart/{memberId}";
            }
            return "redirect:/bgshop/order/{memberId}";
        }else
            return "redirect:/";
    }

    //장바구니에서 수량업데이트 시 동작하는 컨트롤러
    @PostMapping("/bgshop/prodCart/quantityUpdate/{memberId}")
    public String updateQuantityProd(@PathVariable Long memberId, @ModelAttribute CartUpdateForm cartUpdateForm)
    {
        //memberId 와 쿼리파라미터로 날아온 prodId 값을 통해 cart 데이터베이스에서 상품을 찾아낸 뒤 수량을 업데이트한다.
        cartService.updateQuantityProd(cartUpdateForm.getProdId(), memberId, cartUpdateForm.getProdCnt());

        return "redirect:/bgshop/prodCart/{memberId}";
    }

    //장바구니에서 상품 삭제 시 동작하는 컨트롤러
    @GetMapping("/bgshop/prodCart/deleteProd/{memberId}/{prodId}")
    public String deleteProd(@PathVariable Long memberId, @PathVariable Long prodId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)LoginMember member)
    {
        //상품번호와 회원고유번호(memId)만 알면 회원이 다른 회원의 장바구니에 있는 상품을 삭제할 수있다. 이 점을 수정하기위해 장바구니에 있는 상품 삭제 전 세션에 있는 회원데이터와 비교를 통해 값을 지울 수 있도록 수정했다.

        if(member.getMemberId().equals(memberId)) {
            cartService.deleteProd(prodId, memberId);
            //memberId 와 prodId 값을 통해 cart 데이터베이스에서 상품을 찾아내 삭제한다.
            return "redirect:/bgshop/prodCart/{memberId}";
        }else
            return "redirect:/";
    }


}
