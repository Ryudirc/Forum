package forum.board.service;

import forum.board.controller.DTO.cartInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockCheckService {


    // 주문완료 선택시 장바구니에 있는 아이템이 품절된 상태인지 확인하는 메서드
    public Boolean isSoldOut(List<cartInfo> cartInfoList)
    {
        for (cartInfo cartInfo : cartInfoList) {
            if(cartInfo.getStock() == 0)
            {
                return true;
            }
        }
        return false;
    }

    // 주문완료 선택시 장바구니에 있는 상품의 수량보다 재고량이 적은경우가 존재하는지 확인하는 메서드
    public Boolean isMoreThanStock(List<cartInfo> cartInfoList)
    {
        for (cartInfo cartInfo : cartInfoList) {
            if (cartInfo.getStock() > 0) {
                if (cartInfo.getProdCnt() > cartInfo.getStock()) {
                    return true;
                }
            }
        }
        return false;
    }

    //장바구니에 상품이 존재하는지
    public Boolean isEmptyCart(List<cartInfo> cartInfoList)
    {
        if(cartInfoList.size() == 0 || cartInfoList.isEmpty()) {
            return true;
        }
        return false;
    }










}
