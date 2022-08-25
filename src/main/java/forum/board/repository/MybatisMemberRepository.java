package forum.board.repository;

import forum.board.controller.DTO.memberUpdateForm;
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


    private final memberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public Member findById(Long memberId)
    {
        return memberMapper.findById(memberId);
    }

    @Override
    public Member findByLoginInfo(String loginId,String loginPw)
    {
        return memberMapper.findByLoginInfo(loginId,loginPw);
    }

    @Override
    public String findByLoginId(String id) {
        return memberMapper.findByLoginId(id);
    }

    @Override
    public String findByName(String name) {
        return memberMapper.findByName(name);
    }

    @Override
    public String findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }


    @Override
    public void update(Long memberId, Member updateMember) {
        memberMapper.update(memberId,updateMember);
    }

    @Override
    public void updatePoints(Long memberId, Member member) {
        memberMapper.updatePoints(memberId,member);
    }

    @Override
    public void updateByUser(Long memberId, memberUpdateForm memberUpdateForm) {
        memberMapper.updateByUser(memberId,memberUpdateForm);

    }

    @Override
    public void updateRole(Long memberId,String role)
    {
        memberMapper.updateRole(memberId,role);
    }

    @Override
    public void updateConsumePoint(Long memberId, int minusPoint) {
        memberMapper.updateConsumePoint(memberId,minusPoint);
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
