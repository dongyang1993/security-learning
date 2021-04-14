package com.example.jwt.controller;

import com.example.jwt.api.Rs;
import com.example.jwt.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/getToken")
    public Rs<String> getToken() throws Exception {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String payload = TokenUtil.getDefaultPayload(principal.getUsername(), expiration);
        String token = TokenUtil.generateTokenByHMAC(payload, secret);
        return Rs.ok(tokenHead + token);
    }

    @RequestMapping("/fail")
    public Rs<?> fail(@RequestAttribute(value = "msg", required = false) String msg,
                      @RequestAttribute(value = "path", required = false) String path) {
        Rs<Object> rs = Rs.failed(msg);
        rs.setData(path);
        return rs;
    }

    @RequestMapping("/forbidden")
    public Rs<?> forbidden() {
        return Rs.forbidden();
    }
}
