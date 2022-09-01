package forum.board.controller;

import forum.board.domain.Products;
import forum.board.domain.LoginMember;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.repository.MybatisMemberRepository;
import forum.board.service.ProductsService;
import forum.board.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

/**
 * HomePage 컨트롤러
 * - 로그인한 사용자에게 보여줄 loginIndex 페이지를 처리함
 * - Index 에 노출되는 상품리스트를 처리함
 */

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductsService productsService;
    private final CartService cartService;

    private final MybatisMemberRepository memberRepository;

    @GetMapping("/")
    public String HomeIndex(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember, Model model)
    {
        List<Products> prodAll = productsService.getProdAll();
        int cartCnt = 0;

        // 로그인을 하지 않은 사용자는 굳이 불필요하게 세션을 만들 이유가 없으니 false 로 세션을 가져와야 한다.
        if(loginMember == null)
        {
            model.addAttribute("products",prodAll);
            model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
            return "index";
        }

        if(cartService.getCartCnt(loginMember.getMemberId()) != null) {
            cartCnt = (int)cartService.getCartCnt(loginMember.getMemberId());
        }
        model.addAttribute("member",memberRepository.findById(loginMember.getMemberId()));
        model.addAttribute("products",prodAll);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
        model.addAttribute("cartCnt",cartCnt);

        return "loginIndex";
    }


}
