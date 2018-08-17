package com.mobiquityinc.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PacketTest {
	Packet packet;

	@Before
	public void setup() {
		packet = new Packet(81, 6);
	}

	@Test
	public void knapsack_GivenWeightEqualsZero_Zero() {
		assertEquals(0, packet.knapSack(0, null, null, 6));
	}

	@Test
	public void knapsack_ItemNumberIsZero_zero() {
		assertEquals(0, packet.knapSack(81, null, null, 0));
	}

	@Test
	public void knapsack_WeightItemIsZero_zero() {
		assertEquals(0, packet.knapSack(0, null, null, 0));
	}

	@Test
	public void knapsack_SingleItem() {
		assertEquals(34,
				packet.knapSack(800, new int[] { 630 }, new int[] { 34 }, 1));
	}

	@Test
	public void knapsack_MoreItems() {
		assertEquals(40, packet.knapSack(5600, new int[] { 9072, 3380, 4315,
				3797 }, new int[] { 13, 40, 10, 16 }, 4));
	}

	@Test
	public void knapsack_ValueIsZero_zero() {
		assertEquals(0, packet.knapSack(5600, new int[] { 9072, 3380, 4315,
				3797 }, new int[] { 0, 0, 0, 0 }, 4));
	}

	@Test
	public void knapsack_WeightIsZero_zero() {
		assertEquals(
				79,
				packet.knapSack(5600, new int[] { 0, 0, 0, 0 }, new int[] { 13,
						40, 10, 16 }, 4));
	}
}
