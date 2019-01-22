package com.eroad.testlocation.configuration;

import com.opencsv.*;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLocationConfiguration {

	@Value("${init.vehicles.file}")
	private String initVehiclesFile;
	@Value("${withTZ.vehicles.file}")
	private String withTZVehiclesFile;

	@Bean
	public TimeZoneEngine timeZoneEngine() {
		return TimeZoneEngine.initialize();
	}
	@Bean
	public Writer writer() throws IOException {
		return Files.newBufferedWriter(Paths.get(withTZVehiclesFile));
	}
	@Bean
	public CSVWriter csvWriter(Writer writer) throws IOException {
		return new CSVWriter(writer,
				CSVWriter.DEFAULT_SEPARATOR,
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END);
	}

	@Bean
	public CSVReader csvReader() throws FileNotFoundException {
		return new CSVReaderBuilder(new FileReader(initVehiclesFile))
				.withCSVParser(
						new CSVParserBuilder().withSeparator(ICSVParser.DEFAULT_SEPARATOR).build()
				).build();
	}
}
