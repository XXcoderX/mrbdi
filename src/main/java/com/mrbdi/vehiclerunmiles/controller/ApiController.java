package com.mrbdi.vehiclerunmiles.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbdi.vehiclerunmiles.feign.MockApisClient;
import com.mrbdi.vehiclerunmiles.model.request.MsRequestFormat;
import com.mrbdi.vehiclerunmiles.model.response.ChargeLevelResponse;
import com.mrbdi.vehiclerunmiles.model.response.ChargingStation;
import com.mrbdi.vehiclerunmiles.model.response.ChargingStationResponse;
import com.mrbdi.vehiclerunmiles.model.response.DistanceResponse;
import com.mrbdi.vehiclerunmiles.model.response.MsResponseFormat;
import com.mrbdi.vehiclerunmiles.service.ApiService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mrbdi")
@Slf4j
public class ApiController {

	@Autowired
	public MockApisClient client;

	@Autowired
	public ApiService apiSrvc;

	@PostMapping("/validate_journey")
	public ResponseEntity<MsResponseFormat> validateJourneyBasedOnChargeAndDistance(@RequestBody MsRequestFormat input)
			throws Exception {
		String vin = input.getVin();
		String source = input.getSource();
		String destination = input.getDestination();
		String transactionId = System.currentTimeMillis() + "" + String.format("%04d", new Random().nextInt(10000));
		log.info("Calling api with vin " + vin);
		MsResponseFormat response = new MsResponseFormat();
		response.setVin(vin);
		response.setTransactionId(transactionId);
		response.setSource(source);
		response.setDestination(destination);
		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("vin", vin);
		log.info("Calling chargelevel " + vin);
		ChargeLevelResponse chargeLevel = client.getChargeLevel(new ObjectMapper().writeValueAsString(parammap));
		log.info("END Calling chargelevel " + vin);
		if (chargeLevel.getError() != null) {
			List<com.mrbdi.vehiclerunmiles.model.response.Error> lsterror = new ArrayList<com.mrbdi.vehiclerunmiles.model.response.Error>();
			lsterror.add(new com.mrbdi.vehiclerunmiles.model.response.Error(chargeLevel.getError(), 9999));
			response.setErrors(lsterror);
			return new ResponseEntity<MsResponseFormat>(response, HttpStatus.OK);
		}
		long currentChargeLevel = Long.parseLong(chargeLevel.getCurrentChargeLevel());
		parammap.clear();
		parammap.put("source", source);
		parammap.put("destination", destination);
		log.info("Calling distance " + vin);
		DistanceResponse distance = client.getDistance(new ObjectMapper().writeValueAsString(parammap));
		log.info("END Calling distance " + vin);
		long distanceBetSourceDest = Long.parseLong(distance.getDistance());
		if (distance.getError() != null) {
			List<com.mrbdi.vehiclerunmiles.model.response.Error> lsterror = new ArrayList<com.mrbdi.vehiclerunmiles.model.response.Error>();
			lsterror.add(new com.mrbdi.vehiclerunmiles.model.response.Error(distance.getError(), 9999));
			response.setErrors(lsterror);
			return new ResponseEntity<MsResponseFormat>(response, HttpStatus.OK);
		}
		response.setCurrentChargeLevel(currentChargeLevel + "");
		response.setDistance(distanceBetSourceDest + "");
		if (currentChargeLevel >= distanceBetSourceDest) {
			response.setIsChargingRequired("false");
			return new ResponseEntity<MsResponseFormat>(response, HttpStatus.OK);
		}
		log.info("Calling chargestation " + vin);
		ChargingStationResponse chargeStationsList = client
				.getChargingStations(new ObjectMapper().writeValueAsString(parammap));
		log.info("END Calling chargestation " + vin);
		if (chargeStationsList.getError() != null) {
			List<com.mrbdi.vehiclerunmiles.model.response.Error> lsterror = new ArrayList<com.mrbdi.vehiclerunmiles.model.response.Error>();
			lsterror.add(new com.mrbdi.vehiclerunmiles.model.response.Error(
					"Unable to reach the destination with the current charge level", 8888));
			lsterror.add(new com.mrbdi.vehiclerunmiles.model.response.Error(chargeStationsList.getError(), 9999));
			response.setErrors(lsterror);
			return new ResponseEntity<MsResponseFormat>(response, HttpStatus.OK);
		}
		List<ChargingStation> stations = chargeStationsList.getChargingStations();
		long leastChargeRequiredToReachDestination = distanceBetSourceDest - currentChargeLevel;
		long maxDistanceTravelPossibleWithCurrentCharge = currentChargeLevel;
		List<String> requiredChargeStations = apiSrvc.getRequiredChargeStations(stations,
				maxDistanceTravelPossibleWithCurrentCharge, leastChargeRequiredToReachDestination);
		if (requiredChargeStations == null || requiredChargeStations.size() == 0) {
			response.setIsChargingRequired("true");
			List<com.mrbdi.vehiclerunmiles.model.response.Error> lsterror = new ArrayList<com.mrbdi.vehiclerunmiles.model.response.Error>();
			lsterror.add(new com.mrbdi.vehiclerunmiles.model.response.Error(
					"Unable to reach the destination with the current charge level", 8888));
			response.setErrors(lsterror);
		} else {
			response.setIsChargingRequired("true");
			response.setChargingStations(requiredChargeStations);
		}
		return new ResponseEntity<MsResponseFormat>(response, HttpStatus.OK);

	}

}
