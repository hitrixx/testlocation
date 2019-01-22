package com.eroad.testlocation.service;

import lombok.RequiredArgsConstructor;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
	private final TimeZoneEngine engine;

	public Optional<ZoneId> getTimeZone(Double latitude, Double longitude) {
		return engine.query(latitude, longitude);
	}
}
