package forum.board.repository;

import forum.board.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class MemoryMemberRepository implements memberRepository{

   private final Map<Long,Member> memberRepository = new HashMap<>();
   private Long sequence = 0L;

    @Override
    public void save(Member member) {
        member.setMemberId(++sequence);
        memberRepository.put(member.getMemberId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.get(memberId);
    }

    @Override
    public void update(Long memberId, Member updateMember) {
        Member findMember = memberRepository.get(memberId);
        findMember.setMemberPw(updateMember.getMemberPw());
        findMember.setMemberName(updateMember.getMemberName());
    }

    @Override
    public Member delete(Long memberId) {
        return memberRepository.remove(memberId);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.values().stream().toList();
    }
}
