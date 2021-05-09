package ru.taxi.apiservice.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import ru.taxi.apiservice.dto.AccessTokenResponse;
import ru.taxi.apiservice.dto.AuthenticationRequest;
import ru.taxi.apiservice.utils.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class HttpService {


    private final RestTemplate restTemplate;
    private final AsyncRestTemplate asyncRestTemplate;
    private final HttpServletRequest request;
    private final AuthorizationClient authorizationClient;


    public <T> T post(String url, HttpHeaders headers, Object body, Class<T> responseType) {
        return restTemplate.exchange(url,
                HttpMethod.POST,
                getHttpEntityFromDTO(body, headers),
                responseType
        ).getBody();
    }

    public <T> T post(String url, HttpHeaders headers, Object body, ParameterizedTypeReference<T> typeReference) {
        return restTemplate.exchange(url,
                HttpMethod.POST,
                getHttpEntityFromDTO(body, headers),
                typeReference
        ).getBody();
    }


    public <T> T put(String url, HttpHeaders headers, Object body, Class<T> responseType) {
        return restTemplate.exchange(url,
                HttpMethod.PUT,
                getHttpEntityFromDTO(body, headers),
                responseType
        ).getBody();
    }

    public <T> T get(String url, HttpHeaders headers, Class<T> responseType) {
        return restTemplate.exchange(url,
                HttpMethod.GET,
                getHttpEntityFromDTO(null, headers),
                responseType
        ).getBody();
    }


    private static HttpEntity<String> getHttpEntity(String json, HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, httpHeaders);
    }

    private static HttpEntity<String> getHttpEntity(HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(httpHeaders);
    }

    public HttpEntity<String> getHttpEntityFromDTO(Object obj, HttpHeaders httpHeaders) {
        enrichHeadersWithAccessToken(httpHeaders);
        return getHttpEntity(new Gson().toJson(obj), httpHeaders);

    }

    private void enrichHeadersWithAccessToken(HttpHeaders httpHeaders) {
        AccessTokenResponse accessTokenResponse = authorizationClient.acceptToken(buildAuthenticationRequest());
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenResponse.getToken());
    }


    private AuthenticationRequest buildAuthenticationRequest() {
        String principalId = Optional.ofNullable(request.getHeader("X_USER_MDM_ID")).orElseThrow(AuthorizationException::new);
        String principalSecret = Optional.ofNullable(request.getHeader("X_USER_MDM_SECRET")).orElseThrow(AuthorizationException::new);
        return new AuthenticationRequest(principalId, principalSecret);
    }
}
