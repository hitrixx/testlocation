package com.eroad.testlocation.service;

import com.eroad.testlocation.domain.Vehicle;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileAppenderServiceTest {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	@Autowired
	private LocationService locationService = new LocationService(TimeZoneEngine.initialize());
	@Autowired
	private CSVWriter csvWriter;
	@Autowired
	private CSVReader csvReader;
	@Autowired
	private Writer writer;

	@Autowired
	private FileAppenderService fileAppenderService;

	@Test
	public void fileShouldBeLoadedCorrectly() throws IOException {
		Vehicle vehicle1 = new Vehicle(
				LocalDateTime.parse("2013-07-10 02:52:49", formatter),
				-44.490947,
				171.220966
		);

		Vehicle vehicle2 = new Vehicle(
				LocalDateTime.parse("2013-07-10 02:52:49", formatter),
				-33.912167,
				151.21582
		);

		List<Vehicle> vehicles = fileAppenderService.loadVehiclesFromFile();

		Assert.assertTrue(vehicles.contains(vehicle1));
		Assert.assertTrue(vehicles.contains(vehicle2));

	}

	@Test
	public void fileShouldBeSavedCorrectly() throws IOException {
		Vehicle vehicle1 = new Vehicle(
				LocalDateTime.parse("2013-07-10 02:52:49", formatter),
				-44.490947,
				171.220966
		);
		vehicle1.setTimeZone("Pacific/Auckland");
		vehicle1.setLocalisedDateTime(
				LocalDateTime.parse("2013-07-10T14:52:49", formatterT)
		);

		Vehicle vehicle2 = new Vehicle(
				LocalDateTime.parse("2013-07-10 02:52:49", formatter),
				-33.912167,
				151.21582
		);
		vehicle2.setTimeZone("Australia/Sydney");
		vehicle2.setLocalisedDateTime(
				LocalDateTime.parse("2013-07-10T12:52:49", formatterT)
		);
		List<Vehicle> vehicles = fileAppenderService.loadVehiclesFromFile();
		vehicles = fileAppenderService.saveVehicles2File(vehicles);

		Assert.assertEquals(2, vehicles.size());

		Assert.assertEquals(
				vehicles.get(0).getLocalisedDateTime(),
				vehicle1.getLocalisedDateTime()
		);

		Assert.assertEquals(
				vehicles.get(0).getTimeZone(),
				vehicle1.getTimeZone()
		);

		Assert.assertEquals(
				vehicles.get(1).getLocalisedDateTime(),
				vehicle2.getLocalisedDateTime()
		);

		Assert.assertEquals(
				vehicles.get(1).getTimeZone(),
				vehicle2.getTimeZone()
		);
	}
}
