package com.deanwhitten.inventorymanagementsystem.Model;
/**
* Supplied class Part.java 
 */

/**
 * The type Part.
 *
 * @author Dean Whitten
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Part.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Instantiates a new Part.
     */
    protected Part() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets min.
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


}