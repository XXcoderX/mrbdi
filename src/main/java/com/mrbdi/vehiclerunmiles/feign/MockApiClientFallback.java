package com.mrbdi.vehiclerunmiles.feign;

import com.mrbdi.vehiclerunmiles.model.response.ChargeLevelResponse;
import com.mrbdi.vehiclerunmiles.model.response.ChargingStationResponse;
import com.mrbdi.vehiclerunmiles.model.response.DistanceResponse;

public class MockApiClientFallback implements MockApisClient{

	@Override
	public ChargeLevelResponse getChargeLevel(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DistanceResponse getDistance(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargingStationResponse getChargingStations(String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
