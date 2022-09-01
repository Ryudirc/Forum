package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 세션에 담길 멤버 객체 데이터
 * 세션에 멤버객체를 담으면, 너무 많은 정보를 담기 때문에 메모리 비용이 많이 들 수 밖에 없고 보안 면에서도 취약할 수 있으므로
 * 최소한의 정보(멤버고유아이디와 닉네임)만 담아 이 정보만으로 모든 과정이 처리될 수 있도록 한다.
 */
@Getter @Setter
public class LoginMember {

    private Long MemberId;
    private String MemberName;

    private String Role;

    public LoginMember(Long loginMemberId, String loginMemberName, String Role) {
        this.MemberId = loginMemberId;
        this.MemberName = loginMemberName;
        this.Role = Role;
    }
}
