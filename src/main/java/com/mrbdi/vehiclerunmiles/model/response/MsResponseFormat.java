package com.mrbdi.vehiclerunmiles.model.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsResponseFormat implements Serializable {

	public String transactionId;
	public String vin;
	public String source;
	public String destination;
	public String distance;
	public String currentChargeLevel;
	public String isChargingRequired;
	public List<String> chargingStations;
	public List<com.mrbdi.vehiclerunmiles.model.response.Error> errors;

}
