package com.example.telematics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverSpeedingDetails {

    private Date timestamp;
    private Double speed;

}
