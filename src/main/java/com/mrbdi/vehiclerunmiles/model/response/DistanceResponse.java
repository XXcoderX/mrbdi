package com.mrbdi.vehiclerunmiles.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DistanceResponse{
    public String source;
    public String destination;
    public String distance;
    public String error;
}
