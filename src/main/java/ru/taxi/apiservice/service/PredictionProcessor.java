package ru.taxi.apiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taxi.apiservice.dto.PredictionRequestDto;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static ru.taxi.apiservice.utils.ApplicationConstants.XG_PREDICT_UIR;

@Service
public class PredictionProcessor {

    @Value("${services.predictor.xgboost_host}")
    private String xgboostApiHost;


    public String enrichXgRequest(PredictionRequestDto predictionRequestDto) {
        return UriComponentsBuilder.newInstance().uri(URI.create(xgboostApiHost + XG_PREDICT_UIR))
                .queryParam("driver_lat", predictionRequestDto.getDriverLat())
                .queryParam("driver_lng", predictionRequestDto.getDriverLng())
                .queryParam("timestamp", now()).toUriString();
    }
}
