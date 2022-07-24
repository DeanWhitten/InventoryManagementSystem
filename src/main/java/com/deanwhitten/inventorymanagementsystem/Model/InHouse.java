package com.deanwhitten.inventorymanagementsystem.Model;

/**
 * The type In house.
 */
public class InHouse extends Part{
    private int machineID;

    /**
     * Instantiates a new In house.
     *
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param stock     the stock
     * @param min       the min
     * @param max       the max
     * @param machineId the machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    }

    /**
     * Gets machine id.
     *
     * @return the machine id
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Sets machine id.
     *
     * @param machineID the machine id
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}