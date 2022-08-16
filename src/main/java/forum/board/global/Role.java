package forum.board.global;

/**
 * 고객 권한관리 및 권한등급
 * ADMIN - 어드민
 * BUYER - 보드게임몰 실구매자(보드게임을 한번이라도 구매한 이력이 있는 회원)
 * USER - 보드게임몰 회원(보드게임을 한번도 구매한적 없는 회원)
 * GUEST - 비회원
 */
public enum Role {

    ADMIN,USER,GUEST

}
