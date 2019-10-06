//package com.bluelion.order.config.feign;
//
//import feign.RequestInterceptor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class ContentClientConfiguration {
//
//    @Value("${security.oauth2.content.access-token-uri}")
//    private String accessTokenUri;
//
//    @Value("${security.oauth2.content.client-id}")
//    private String clientId;
//
//    @Value("${security.oauth2.content.client-secret}")
//    private String clientSecret;
//
//    @Value("${security.oauth2.content.scope}")
//    private String scope;
//
//    @Bean
//    RequestInterceptor oauth2FeignRequestInterceptor() {
//        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
//    }
//
//    OAuth2ProtectedResourceDetails resource() {
//        List<String> scopes = new ArrayList<String>();
//        scopes.add("server");
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
//        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
//        details.setAccessTokenUri(accessTokenUri);
//        details.setClientId(clientId);
//        details.setClientSecret(clientSecret);
//        details.setScope(scopes);
//        details.setGrantType("client_credentials");
//        details.setUsername("user_1");
//        details.setPassword(finalPassword);
//        return details;
//    }
//
//}
