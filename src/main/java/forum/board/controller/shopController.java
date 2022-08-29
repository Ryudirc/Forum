package forum.board.controller;

import forum.board.domain.Cart;
import forum.board.domain.Products;
import forum.board.domain.loginMember;
import forum.board.global.AuthConst;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.repository.MybatisMemberRepository;
import forum.board.service.ProductsService;
import forum.board.service.cartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

/**
 * 쇼핑몰 컨트롤러
 * - 로그인 사용자(유저, 어드민)의 역할(Role)에 따라 다른 페이지 요청처리
 * - 카테고리별 페이지 처리(유저, 어드민,비회원)
 * - 상품상세 페이지 처리
 */

@Controller
@RequiredArgsConstructor
public class shopController {

    private final ProductsService productsService;
    private final cartService cartService;

    private final MybatisMemberRepository memberRepository;



    //로그인 사용자 역할(Role)에 따라 각기 다른페이지 요청
    @GetMapping("profile/{userName}")
    public String getUserInfo(@PathVariable String userName, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember member)
    {
        if(member.getMemberName().equals(userName))
        {
            if(member.getRole().equals(AuthConst.USER))
            {
                return "redirect:/profile/myPage/{userName}";
            }
            if(member.getRole().equals(AuthConst.ADMIN))
            {
                return "redirect:/profile/admin";
            }
        }
        return "redirect:/";
    }

    //카테고리별 화면 처리(회원권한별 처리)
    @GetMapping("/bgshop/prodCategory/{category}")
    public String getProdCategory(@PathVariable String category,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember member,Model model)
    {
        List<Products> prodByCategory = productsService.findProdByCategory(category);
        int cartCnt = 0;
            if (category.equals("normalProd")) {
                if(member == null) {
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    return "shop/normalProd";
                }else {
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    model.addAttribute("member",memberRepository.findById(member.getMemberId()));
                    if(cartService.getCartCnt(member.getMemberId()) != null) {
                       cartCnt = (int)cartService.getCartCnt(member.getMemberId());
                    }
                    model.addAttribute("cartCnt",cartCnt);
                    return "shop/loginNormalProd";
                }
            }
            if(category.equals("discountProd")) {
                if(member == null){
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    return "shop/discountProd";
                }else{
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    model.addAttribute("member",memberRepository.findById(member.getMemberId()));
                    if(cartService.getCartCnt(member.getMemberId()) != null) {
                        cartCnt = (int)cartService.getCartCnt(member.getMemberId());
                    }
                    model.addAttribute("cartCnt",cartCnt);
                    return "shop/loginDiscountProd";
                }
            }

           return "redirect:/";
    }

    // 상품상세 화면 처리(권한별 처리)
    @GetMapping("/bgshop/prodDetail/{prodId}")
    public String getProdDetail(@PathVariable Long prodId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember member, Model model)
    {
        Products findProd = productsService.findProdById(prodId);
        List<Products> relatedProdList = productsService.getRelatedProd(findProd.getProdCategory());
        int cartCnt = 0;

        if(findProd.getProdCategory().equals("normalProd"))
        {
            return getProdDetailURL(member, model, findProd, relatedProdList,cartCnt);
        }
        if(findProd.getProdCategory().equals("discountProd"))
        {
            return getProdDetailURL(member, model, findProd, relatedProdList,cartCnt);
        }
        return "redirect:/";
    }

    // getProdDetail 메서드에 사용되는 메서드로, 코드중복적인 부분만 리팩터링해서 따로 추출한 메서드임
    public String getProdDetailURL(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) loginMember member, Model model, Products findProd, List<Products> relatedProdList, int prodCnt) {


        if(member != null)
        {
            prodCnt = (int)cartService.getCartCnt(member.getMemberId());
            List<Cart> cartList = cartService.findProdByMemberId(member.getMemberId());
            for (Cart cart : cartList) {
                if(cart.getProdId() == findProd.getProdId()){
                    model.addAttribute("message","이미 장바구니에 존재하는 상품입니다.");
                }
            }
            model.addAttribute("member",memberRepository.findById(member.getMemberId()));
            model.addAttribute("product",findProd);
            model.addAttribute("relatedProdList",relatedProdList);
            model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
            model.addAttribute("cartCnt",prodCnt);
            return "shop/loginProdDetail";
        }else {

            model.addAttribute("product",findProd);
            model.addAttribute("relatedProdList",relatedProdList);
            model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
            model.addAttribute("cartCnt",prodCnt);
            return "shop/prodDetail";
        }
    }


}
