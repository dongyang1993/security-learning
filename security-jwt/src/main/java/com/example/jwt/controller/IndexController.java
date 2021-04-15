package com.example.jwt.controller;

import com.example.jwt.api.Rs;
import com.example.jwt.util.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/auth")
@Controller
public class IndexController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.github.authorization-uri}")
    private String authorizationUri;
    @Value("${spring.security.oauth2.client.registration.github.token-uri}")
    private String tokenUri;

    @GetMapping("/index")
    public String index() {
        String encode = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String url = authorizationUri + "?" + "client_id=" + clientId;
        return "redirect:" + url;
    }

    @GetMapping("/github/callback")
    @ResponseBody
    public Rs<String> githubOauth2(@RequestParam("code") String code) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("client_id", clientId);
            map.put("client_secret", clientSecret);
            map.put("code", code);
            String resp = OkHttpUtil.postForm(tokenUri, map);
            System.out.println(resp);
            return Rs.ok(resp);
        } catch (IOException e) {
            log.error("", e);
            return Rs.failed();
        }
    }

    @GetMapping("/github/user")
    @ResponseBody
    public Rs<?> user(@RequestParam("token") String token, @RequestParam("token_type") String tokenType) throws IOException {
        String res = OkHttpUtil.get("https://api.github.com/user", tokenType + " " + token);
        return Rs.ok(res);
    }
}
