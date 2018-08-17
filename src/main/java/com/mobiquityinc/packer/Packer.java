package com.mobiquityinc.packer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Packet;

/**
 * In this class we would be reading the file and to determine which things to
 * put into the package so that the total weight is less than or equal to the
 * package limit and the total cost is as large as possible. With some
 * additional constraints as follows 1. Max weight that a package can take is ≤
 * 100 2. There might be up to 15 items you need to choose from 3. Max weight
 * and cost of an item is ≤ 100
 * 
 * @author sindhu
 *
 */
	public class Packer {
	private static final Pattern itemPattern = Pattern
			.compile("^\\((\\d+?),(.*?),.(\\d+?)\\)$");

	/**
	 * In this method we would read the file and get the optimal items that can
	 * be sealed within a packet.
	 * 
	 * @param input
	 * @throws APIException
	 */
	private static void packer(String input) throws APIException {
		try (BufferedReader inputReader = Files.newBufferedReader(Paths
				.get(input))) {
			List<Packet> collection = inputReader.lines()
					.map(Packer::parseLineWrapper).map(Packet::packed)
					.collect(Collectors.toList());
			collection.forEach(System.out::println);
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	private static Packet parseLineWrapper(String line) {
		
		try {
			return parseLine(line);
		} catch (APIException e) {
			return new Packet(0, 0,e.getMessage());
		}
		
	}
	

	/**
	 * Convert the data received per line into individual packet and it details
	 * and possible items that can be part of the packet with constraints
	 * 
	 * @param line
	 * @return
	 * @throws APIException 
	 */
	private static Packet parseLine(String line) throws APIException {
		String[] tokens = line.split(":");
		String[] tuples = tokens[1].trim().split("\\s+(.*?)");
	
		int totalWeight = Integer.valueOf(tokens[0].trim());
		int itemLength = tuples.length;
			validate(totalWeight, itemLength);
		
		Packet pack = new Packet(totalWeight, itemLength);
		for (String tuple : tuples) {
			Matcher itemMatcher = itemPattern.matcher(tuple.trim());
		
			if (itemMatcher.find()) {
				int item = Integer.valueOf(itemMatcher.group(1));
				float weight = Float.valueOf(itemMatcher.group(2));
				int cost = Integer.valueOf(itemMatcher.group(3));
					validateItem(weight, cost);
				pack.addItem(item, weight, cost);
			}else{
				try {
					throw new APIException("Provided input parameters in the files are incorrect");
				} catch (APIException e) {
				}
			}
		}
		return pack;
	}

	/**
	 * Validating if weight and cost are above 100
	 * @param weight
	 * @param cost
	 * @return
	 * @throws APIException 
	 */
	public static boolean validateItem(double weight, int cost)
			throws APIException {
		if (weight > 100.00 || cost > 100) {
			throw new APIException(
					"Max weight and cost of an item cannot be more than 100");
		}
		return true;
	}

	/**
	 * Validating if total weight is above 100 and number of items is above 15
	 * @param totalWeight
	 * @param itemLength
	 * @return
	 * @throws APIException 
	 */
	public static boolean validate(int totalWeight, int itemLength)
			throws APIException {
		if (totalWeight > 100) {
			throw new APIException("Weight cannot be more than 100");
		} else if (itemLength > 15) {
			throw new APIException("Item size cannot be more than 15");
		}
		return true;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Provide file name");
		Scanner sc = new Scanner(System.in);
		String filePath = sc.nextLine();
		String fileName = "";
		while (filePath.isEmpty()) {
			System.out.println("File name cannot be empty");
			System.out.println("Please provide the file name");	
		}
		try {
			Path filePaths= Paths.get(filePath);
			fileName = filePaths.getFileName().toString();
			Packer.packer(fileName);
		} catch (APIException e) {
			System.out
					.println("File is not present in the project. Please provide the file name present in the project");
		}
	}
}
