package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.orderHistory;
import forum.board.controller.DTO.orderProd;
import forum.board.domain.orderList;
import forum.board.domain.orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ordersMapper {

    void saveOrder(orders order);

    void saveOrderList(List<orderList> orderList);

    List<orderProd> getOrderProd(@Param("memberId") Long memberId,@Param("orderId")Long orderId);

    List<orderHistory> getOrderHistoryByMemberId(Long memberId);


}
