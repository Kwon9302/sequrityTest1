# sequrityTest1
 - security를 custom하기 위해 SecurityConfig를 만들어 @Bean을 통해 등록한다.
 - FilterChainProxy는 Security와 관련된 필터 Bean들을 가진다. 
 - SecurityFilterChain를 구성하는 필터들을 불러와 적용한다.
 - SecuirtyFilterCHain내부에서 필터끼리의 정보 공유를 위해 SecurityContext를 가진다
 - SecurityContext는 Authentication값을 저장하는데 여기에는  Principal(유저정보), Credentials(토큰,비밀번호), Authorities(권한)를 저장한다.
 - SecurityContext는 한번 사용하고 나면 삭제된다.
 - SecurityContext는 SecurityContextHolder에서 전반적으로 관리한다.
 - SecurityContextHolder는 context를 관리하지만 등록,초기화,읽기와 같은 작업은 SecurityContextHolderStrategy 인터페이스에서 관리한다. 
