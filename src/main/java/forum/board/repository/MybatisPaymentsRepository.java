package forum.board.repository;

import forum.board.controller.DTO.payForm;
import forum.board.repository.mybatisMapper.payMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisPaymentsRepository {

    private final payMapper payMapper;

    public void savePayBill(payForm payForm,Long memberId)
    {
        payMapper.savePayBill(payForm,memberId);
    }
}
