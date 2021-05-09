package ru.taxi.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @NonNull
    private Double lat;
    @NonNull
    private Double lng;
}
