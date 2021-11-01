package com.mrbdi.vehiclerunmiles.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mrbdi.vehiclerunmiles.model.response.ChargingStation;

@Service
public class ApiService {

	public List<String> getRequiredChargeStations(List<ChargingStation> lst, long travelPossible, long requiredCharge)
			throws Exception {
		Collections.sort(lst, (a, b) -> {
			return Long.parseLong(a.getDistance()) > Long.parseLong(b.getDistance()) ? 1
					: Long.parseLong(a.getDistance()) < Long.parseLong(b.getDistance()) ? -1 : 0;
		});
		List<String> leaststationslink = new LinkedList<String>();
		for (int i = 0; i < lst.size(); i++) {
			long updatedtravelpossible = travelPossible;
			long updatedchargerequired = requiredCharge;
			List<String> leaststationslinktemp = new LinkedList<String>();
			List<Long> distancelinknodes = new LinkedList<Long>();
			ChargingStation stationouter = lst.get(i);
			long distance = Long.parseLong(stationouter.getDistance());
			long limit = Long.parseLong(stationouter.getLimit());
			if (distance <= updatedtravelpossible) {
				distancelinknodes.add(distance);
				leaststationslinktemp.add(stationouter.getName());
				updatedchargerequired = updatedchargerequired - limit;
				updatedtravelpossible = updatedtravelpossible - distance + limit;
				if (updatedchargerequired > 0) {
					int k =1 ;
					while(i+k < lst.size()) {
						for (int j = i + k; j < lst.size(); j++) {
							if (updatedchargerequired > 0) {
								ChargingStation stationinner = lst.get(j);
								long distanceinner = Long.parseLong(stationinner.getDistance());
								long limitinner = Long.parseLong(stationinner.getLimit());
								if (distanceinner
										- distancelinknodes.get(distancelinknodes.size() - 1) <= updatedtravelpossible) {
									leaststationslinktemp.add(stationinner.getName());
									updatedchargerequired = updatedchargerequired - limitinner;
									updatedtravelpossible = updatedtravelpossible
											- (limitinner - distancelinknodes.get(distancelinknodes.size() - 1))
											+ limitinner;
								}
							}
						}
						if ((leaststationslink.size() == 0 || leaststationslink.size() > leaststationslinktemp.size())
								&& leaststationslinktemp.size() > 0) {
							leaststationslink = leaststationslinktemp; // swap if new chargestation combinations are least in number
																		// than previous entry
						}
						k++;
					}
				}
			}
			
		}
		return leaststationslink;
	}

}
