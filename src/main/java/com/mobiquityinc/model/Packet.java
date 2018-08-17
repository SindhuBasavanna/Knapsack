package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * This class contains the properties of packet and available items from which the items will be picked 
 * @author sindhu
 *
 */
public class Packet {
  private static final Integer MULTIPLICATION_FACTOR = 100;
  private final int weight;
  private final Item[] items;
  private final int[][] matrix;
  private String exceptionMessage;

  public Packet(int weight, int numberOfItems,String exceptionMessage) {
	  this(weight,numberOfItems);
	  this.exceptionMessage = exceptionMessage;
}


public Packet(int weight, int numberOfItems) {
    this.weight = weight;
    this.items = new Item[numberOfItems];
    this.matrix = new int[numberOfItems][weight * MULTIPLICATION_FACTOR + 1];
  }

/**
 * Creates item object
 * @param indexNumber
 * @param weight
 * @param cost
 */
public void addItem(int indexNumber, float weight, int cost) {
    items[indexNumber - 1] = new Item(weight, cost);
  }

  /**
   * knapsack recursive algorithm
   * @param W
   * @param wt
   * @param val
   * @param n
   * @return
   */
  public int knapSack(int W, int[] wt, int[] val, int n) {
    if (n == 0 || W == 0) {
      return 0;
    }

    if (wt[n - 1] > W) {
      return knapSack(W, wt, val, n - 1);
    } else {
      int take = val[n - 1] + knapSack(W - wt[n - 1], wt, val, n - 1);
      int donTake = knapSack(W, wt, val, n - 1);
      if (take >= donTake) {
        matrix[n - 1][W] = 1;
      } else {
        matrix[n - 1][W] = -1;
      }
      return Math.max(take, donTake);
    }
  }

  /**
   * @return
   */
  public Packet packed() {
    int[] wt = new int[items.length];
    int[] val = new int[items.length];
    for (int i = 0; i < items.length; i++) {
      wt[i] = (int) (items[i].getWeight() * MULTIPLICATION_FACTOR);
      val[i] = items[i].getCost();
    }
    for (int i = 0; i < items.length; i++) {
      for (int w = 0; w <= weight * MULTIPLICATION_FACTOR; w++) {
        matrix[i][w] = 0;
      }
    }
    knapSack(weight * MULTIPLICATION_FACTOR, wt, val, items.length);
    return this;
  }

  @Override
  public String toString() {
    List<Integer> selectedIndices = new ArrayList<>();
    int item = items.length - 1;
    int size = weight * MULTIPLICATION_FACTOR;
    Object[] weights = Arrays.stream(items).map(Item::getWeight).toArray();
    while (item >= 0) {
      if (matrix[item][size] == 1) {
        selectedIndices.add(item + 1);
        size -= ((float) (weights[item])) * MULTIPLICATION_FACTOR;
        item--;
      } else {
        item--;
      }
    }
    String result =
        selectedIndices.stream().sorted(Comparator.comparingInt(Integer::intValue))
            .mapToInt(Integer::intValue).mapToObj(String::valueOf).collect(Collectors.joining(","));
    return exceptionMessage != null ? exceptionMessage : result.trim().isEmpty() ? "-" : result;
  }

}
