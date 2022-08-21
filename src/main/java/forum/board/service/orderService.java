package forum.board.service;

import forum.board.controller.DTO.orderSaveForm;
import forum.board.controller.DTO.orderStock;
import forum.board.controller.DTO.viewOrderForm;
import forum.board.domain.Cart;
import forum.board.domain.orders;
import forum.board.domain.orderList;
import forum.board.repository.MybatisMemberRepository;
import forum.board.repository.MybatisOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class orderService {

    private final MybatisOrdersRepository ordersRepository;

    private final MybatisMemberRepository memberRepository;

    private final cartService cartService;

    private final ProductsService productsService;

    public int showMemberPoint(Long memberId)
    {
        return memberRepository.findById(memberId).getPoints();
    }

    public void orderProcess(Long memberId,orderSaveForm form)
    {
        //기본값 세팅
        List<Cart> cartList = cartService.findProdByMemberId(memberId);
        List<orderList> orderList = new ArrayList<>();
        List<orderStock> orderStockList = new ArrayList<>();
        int totalPrice = 0;
        String memAddress = form.getAddress() + form.getExtraAddress() + form.getDetailAddress();

        // order 객체 데이터베이스에 적재
        orders orders = new orders(memberId,form.getMemberRealName(), form.getMemberPhone(),form.getDeliveryRequirements(),memAddress);
        System.out.println("orders = " + orders);
        ordersRepository.saveOrder(orders);

        // 장바구니에서 주문한 상품으로 객체 convert
        for (Cart cart : cartList) {
            orderList.add(new orderList(orders.getOrderId(),cart.getProdName(),cart.getProdCnt(),cart.getProdPrice()));
            orderStockList.add(new orderStock(cart.getProdCnt(),cart.getProdId()));
            totalPrice += cart.getProdPrice() * cart.getProdCnt();
        }

        //orderStock 에 제대로된 값이 적재되었는지 확인
        for (orderStock orderStock : orderStockList) {
            System.out.println("orderStock = " + orderStock);
        }


        // 주문한 상품데이터 적재
        ordersRepository.saveOrderList(orderList);

        // 주문금액만큼 포인트 절감
        memberRepository.updateConsumePoint(memberId,totalPrice);

        // 주문 수량만큼 재고 절감
        productsService.updateStock(orderStockList);

        // 내 장바구니 목록 삭제
        cartService.deleteMyCartAll(memberId);

    }

    //마이페이지 - 주문내역 상품 불러오기 메서드
   /*public List<viewOrderForm> getViewOrderList(Long memberId)
    {
        return ordersRepository.findById(memberId);
    }*/


}
