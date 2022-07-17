package forum.board.repository.mybatisMapper;

import forum.board.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface memberMapper {

    void save(Member member);
    Member findById(Long memberId);
    void update(@Param("id")Long memberId, @Param("updateMember") Member updateMember);
    Member delete(Long memberId);
    List<Member> findAll();


}
