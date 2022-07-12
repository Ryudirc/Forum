package forum.board.repository;

import forum.board.domain.Member;

import java.util.List;

public interface memberRepository {

    void save(Member member);
    Member findById(Long memberId);
    void update(Long memberId, Member updateMember);
    Member delete(Long memberId);
    List<Member> findAll();


}
