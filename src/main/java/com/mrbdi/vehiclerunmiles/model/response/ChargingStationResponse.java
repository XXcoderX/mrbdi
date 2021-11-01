package com.mrbdi.vehiclerunmiles.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ChargingStationResponse{
    public String source;
    public String destination;
    public List<ChargingStation> chargingStations;
    public String error;
}