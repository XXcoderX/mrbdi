package com.mrbdi.vehiclerunmiles.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mrbdi.vehiclerunmiles.model.request.MsRequestFormat;
import com.mrbdi.vehiclerunmiles.model.response.ChargeLevelResponse;
import com.mrbdi.vehiclerunmiles.model.response.ChargingStationResponse;
import com.mrbdi.vehiclerunmiles.model.response.DistanceResponse;

@FeignClient(value = "restmock", url = "https://restmock.techgig.com/merc", fallback = MockApiClientFallback.class)
public interface MockApisClient {

	@PostMapping(value = "/charge_level", consumes = { "application/json" })
	public ChargeLevelResponse getChargeLevel(String input);

	@PostMapping(value = "/distance", consumes = { "application/json" })
	public DistanceResponse getDistance(String input);

	@PostMapping(value = "/charging_stations", consumes = { "application/json" })
	public ChargingStationResponse getChargingStations(String input);

}
