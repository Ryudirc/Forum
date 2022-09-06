# Portfolio
### 기본 인적사항
 - 이메일 : cnh0090@naver.com
 - GitHub : https://github.com/Ryudirc
 - DISCORD : LickTe#7681
 
 ### 기술스택
 - #### 프론트엔드
    - HTML의 구조, BootStrap 사용하여 백엔드에서 구성한 로직을 통해 templateEngine 으로 view 를 제작할 수 있음
    - 약간의 JavaScript, Jquery를 공부하여 간단한 ajax 를 다룰 수 있음 
    
 - #### 백엔드
    - Java: 자바를 활용하여 비즈니스 로직을 작성할 수 있음
    - Spring: Spring MVC 구조를 활용하여 웹 서비스를 제작한 프로젝트가 존재
    - MySQL: mybatis 를 활용한 동적쿼리, 간단한 쿼리 작성 가능(JOIN 등)
    - Thymeleaf: 타임리프 템플릿 엔진을 활용하여 동적 웹페이지를 작성할 수 있음


# 개인 프로젝트 
### 주제
 - ### 보드게임 쇼핑몰과 고객간 보드게임에 관한 의견을 나눌 수 있는 커뮤니티 게시판을 포함한 웹 서비스


# 프로젝트 디테일
 ### 접속정보
 - ### 접속주소 : http://52.78.127.22:8080/  (AWS EC2 , RDS 사용)

 ### SQL DDL 정보
 - ### DDL : [https://drive.google.com/file/d/17R3tBRt23ZkHSRw37b-DeD2dJLP5Gd2Z/view?usp=sharing](https://drive.google.com/file/d/1I37arjD9byIi-NUVBGDVhf-bxhEt10QK/view?usp=sharing)

 ### 계정 정보
- ### Admin : admin / admin123
- ### User : testuser / test123



### 주요 기능과 로직
 - #### 공통
   - 회원/비회원 구분 : 인터셉터를 활용해 회원과 비회원의 웹 서비스 이용가능 범위 차별화
   - 로그인/로그아웃 기능 : 밸리데이션과 인터셉터를 활용한 세션방식의 로그인 기능 구현
   - 어드민 기능 : 인터셉터를 활용하여 쇼핑몰과 게시판에서의 모든 권한을 가지고 있는 어드민 구현
   
 - #### 보드게임 쇼핑몰
   - 장바구니 기능 : 원하는 상품을 장바구니에 추가하고 보관할 수 있는 기능 구현
   - 마이페이지 기능 : 아임포트 API를 활용해 결제모듈을 연동하여 결제기능 구현
   - 주문 기능 : 주문서에 Daum 주소API를 활용해 주소찾기 모듈을 연동하여 주소찾기기능 구현
   - 상품 필터 : 상품 카테고리별로 노출될 수 있도록 기능 구현
   - 연관상품추천 : 임의의 카테고리에 속한 상품 선택 시 해당 상품과 관련된 상품을 추천해주는 기능 구현
   
 - #### 보드게임 자유게시판
   - 페이징 기능 : 단위 페이지당 15개의 게시글을 노출하도록 백엔드에서 VIEW로 데이터를 전달
   - 게시글 업로드 : summerNoteAPI를 활용하여 글의 폰트, 크기, 색, 이미지 등 자유롭게 게시글을 업로드 할 수있도록 기능 구현
   - 파일 업로드 : 게시글 업로드 시 파일을 첨부하여 업로드 할 수 있도록 기능 구현
   - 검색 기능 : 작성자 이름, 게시글 제목, 제목 + 내용 으로 다양하게 검색가능하도록 게시글 검색기능 구현
   - 조회수 기능 : 게시글에 접근 시 DB에 해당 게시글의 조회수가 카운트되도록 하고 실시간으로 조회수가 노출되도록 구현
   
## 메인기능 1 : 쇼핑몰 전체 기능
1. 어드민으로 로그인하면 원하는 상품을 등록 및 자유롭게 수정, 삭제가 가능하며 가입한 회원의 상태를 변경할 수 있다.
[![메인기능1_1](http://img.youtube.com/vi/jV2HWLNqiwA/maxresdefault.jpg)](https://youtu.be/jV2HWLNqiwA)

2. 유저로 로그인하면, 어드민이 업로드한 상품이 보이며, 카테고리별로도 볼 수 있으며 장바구니에 담고 구매가 가능하다.
[![메인기능1_2](http://img.youtube.com/vi/ePH-LCA0sPQ/maxresdefault.jpg)](https://youtu.be/ePH-LCA0sPQ)


## 메인기능 2 : 게시판 전체 기능
1. 로그인한 사용자는 자유롭게 게시글을 올릴 수 있고, 자유롭게 검색 및 탐색이 가능하다.
[![메인기능2](http://img.youtube.com/vi/_8HvkKZAOi8/maxresdefault.jpg)](https://youtu.be/_8HvkKZAOi8)

## 메인기능 3 : 마이페이지
1. 마이페이지에서 회원정보를 변경하거나, 연동된 결제모듈로 포인트를 결제할 수 있다.
[![메인기능3](http://img.youtube.com/vi/nTuxDpQO7XQ/maxresdefault.jpg)](https://youtu.be/nTuxDpQO7XQ)
