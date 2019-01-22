package com.eroad.testlocation.service;

import com.eroad.testlocation.domain.Vehicle;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileAppenderService {
	private final LocationService locationService;
	private final CSVWriter csvWriter;
	private final CSVReader csvReader;
	private final Writer writer;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String[] headerRecord = {"utc", "latitude", "longitude", "TimeZone", "LocalisedDateTime"};

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws IOException {
		log.info("FileAppenderService started");
		process();
	}

	private void process() throws IOException {
		List<Vehicle> loadedVehicles = loadVehiclesFromFile();
		saveVehicles2File(loadedVehicles);
	}

	public List<Vehicle> loadVehiclesFromFile() throws IOException {
		String[] record;
		List<Vehicle> vehicles = new ArrayList<>();

		while ((record = csvReader.readNext()) != null) {
			try {
				Vehicle vehicle = new Vehicle(
						LocalDateTime.parse(record[0], formatter),
						Double.parseDouble(record[1]),
						Double.parseDouble(record[2])
				);
				vehicles.add(vehicle);
			} catch (Exception e) {
				log.error("cannot parse string {} {} {}", record[0], record[1], record[2]);
			}
		}
		log.info("vehicles parsed = {}", vehicles.size());
		return vehicles;
	}

	public List<Vehicle> saveVehicles2File(List<Vehicle> vehicles) throws IOException {
		csvWriter.writeNext(headerRecord);

		for (Vehicle vehicle : vehicles) {
			Optional<ZoneId> zoneId = locationService.getTimeZone(vehicle.getLatitude(), vehicle.getLongitude());
			zoneId.ifPresent(z -> {
				vehicle.setTimeZone(z.toString());
				ZonedDateTime zonedDateTime = vehicle.getUtcDateTime().atZone(ZoneId.of("UTC"));
				vehicle.setLocalisedDateTime(
						zonedDateTime.withZoneSameInstant(z).toLocalDateTime()
				);
			});

			String localizedDateTimeString = null;
			if (vehicle.getLocalisedDateTime() != null) {
				localizedDateTimeString = String.valueOf(vehicle.getLocalisedDateTime());
			}
			csvWriter.writeNext(
					new String[]{
							formatter.format(vehicle.getUtcDateTime()),
							String.valueOf(vehicle.getLatitude()),
							String.valueOf(vehicle.getLongitude()),
							vehicle.getTimeZone(),
							localizedDateTimeString
					});
		}
		writer.flush();
		log.info("Vehicles saved = {}", vehicles.size());
		return vehicles;
	}
}
