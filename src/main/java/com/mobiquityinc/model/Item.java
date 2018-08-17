package com.mobiquityinc.model;
/**
 * Item with properties weight and value
 * @author sindhu
 *
 */
class Item {
  private final float weight;
  private final int cost;

  public Item(float weight, int cost) {
    this.weight = weight;
    this.cost = cost;
  }

  public float getWeight() {
    return weight;
  }

  public int getCost() {
    return cost;
  }
  
  public String toString(){
	return "weight "+weight+" cost "+cost;
	  
  }
}
