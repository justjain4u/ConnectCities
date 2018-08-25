package com.java.connected.cities.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.connected.cities.main.ConnectedMain;


@RestController()
public class ConnectCitiesControler {
	
	@RequestMapping("/connected")
	//?origin=Boston&destination=Newark
    public String greeting(@RequestParam(value="origin") String origin,@RequestParam(value="destination") String destination) {
		boolean connected = ConnectedMain.execute( origin,  destination);
		if (connected) {
			return "yes";
		}
		else {
			return "no";
		}
    }

}
