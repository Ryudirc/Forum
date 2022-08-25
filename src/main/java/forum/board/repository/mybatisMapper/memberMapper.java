package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.memberUpdateForm;
import forum.board.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface memberMapper {

    void save(Member member);
    Member findById(Long memberId);

    Member findByLoginInfo(@Param("loginId")String loginId,@Param("loginPw")String loginPw);
    void update(@Param("id")Long memberId, @Param("updateMember") Member updateMember);

    void updatePoints(@Param("id")Long memberId, @Param("member")Member member);

    void updateByUser(@Param("memberId")Long memberId,@Param("memberUpdate") memberUpdateForm memberUpdateForm);

    void updateRole(@Param("id") Long id, @Param("role") String role);

    void updateConsumePoint(@Param("memberId")Long memberId, @Param("minusPoint")int minusPoint);

    String findByLoginId(String id);

    String findByName(String name);

    String findByEmail(String email);


    Member delete(Long memberId);
    List<Member> findAll();


}
