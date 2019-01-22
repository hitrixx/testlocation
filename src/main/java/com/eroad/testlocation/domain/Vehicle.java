package com.eroad.testlocation.domain;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of={"utcDateTime","latitude","longitude"})
public class Vehicle {
	private final LocalDateTime utcDateTime;
	private final double latitude;
	private final double longitude;

	@Setter
	private LocalDateTime localisedDateTime;
	@Setter
	private String timeZone;
}
