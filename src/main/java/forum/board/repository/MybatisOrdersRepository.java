package forum.board.repository;

import forum.board.controller.DTO.orderHistory;
import forum.board.controller.DTO.orderProd;
import forum.board.controller.DTO.viewOrderForm;
import forum.board.domain.orders;
import forum.board.domain.orderList;
import forum.board.repository.mybatisMapper.orderHistoryMapper;
import forum.board.repository.mybatisMapper.ordersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisOrdersRepository {

    private final ordersMapper ordersMapper;

    public void saveOrder(orders order)
    {
        ordersMapper.saveOrder(order);
    }

    public void saveOrderList(List<orderList> orderList)
    {
        ordersMapper.saveOrderList(orderList);
    }

   public List<orderProd> getOrderProd(Long memberId,Long orderId)
   {
       return ordersMapper.getOrderProd(memberId,orderId);
   }

   public List<orderHistory> getOrderHistoryByMemberId(Long memberId)
   {
       return ordersMapper.getOrderHistoryByMemberId(memberId);
   }


}
