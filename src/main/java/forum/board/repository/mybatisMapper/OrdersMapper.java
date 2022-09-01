package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.OrderHistory;
import forum.board.controller.DTO.OrderProd;
import forum.board.domain.OrderList;
import forum.board.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper {

    void saveOrder(Orders order);

    void saveOrderList(List<OrderList> orderList);

    List<OrderProd> getOrderProd(@Param("memberId") Long memberId, @Param("orderId")Long orderId);

    List<OrderHistory> getOrderHistoryByMemberId(Long memberId);


}
