package forum.board.controller;

import forum.board.domain.Products;
import forum.board.domain.loginMember;
import forum.board.global.AuthConst;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
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
            if (category.equals("normalProd")) {
                if(member == null) {
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    return "shop/normalProd";
                }else {
                    model.addAttribute("products", prodByCategory);
                    model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
                    model.addAttribute("member",member);
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
                    model.addAttribute("member",member);
                    return "shop/loginDiscountProd";
                }
            }

           return "redirect:/";
    }

    // 상품상세 화면 처리(권한별 처리)
    @GetMapping("/bgshop/prodDetail/{prodId}")
    public String getProdDetail(@PathVariable Long prodId,Model model)
    {
        Products findProd = productsService.findProdById(prodId);
        List<Products> relatedProdList = new ArrayList<>();
        if(findProd.getProdCategory().equals("normalProd")){
            relatedProdList = productsService.getRelatedProd(findProd.getProdCategory());
        }
        if(findProd.getProdCategory().equals("discountProd")){
            relatedProdList = productsService.getRelatedProd(findProd.getProdCategory());
        }

        model.addAttribute("relatedProdList",relatedProdList);
        model.addAttribute("product",findProd);
        model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
        return "shop/prodDetail";
    }




}
