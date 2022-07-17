package forum.board.repository;

import forum.board.domain.Member;
import forum.board.repository.RepositoryInterface.memberRepository;
import forum.board.repository.mybatisMapper.memberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MybatisMemberRepository implements memberRepository {

   //private final Map<Long,Member> memberRepository = new HashMap<>();
  //private Long sequence = 0L;
    private final memberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public Optional<Member> findById(Long memberId)
    {
        return Optional.ofNullable(memberMapper.findById(memberId));
    }

    @Override
    public void update(Long memberId, Member updateMember) {
        memberMapper.update(memberId,updateMember);
    }

    @Override
    public void delete(Long memberId) {
        memberMapper.delete(memberId);
    }

    @Override
    public List<Member> findAll() {

        return memberMapper.findAll();
    }
}
