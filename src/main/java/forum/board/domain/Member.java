package forum.board.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class Member {
    private Long memberId; //DB 관리용 회원 id

    private String memberAcct; //아이디
    private String memberPw; //비밀번호
    private String memberName; //닉네임

    private String memberEmail; //이메일

    private String Role; // 데이터베이스에 기본값이 USER 로 insert 되므로, 데이터베이스로부터 멤버 row 를 뽑아올때는 해당 역할을 가져와 매핑한다.

    private int points;

    public Member() {}

    public Member(String memberAcct, String memberPw, String memberName, String memberEmail) {
        this.memberAcct = memberAcct;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
    }

}
