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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    @GetMapping("login-code-with-audience-for-api")
    public ModelAndView getLoginCodeWithAudienceForApi(ModelMap model) {
        System.out.println("Login called");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("authorize")
                .queryParam("response_type", "code")
                .queryParam("scope", "openid")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/callback")
                .queryParam("audience", "https://test1-api")
                .build();
        System.out.println("Redirecting to Authorization Server: " + uriComponents.toString());
        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    @GetMapping("login-code-pkce")
    public ModelAndView getLoginCodeWithPKCE(HttpSession session, ModelMap model) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("Login called");

        String codeVerifier = createCodeVerifier();
        String codeChallenge = createCodeChallenge(codeVerifier);

        session.setAttribute("code_verifier", codeVerifier);
        session.setAttribute("code_challenge", codeChallenge);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(domain)
                .path("authorize")
                .queryParam("response_type", "code")
                .queryParam("scope", "openid read:foo")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/callback")
                .queryParam("audience", "https://test1-api")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .build();
        System.out.println("Redirecting to Authorization Server: " + uriComponents.toString());
        return new ModelAndView("redirect:" + uriComponents.toString());
    }

    private String createCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        return verifier;
    }

    private String createCodeChallenge(String verifier) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] bytes = verifier.getBytes("US-ASCII");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        return challenge;
    }

    @GetMapping("callback")
    public ModelAndView getCallback(HttpSession session, HttpServletRequest request, ModelMap model) throws IOException {
        System.out.println("Callback called GET: " + request.getRequestURL());

        if (request.getParameterMap().containsKey("code") && session.getAttribute("code_verifier") == null) {

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
        } else if (session.getAttribute("code_verifier") != null) {
            String code = request.getParameter("code");

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(domain)
                    .path("oauth/token")
                    .build();

            // It also works with APPLICATION_FORM_URLENCODED and passing a map
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> map = new HashMap<String, String>();
            map.put("client_id", clientId);
            map.put("client_secret", clientSecret);
            map.put("code", code);
            map.put("grant_type", "authorization_code");
            map.put("redirect_uri", "http://localhost:8080/callback");
            map.put("code_verifier", (String) session.getAttribute("code_verifier"));

            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writer().writeValueAsString(map);

            HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);

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
            model.addAttribute("apiResult", getProtected(tokens));
        }

        return new ModelAndView("welcome", model);
    }

    private String getProtected(HashMap<String, String> tokens) {
        String response = null;
        if (tokens != null && tokens.size() > 0) {

            String accessToken = tokens.get("access_token");

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost:8081")
                    .path("foobar")
                    .build();

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", "Bearer " + accessToken);

            HttpEntity requestEntity = new HttpEntity(requestHeaders);

            try {
                System.out.println("Calling API: " + uriComponents.toString());
                ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, requestEntity, String.class);
                System.out.println("Response" + responseEntity.getBody());
                response = responseEntity.getBody();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                response = "Unauthorized!";
            }
        }

        return response;
    }

}
