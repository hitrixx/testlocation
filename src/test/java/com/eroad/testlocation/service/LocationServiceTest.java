package com.eroad.testlocation.service;

import com.eroad.testlocation.service.LocationService;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class LocationServiceTest {
	private LocationService locationService = new LocationService(TimeZoneEngine.initialize());

	@Test
	public void locationShouldBeRecognized() throws IOException {
		Optional<ZoneId> zoneIdAuckland = locationService.getTimeZone(-44.490947,171.220966);
		Optional<ZoneId> zoneIdSydney = locationService.getTimeZone(-33.912167,151.21582);

		Assert.assertEquals("Pacific/Auckland", zoneIdAuckland.get().toString());
		Assert.assertEquals("Australia/Sydney", zoneIdSydney.get().toString());

	}
}
