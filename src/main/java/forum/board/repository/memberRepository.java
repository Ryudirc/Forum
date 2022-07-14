package forum.board.repository;

import forum.board.domain.Member;

import java.util.List;
import java.util.Optional;

public interface memberRepository {

    void save(Member member);
    Optional<Member> findById(Long memberId);
    void update(Long memberId, Member updateMember);
    void delete(Long memberId);
    List<Member> findAll();


}
