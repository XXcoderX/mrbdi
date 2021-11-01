package com.mrbdi.vehiclerunmiles.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChargingStation{
    public String name;
    public String distance;
    public String limit;
}