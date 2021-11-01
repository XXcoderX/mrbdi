package com.mrbdi.vehiclerunmiles.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MsRequestFormat implements Serializable {
	
	private String vin;
	private String source;
	private String destination;
	
	

}
