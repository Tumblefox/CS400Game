/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

/**
 *
 * @author asieka01
 */
public class Item {
    
    private String name;
    private boolean isDiscardable;
    private boolean isCombined;
    private int numOfUses;
    private double weight;
    private String[] combinedItems;
    
    public Item(String name, boolean isDiscardable, boolean isCombined, int numOfUses) {
        this.name = name;
        this.isDiscardable = isDiscardable;
        this.isCombined = isCombined;
        this.numOfUses = numOfUses;
        weight = 0;
    }
    
    public String name() {
        return name;
    }
    
    public boolean isDiscardable() {
        return isDiscardable; 
    }
    
    public boolean isCombined() {
        return isCombined; 
    }
    
    public void toggleCombined() {
        if (isCombined)
            isCombined = false;
        else
            isCombined = true;
    }
    
    public int numOfUses() {
        return numOfUses;
    }
    
    public void use() {
        if (numOfUses != 0)
            numOfUses -= 1;
        else
            isDiscardable = true;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
    
}
