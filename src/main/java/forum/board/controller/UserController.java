package forum.board.controller;

import forum.board.controller.DTO.*;
import forum.board.domain.Member;
import forum.board.domain.Products;
import forum.board.domain.LoginMember;
import forum.board.global.SessionConst;
import forum.board.global.SessionManager;
import forum.board.repository.MybatisMemberRepository;
import forum.board.repository.MybatisProdFileRepository;
import forum.board.service.ProductsService;
import forum.board.service.MemberService;
import forum.board.service.OrderService;
import forum.board.service.PointsService;
import forum.board.validation.ProdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 유저 컨트롤러
 * - 어드민의 경우 관리자 화면으로 접속하고 유저의 경우 개인의 마이페이지 화면으로 이동
 * - 어드민 관리자 페이지 기능
 * - 마이페이지 기능
 */

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ProdValidator prodValidator;

    private final ProductsService productsService;

    private final OrderService orderService;
    private final MybatisMemberRepository memberRepository;

    private final MybatisProdFileRepository prodFileRepository;

    private final SessionManager sessionManager;

    private final MemberService memberService;

    private final PointsService pointsService;


    /**
     * 유저 마이페이지 기능 - 회원정보,주문정보
     */

    // 회원 개인정보를 보여주는 대문 마이페이지
    @GetMapping("profile/myPage/{userName}")
    public String getUserMyPage(@PathVariable String userName, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        if(member.getMemberName().equals(userName)) {
            if(member.getRole().equals("USER")) {
                model.addAttribute("mem", memberRepository.findById(member.getMemberId()));
                return "shop/userMyPage";
            }else if(member.getRole().equals("ADMIN")){
                return "redirect:/profile/admin";
            }
        }
        return "redirect:/";
    }


   // 회원이 주문한 상품정보를 테이블로 보여주는 페이지
    @GetMapping("profile/myPage/order/{memberId}")
    public String getUserOrderListPage(@PathVariable Long memberId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        int seq=0;
        if(member.getMemberId() == memberId) {
          List<OrderHistory> orderHistoryList = orderService.getOrderHistory(member.getMemberId());
              for (OrderHistory orderHistory : orderHistoryList) {
                  orderHistory.setModalSequence(++seq);
              }
            model.addAttribute("orderHistoryList",orderHistoryList);
            model.addAttribute("member",memberRepository.findById(memberId));
            return "shop/userOrderHistory";
        }

        return "redirect:/";
    }

    //내 정보페이지 불러오기
    @GetMapping("profile/myPage/userConfirm/{userName}")
    public String getUserConfirmPage(@PathVariable String userName, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        if(member.getRole().equals("ADMIN")){
            return "redirect:/profile/admin";
        }

        if(member.getMemberName().equals(userName)) {
            model.addAttribute("memberId",member.getMemberId());
            model.addAttribute("memberName",member.getMemberName());
            return "shop/confirmPw";
        }

        return "redirect:/";
    }

    @PostMapping("profile/myPage/userConfirm/{userName}")
    public String confirmUser(@PathVariable String userName, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, HttpServletRequest request)
    {
        if(member.getMemberName().equals(userName)) {
            String memberPw = request.getParameter("memberPw");
            if (memberRepository.findById(member.getMemberId()).getMemberPw().equals(memberPw)) {

                return "redirect:/profile/myPage/userInfo/{userName}";
            }
        }
      return "redirect:/";
    }




    @GetMapping("profile/myPage/userInfo/{userName}")
    public String getUserInfoPage(@PathVariable String userName, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        if(member.getRole().equals("ADMIN")){
            return "redirect:/profile/admin";
        }

        if(member.getMemberName().equals(userName)) {

            model.addAttribute("memberInfo",memberRepository.findById(member.getMemberId()));
            model.addAttribute("memberUpdateForm",new MemberUpdateForm());

            return "shop/userProfile";
        }
        return "redirect:/";
    }

    //내 정보 수정하기
    @PostMapping("profile/myPage/userEdit/{userName}")
    public String updateMemberInfo(@PathVariable String userName, @Validated MemberUpdateForm memberUpdateForm, BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, HttpServletRequest request, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("memberInfo",memberRepository.findById(member.getMemberId()));
            return "shop/userProfile";
        }

        if(member.getMemberName().equals(userName)) {
            //멤버 정보 변경 로직
            memberRepository.updateByUser(member.getMemberId(),memberUpdateForm);

            //세션값 변경
            sessionManager.changeSessionMemberName(request,memberUpdateForm);

            model.addAttribute("memberInfo",memberRepository.findById(member.getMemberId()));
            return "shop/userProfile";
        }

        return "redirect:/";
    }

    //포인트 충전페이지 불러오기
    @GetMapping("profile/myPage/pay/{userName}")
    public String getPaymentPointsPage(@PathVariable String userName, @SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        if(member.getRole().equals("ADMIN")) {
            return "redirect:/profile/admin";
        }

        Member mem = memberRepository.findById(member.getMemberId());
        model.addAttribute("member",mem);

        return "shop/payments";
    }

    //충전될 포인트가 처리되는 메서드
    @PostMapping("profile/myPage/payments/{userName}")
    @ResponseBody
    public void paymentsProcess(@PathVariable String userName, @ModelAttribute PayForm payForm, @SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) LoginMember member)
    {
        if(userName.equals(member.getMemberName())) {
           // System.out.println("payForm = " + payForm);
            pointsService.addPoints(payForm, member.getMemberId());
        }
    }





    /**
     * 어드민 관리자 페이지 기능 - 상품 CRUD
     */

    //AuthCheckInterceptor 가 해당 링크로 직접적인 요청이 들어오면 권한을 체크하여 허용할지 안할지 판단한다.
    @GetMapping("profile/admin")
    public String getAdminPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember member, Model model)
    {
        // 모든 상품리스트를 적재하여 모델에 담아 view 로 넘긴다.
        List<Products> prodAll = productsService.getProdAll();
        model.addAttribute("products",prodAll);
        //세션에 있는 어드민 정보를 view 로 넘긴다.
        model.addAttribute("member", member);
        return "shop/admin";
    }

    /**
     * 상품 썸네일 이미지를 Resource 로 반환하여 화면에 노출시켜주도록 한다.
     */
    @GetMapping(value = "/prodImage/{prodId}",produces = "image/jpeg")
    @ResponseBody
    public Resource getProdLogo(@PathVariable Long prodId) throws MalformedURLException {

        return new UrlResource("file:" + productsService.getProdImg(prodFileRepository.findProdFileById(prodId).getStoreFileName()));
    }

    /**
     * 결제수단 이미지 불러와서 썸네일로 띄워주기 위한 메서드
     */
    @GetMapping("/payImg/{payName}")
    @ResponseBody
    public Resource getPayMethodImg(@PathVariable String payName) throws MalformedURLException {


        if(payName.equals("kakao")) {
            String kakaoPath = "/home/ec2-user/apps/payImg/kakaoPay.png";
            return new UrlResource("file:" + kakaoPath);
        }else {
            String tossPath = "/home/ec2-user/apps/payImg/tossPay.jpg";
            return new UrlResource("file:" + tossPath);
        }
    }


    @GetMapping("profile/admin/addProd")
    public String getAddProdForm(@ModelAttribute("product") ProdSaveForm product)
    {
        return "shop/addProdForm";
    }

   @PostMapping("profile/admin/addProd")
    public String saveProd(@Validated @ModelAttribute("product") ProdSaveForm product, BindingResult bindingResult) throws IOException {
        //validate
       if(prodValidator.supports(product.getClass()))
       {
           prodValidator.validate(product,bindingResult);
           if(bindingResult.hasErrors())
           {
               return "shop/addProdForm";
           }
       }

        //저장로직
        productsService.prodAddProcess(product);

        return "redirect:/profile/admin";
    }


    @GetMapping("profile/admin/prod/update/{prodId}")
    public String getUpdateProdForm(@PathVariable Long prodId,Model model)
    {
        Products product = productsService.findProdById(prodId);
        model.addAttribute("product",product);
        return "shop/editProdForm";
    }


    @PostMapping("profile/admin/prod/update/{prodId}")
     public String updateProd(@PathVariable Long prodId, @ModelAttribute("product") ProdSaveForm product, BindingResult bindingResult) throws IOException {
        //validate
        if(prodValidator.supports(product.getClass()))
        {
            prodValidator.validate(product,bindingResult);
            if(bindingResult.hasErrors())
            {

                return "shop/editProdForm";
            }
        }

        //상품 수정로직
        productsService.updateProdProcess(prodId,product);


        return "redirect:/profile/admin";
    }

    @GetMapping("profile/admin/prod/delete/{prodId}")
    public String deleteProd(@PathVariable Long prodId)
    {
        productsService.deleteProdProcess(prodId);
        return "redirect:/profile/admin";
    }

    /**
     * 어드민 관리자 페이지 기능 - 회원관리(권한변경 및 포인트 관리조작)
     */

    @GetMapping("profile/admin/member/manage")
    public String getUserManageForm(Model model)
    {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "shop/userManage";
    }

    @GetMapping("profile/admin/member/manage/update/{memberId}")
    public String userRoleUpdate(@PathVariable Long memberId,@RequestParam("role") String role)
    {

        //memberId 로 받은 member 객체에서 Role 값을 파라미터로 받은 값으로 변경하여 다시 DB 에 적재
        Member member = memberRepository.findById(memberId);
        member.setRole(role);
        memberRepository.updateRole(memberId,member.getRole());

        return "redirect:/profile/admin/member/manage";
    }


    @GetMapping("profile/admin/member/manage/UpdatePoint/{memberId}")
    public String getUpdateUserPointPage(@PathVariable Long memberId,Model model)
    {
        Member member = memberRepository.findById(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("memberName",member.getMemberName());
        model.addAttribute("memberPoints",member.getPoints());

        return "shop/changePoints";
    }

    @GetMapping("profile/admin/member/manage/addPoint/{memberId}")
    public String getAddUserPointPage(@PathVariable Long memberId,Model model)
    {
        Member member = memberRepository.findById(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("memberName",member.getMemberName());
        model.addAttribute("memberPoints",member.getPoints());

        return "shop/userAddPoints";
    }



    @PostMapping("profile/admin/member/manage/UpdatePoint/{memberId}")
    public String changeUserPoints(@PathVariable Long memberId,HttpServletRequest request)
    {
        memberService.changeMemberPoints(memberId,request);

        return "shop/PointsModifySuccess";
    }

    @PostMapping("profile/admin/member/manage/addPoint/{memberId}")
    public String addUserPoints(@PathVariable Long memberId,HttpServletRequest request)
    {
        memberService.addMemberPoints(memberId,request);

        return "shop/PointsModifySuccess";
    }



}
