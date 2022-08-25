package forum.board.repository.RepositoryInterface;

import forum.board.controller.DTO.memberUpdateForm;
import forum.board.domain.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface memberRepository {

    void save(Member member);
    Member findById(Long memberId);
    Member findByLoginInfo(String loginId,String loginPw);

    String findByLoginId(String id);

    String findByName(String name);

    String findByEmail(String email);

    void update(Long memberId, Member updateMember);

    void updatePoints(Long memberId, Member member);

    void updateByUser(Long memberId, memberUpdateForm memberUpdateForm);

    void updateRole(Long memberId, String role);

    void updateConsumePoint(Long memberId, int minusPoint);
    void delete(Long memberId);
    List<Member> findAll();


}
