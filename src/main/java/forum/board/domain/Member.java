package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
    private Long memberId; //DB 관리용 회원 id
    private String memberAcct; //아이디
    private String memberPw; //비밀번호
    private String memberName; //닉네임

    public Member() {}

    public Member(String memberAcct, String memberPw, String memberName) {
        this.memberAcct = memberAcct;
        this.memberPw = memberPw;
        this.memberName = memberName;
    }
}
