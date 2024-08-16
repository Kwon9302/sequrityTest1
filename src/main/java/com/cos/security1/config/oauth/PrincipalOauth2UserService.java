package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.KakaoUserInfo;
import com.cos.security1.config.oauth.provider.NaverUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService를 상속받아 naver,google로그인과 구현을 위한 커스터마이징을 위한 클래스이다.
    private final UserRepository userRepository;

    // userRequest 는 code를 받아서 accessToken을 응답 받은 객체
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 로그인시 바로 유저 정보가 들어온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // code를 통해 구성한 정보
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        System.out.println("userRequest AccessToken : "+ userRequest.getAccessToken().getTokenValue());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("oAuth2User.getAttributes() : " + oAuth2User.getAttributes());
        return processOAuth2User(userRequest, oAuth2User);
    }
        // OAuth2 요청으로 들어온 정보에따라 정보 가공
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청~~"); // oAuth2User는 Map방식이다.
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes()); // 의존성 주입

        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청~~");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes()); // 의존성 주입

        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청~~");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes()); // 의존성 주입

        }else {
            System.out.println("우리는 구글과 네이버만 지원해요 ㅎㅎ");
        }

        System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
        System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());

        // userOptional을 통해 naver, google로그인에 성공한 user들을 db에 저장한다.
//        Optional<User> userOptional =
//                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
                 User userEmail = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        // DB에 해당 email이 없을 경우 가입 불가.


        User user = null;
        if (userEmail == null) {
            System.out.println("가입 권한이 없습니다");
        } else {
            //            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            System.out.println("user not found ==> oAuthUser정보 db 저장");

            user = User.builder()
                    // provider + providerId를 조합해서 name으로 저장
                    .username(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .role("ROLE_USER") // 가입시 기본 role 부여
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        // User user;
//        if (userOptional.isPresent()) {
//            System.out.println("DB에 정보가 있음 ==> user update실시");
//            user = userOptional.get();
//            // user가 존재하면 update 해주기
//            user.setEmail(oAuth2UserInfo.getEmail());
//            userRepository.save(user);
//        } else {
//            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
//            System.out.println("user not found ==> oAuthUser정보 db 저장");
//
//            user = User.builder()
//                    // provider + providerId를 조합해서 name으로 저장
//                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
//                    .email(oAuth2UserInfo.getEmail())
//                    .role("ROLE_USER") // 가입시 기본 role 부여
//                    .provider(oAuth2UserInfo.getProvider())
//                    .providerId(oAuth2UserInfo.getProviderId())
//                    .build();
//            userRepository.save(user);
//        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
