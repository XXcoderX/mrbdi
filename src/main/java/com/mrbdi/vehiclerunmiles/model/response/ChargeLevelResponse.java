package com.mrbdi.vehiclerunmiles.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChargeLevelResponse{
    public String vin;
    public String currentChargeLevel;
    public String error;
}