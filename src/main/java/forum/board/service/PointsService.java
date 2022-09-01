package forum.board.service;

import forum.board.controller.DTO.PayForm;
import forum.board.domain.Member;
import forum.board.repository.MybatisMemberRepository;
import forum.board.repository.MybatisPaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointsService {

    private final MybatisMemberRepository memberRepository;
    private final MybatisPaymentsRepository paymentsRepository;

    public void addPoints(PayForm payForm, Long memberId) {
        // 포인트 적재 프로세스
        Member member = memberRepository.findById(memberId);
        int points = Integer.parseInt(payForm.getAmount());
        int ownPoint = member.getPoints();
        int resultPoint = ownPoint + points;
        member.setPoints(resultPoint);
        memberRepository.updatePoints(memberId, member);

        //결제내역 적재 프로세스
        if(member.getMemberName().equals(payForm.getBuyerName())) {
            paymentsRepository.savePayBill(payForm,memberId);
        }

    }
}
