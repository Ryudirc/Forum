package forum.board.repository;

import forum.board.controller.DTO.OrderHistory;
import forum.board.controller.DTO.OrderProd;
import forum.board.domain.Orders;
import forum.board.domain.OrderList;
import forum.board.repository.mybatisMapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisOrdersRepository {

    private final OrdersMapper ordersMapper;

    public void saveOrder(Orders order)
    {
        ordersMapper.saveOrder(order);
    }

    public void saveOrderList(List<OrderList> orderList)
    {
        ordersMapper.saveOrderList(orderList);
    }

   public List<OrderProd> getOrderProd(Long memberId, Long orderId)
   {
       return ordersMapper.getOrderProd(memberId,orderId);
   }

   public List<OrderHistory> getOrderHistoryByMemberId(Long memberId)
   {
       return ordersMapper.getOrderHistoryByMemberId(memberId);
   }


}
