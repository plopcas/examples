package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuthController {

    @Value("${com.auth0.domain}")
    private String domain;

    @Value("${com.auth0.clientId}")
    private String clientId;

    @Value("${com.auth0.clientSecret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    @GetMapping("login-code")
    public ModelAndView getLoginCode(ModelMap model) {
        System.out.println("Login called");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("authorize")
                .queryParam("response_type", "code")
                .queryParam("scope", "openid")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/callback")
                .build();
        System.out.println("Redirecting to Authorization Server: " + uriComponents.toString());
        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    @GetMapping("login-implicit-form-post")
    public ModelAndView getLoginCodeFormPost(ModelMap model) {
        System.out.println("Login called");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("authorize")
                .queryParam("response_type", "id_token")
                .queryParam("response_mode", "form_post")
                .queryParam("scope", "openid")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/callback")
                .queryParam("nonce", RandomStringUtils.randomAlphanumeric(10))
                .build();
        System.out.println("Redirecting to Authorization Server: " + uriComponents.toString());
        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    @GetMapping("login-implicit-fragment")
    public ModelAndView getLoginCodeFragment(ModelMap model) {
        System.out.println("Login called");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("authorize")
                .queryParam("response_type", "id_token")
                .queryParam("scope", "openid")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/callback")
                .queryParam("nonce", RandomStringUtils.randomAlphanumeric(10))
                .build();
        System.out.println("Redirecting to Authorization Server: " + uriComponents.toString());
        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    @GetMapping("callback")
    public ModelAndView getCallback(HttpSession session, HttpServletRequest request, ModelMap model) throws IOException {
        System.out.println("Callback called GET: " + request.getRequestURL());

        if (request.getParameterMap().containsKey("code")) {

            String code = request.getParameter("code");

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(domain)
                    .path("oauth/token")
                    .build();

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("code", code);
            map.add("grant_type", "authorization_code");
            map.add("redirect_uri", "http://localhost:8080/callback"); // Must match the one when called /authorize

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, requestHeaders);

            System.out.println("Exchanging code: " + uriComponents.toString());
            ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toString(), HttpMethod.POST, requestEntity, String.class);
            System.out.println("Response" + responseEntity.getBody());

            HashMap<String, String> tokens = new ObjectMapper().readValue(responseEntity.getBody(), HashMap.class);
            session.setAttribute("tokens", tokens);
        } else {
            // It must be implicit - fragment, therefore nothing gets sent to the server

        }

        return new ModelAndView("redirect:/welcome", model);
    }

    @PostMapping("callback")
    private ModelAndView postCallback(@RequestBody MultiValueMap<String, String> body, HttpSession session, HttpServletRequest request, ModelMap model) {
        System.out.println("Callback called POST: " + request.getRequestURL());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("id_token", body.get("id_token").get(0));
        session.setAttribute("tokens", tokens);

        return new ModelAndView("redirect:/welcome", model);
    }

    @GetMapping("logout")
    public ModelAndView getLogout(HttpSession session, HttpServletRequest request) {
        System.out.println("Logout called: " + request.getRequestURL());

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("v2/logout")
                .queryParam("client_id", clientId)
                .queryParam("returnTo", "http://localhost:8080")
                .build();

        session.invalidate();

        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    @GetMapping("welcome")
    public ModelAndView getWelcome(HttpSession session, HttpServletRequest request, ModelMap model) throws IOException {
        System.out.println("Welcome called: " + request.getRequestURL());

        HashMap<String, String> tokens = (HashMap<String, String>) session.getAttribute("tokens");

        if (tokens != null && tokens.size() > 0) {
            String idToken = tokens.get("id_token");
            String idTokenPayload = idToken.split("\\.")[1];

            byte[] decodedIdTokenPayloadBytes = Base64.getDecoder().decode(idTokenPayload);
            String decodedIdTokenPayload = new String(decodedIdTokenPayloadBytes);

            HashMap<String, String> idTokenClaims = new ObjectMapper().readValue(decodedIdTokenPayload, HashMap.class);

            model.addAttribute("name", idTokenClaims.get("sub"));
        }

        return new ModelAndView("welcome", model);
    }

}
