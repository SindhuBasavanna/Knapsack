package com.mobiquityinc.packer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;

public class PackerTest {
	@Test(expected = APIException.class)
	public void validate_givenWeight_More_Hundread() throws APIException {
		Packer.validate(101, 0);
	}

	@Test
	public void validate_givenWeight_Less_Hundread() throws APIException {
		assertTrue(Packer.validate(99, 0));
	}

	@Test(expected = APIException.class)
	public void validate_Item_More_Fifteen() throws APIException {
		Packer.validate(99, 16);
	}

	@Test
	public void validate_givenItem_Less_Fifteen() throws APIException {
		assertTrue(Packer.validate(99, 5));
	}

	@Test(expected = APIException.class)
	public void validate_ItemWeight_More_Hundread() throws APIException {
		Packer.validateItem(100.99, 35);
	}

	@Test
	public void validate_ItemWeight_Less_Hundread() throws APIException {
		Packer.validateItem(19, 35);
	}

	@Test(expected = APIException.class)
	public void validate_Cost_More_Hundread() throws APIException {
		Packer.validateItem(80, 110);
	}

	@Test
	public void validate_ItemCost_Less_Hundread() throws APIException {
		Packer.validateItem(20, 40);
	}

}
