package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.viewOrderForm;
import forum.board.domain.orderList;
import forum.board.domain.orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ordersMapper {

    void saveOrder(orders order);

    void saveOrderList(List<orderList> orderList);



}
