package com.hsjeevan.bestbuychallenge;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

	public static List<Version> versions = new ArrayList<Version>();

	public static void main(String[] args) {

		versions.add(new Version("V1", Instant.parse("2023-02-15T00:00:00.000Z"),
				Instant.parse("2023-02-17T00:00:00.000Z")));
		versions.add(new Version("V2", Instant.parse("2023-02-17T00:00:00.000Z"),
				Instant.parse("2023-02-18T00:00:00.000Z")));
		versions.add(new Version("V3", Instant.parse("2023-02-18T00:00:00.000Z"),
				Instant.parse("2023-02-20T00:00:00.000Z")));

//	Print Initial values
		System.out.println("Initial Values");
		versions.stream().map(v -> v.toString()).forEach(System.out::println);

//		Add new Version
		Version v4 = new Version("V4", Instant.parse("2023-02-16T00:00:00.000Z"));
		addVersion(v4);

//		Remove version V2
		removeVersion(new Version("V2"));

//		Update version V4
		Version updateV4 = new Version("V4", Instant.parse("2023-02-17T00:00:00.000Z"));
		updateVersion(updateV4);

//		Print modified Values
		System.out.println("\nAfter Operations");
		versions.stream().map(v -> v.toString()).forEach(System.out::println);

	}

	public static void addVersion(Version newVersion) {
		int i = 0;
		Boolean check = false;
		for (Version v : versions) {// Looping through versions

			if (newVersion.startTime.isBefore(v.startTime)) {
//				If the newVersion begins before any event in the system
				Version nextVersion = versions.get(i);
				newVersion.endTime = nextVersion.startTime;
//				versions.add(i, newVersion);
				check = true;
				i--;
				break;
			} else if (newVersion.startTime.equals(v.startTime)) {
//				Having same startTime
				throw new Error("Not allowed");
			} else if (newVersion.startTime.isAfter(v.startTime) && newVersion.startTime.isBefore(v.endTime)) {
//				If the newVersion is in between two versions
				v.endTime = newVersion.startTime;
				Version nextVersion = versions.get(i + 1);
				newVersion.endTime = nextVersion.startTime;
				check = true;
				break;
			}
			i++;
		}
		if (check)
			versions.add(i + 1, newVersion);
	}

	public static void removeVersion(Version removableVersion) {

		Iterator<Version> iterator = versions.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Version currentVersion = iterator.next();
			if (currentVersion.version.equals(removableVersion.version)) {
				Version previousVersion = versions.get(i - 1);
				versions.set(i - 1,
						new Version(previousVersion.version, previousVersion.startTime, versions.get(i + 1).startTime));

				iterator.remove();
				return;
			}
			i++;
		}
	}

	public static void updateVersion(Version updatedVersion) {
		Iterator<Version> iterator = versions.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Version currentVersion = iterator.next();

			if (updatedVersion.startTime.equals(currentVersion.startTime)) {
//				Having same startTime
				throw new Error("Not allowed");
			}
			Version previousVersion, nextVersion;
			if (i != 0) {
//				check if the version is not the first item in the list
				previousVersion = versions.get(i - 1);
				nextVersion = versions.get(i + 1);

				if (currentVersion.version.equals(updatedVersion.version)) {
					previousVersion.endTime = updatedVersion.startTime;
					updatedVersion.endTime = nextVersion.startTime;
					versions.set(i, updatedVersion);
					return;
				}
			} else {
//				if version is the first item in the list
				nextVersion = versions.get(i + 1);
				currentVersion = updatedVersion;
				currentVersion.endTime = nextVersion.startTime;
				break;
			}

			i++;
		}
	}
}
