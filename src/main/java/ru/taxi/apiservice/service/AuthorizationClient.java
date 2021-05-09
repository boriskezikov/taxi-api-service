package ru.taxi.apiservice.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.taxi.apiservice.config.FeignConfig;
import ru.taxi.apiservice.dto.AccessTokenResponse;
import ru.taxi.apiservice.dto.AuthenticationRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "session", url = "http://localhost:8083",
        path = "api/v1/auth/session", configuration = FeignConfig.class)
public interface AuthorizationClient {


    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    AccessTokenResponse acceptToken(@RequestBody @Validated AuthenticationRequest authenticationRequest);

}