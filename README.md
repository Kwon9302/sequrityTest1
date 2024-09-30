# Security 일지
 - security를 custom하기 위해 SecurityConfig를 만들어 @Bean을 통해 등록한다.
 - FilterChainProxy는 Security와 관련된 필터 Bean들을 가진다. 
 - SecurityFilterChain를 구성하는 필터들을 불러와 적용한다.
 - SecuirtyFilterCHain내부에서 필터끼리의 정보 공유를 위해 SecurityContext를 가진다
 - SecurityContext는 Authentication값을 저장하는데 여기에는  Principal(유저정보), Credentials(토큰,비밀번호), Authorities(권한)를 저장한다.
 - SecurityContext는 한번 사용하고 나면 삭제된며, 멀티 스레드로 작동한다.
 - SecurityContext는 SecurityContextHolder에서 전반적으로 관리한다.
 - SecurityContextHolder는 context를 관리하지만 등록,초기화,읽기와 같은 작업은 SecurityContextHolderStrategy 인터페이스에서 관리한다.


 - ServletFilter인터페이스  - GenericFilterBean  -  OncePerRequestFilter
 - GenericFilterBean : 다양한 필터 작업에 반복적으로 사용되는 Filter
 - OncePerRequestFilter : JWT인증, Session인증 등 하나의 요청 당 한 번만 실행되는 필터이다.
 - ==============================================================================

[//]: # ( - DisableEncodeUrlFilter)

[//]: # ( - DefaultSecurityFilterChain에 기본 등록되는 필터로 가장 첫번째에 위치한다. -> 세션id가 인코딩되어 로그에 출력되는것을 방지)

[//]: # ( - -> http)

[//]: # (   .sessionManagement&#40;&#40;manage&#41; -> manage.disable&#40;&#41;&#41;;)

[//]: # ( - disable시에 encode메서드들은 그대로 url을 반환&#40;원래값은 session값&#40;인코딩한&#41;을 포함&#41;)
 - DefaultSecurityFilterChain에 기본 등록되지 않는다. 웹 자체에서 url에 session id값을 포함시켜서 보내는데 
 - DisableEncodeUrlFilter를 addFilter를 통해 추가시키거나 .sessionManagement((manage) -> manage.disable()); 로 세션기능을 끄거나 2가지 방법이 있다.

### WebAsyncManagerIntegrationFilter
 - 비동기 처리를 관리해주는 필터:
 - WebAsyncMagerIntegrationFilter는 현재 쓰레드의 SecurityContext를 다룰 수 있는 SecurityContectCallableProcessingInterceptor를 WebAsyncManager에 등록만 진행한다.
이후 서블릿단에서 WebAsyncManager을 통해 새로운 쓰레드에 SecurityContext를 복제한다.

