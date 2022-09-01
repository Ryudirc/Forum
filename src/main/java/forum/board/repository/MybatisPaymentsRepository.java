package forum.board.repository;

import forum.board.controller.DTO.PayForm;
import forum.board.repository.mybatisMapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisPaymentsRepository {

    private final PayMapper payMapper;

    public void savePayBill(PayForm payForm, Long memberId)
    {
        payMapper.savePayBill(payForm,memberId);
    }
}
