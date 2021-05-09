package ru.taxi.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionRequestDto {


    @NonNull
    private Double driverLat;

    @NonNull
    private Double driverLng;

}
