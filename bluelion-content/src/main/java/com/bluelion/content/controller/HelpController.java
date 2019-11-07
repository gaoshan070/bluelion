package com.bluelion.content.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelpController {

    Logger logger = LoggerFactory.getLogger(HelpController.class);

    @GetMapping("/content/qa_list")
    public String qaList() {
        return "qa_list";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }

//    @GetMapping("/getPrinciple")
//    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
//        logger.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
//        logger.info(oAuth2Authentication.toString());
//        logger.info("principal.toString() " + principal.toString());
//        logger.info("principal.getName() " + principal.getName());
//        logger.info("authentication: " + authentication.getAuthorities().toString());
//
//        return oAuth2Authentication;
//    }
}
