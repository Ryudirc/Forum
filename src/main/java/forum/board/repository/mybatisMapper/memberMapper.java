package forum.board.repository.mybatisMapper;

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

    void updateRole(@Param("id") Long id, @Param("role") String role);

    String findByLoginId(String id);

    String findByName(String name);

    String findByEmail(String email);


    Member delete(Long memberId);
    List<Member> findAll();


}
