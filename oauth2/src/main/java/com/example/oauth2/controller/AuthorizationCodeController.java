package com.example.oauth2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/authorization/code")
@Controller
public class AuthorizationCodeController {

    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${client.redirect.url}")
    private String clientRedirectURL;
    @Value("${client.yuque.authorize}")
    private String authorizeURL;
    @Value("${client.yuque.token}")
    private String tokenURL;
    @Value("${client.yuque.user}")
    private String userURL;


    @RequestMapping("/login")
    public String login() {
        return "redirect:" + authorizeURL + "?client_id=" + clientId + "&response_type=code&state=none" + "&redirect_uri=" + clientRedirectURL;
    }

    @RequestMapping("/getToken")
    public String getToken(@RequestParam("code") String code,
                         @RequestParam("state") String state, HttpServletResponse response) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            Map<String, String> params = new HashMap<>();
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("code", code);
            params.put("grant_type", "authorization_code");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(params);
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(tokenURL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .timeout(Duration.of(30L, ChronoUnit.SECONDS))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            String body = httpResponse.body();
            System.out.println(body);
            Map<String, String> map = mapper.readValue(body, new TypeReference<>() {
            });
            //{"access_token":"Mgv6yS8wIo7saUXcqPpXdQQ2seePrXBBTkons2Th","token_type":"bearer","scope":""}
            String accessToken = map.get("access_token");
            response.addHeader("Authorization", "Bearer " + accessToken);
        } catch (Exception e) {
            log.error("", e);
        }
        return "userInfo";
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(userURL))
                    .header("Authorization", token)
                    .timeout(Duration.of(30L, ChronoUnit.SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            String body = httpResponse.body();
            return body;
        } catch (Exception e) {
            log.error("", e);
            return e.getMessage();
        }
    }


}
