package ru.taxi.apiservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taxi.apiservice.dto.PredictionRequestDto;
import ru.taxi.apiservice.dto.PredictionResponseDto;
import ru.taxi.apiservice.service.PredictionProxyService;

@RestController
@RequestMapping("/api/predict")
@RequiredArgsConstructor
public class ApiController {

    private final PredictionProxyService predictionProxyService;

    @PostMapping("/xg")
    public PredictionResponseDto predict(@Validated @RequestBody PredictionRequestDto predictionRequestDto) {
        return predictionProxyService.processPredictionRequest(predictionRequestDto);
    }
}
