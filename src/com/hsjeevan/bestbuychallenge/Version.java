package com.hsjeevan.bestbuychallenge;

import java.time.Instant;

public class Version {
	String version;
	Instant startTime;
	Instant endTime;

	public Version(String version, Instant startTime, Instant endTime) {
		super();
		this.version = version;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Version(String version, Instant startTime) {
		super();
		this.version = version;
		this.startTime = startTime;
	}
	public Version(String version) {
		super();
		this.version = version;
	}

	@Override
	public String toString() {
		return "["+ version + "," + startTime + "," + endTime + "]";
	}
	
	
}
