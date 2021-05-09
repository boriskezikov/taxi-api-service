package ru.taxi.apiservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.taxi.apiservice.dto.LocationDto;
import ru.taxi.apiservice.dto.PredictionRequestDto;
import ru.taxi.apiservice.dto.PredictionResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionProxyService {

    private final HttpService httpService;
    private final PredictionProcessor predictionProcessor;

    public PredictionResponseDto processPredictionRequest(PredictionRequestDto requestDto) {
        String xgUri = predictionProcessor.enrichXgRequest(requestDto);
        List<LocationDto> post = httpService.post(xgUri, null, null, new ParameterizedTypeReference<>() {});
        return new PredictionResponseDto(post);
    }
}
