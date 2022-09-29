/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plotsubdivision;

/**
 *
 * @author tim
 */
public class Plot {

    public int id;
    private int x;
    private int y;
    private int subdivisions;
    private int value; //$$
    private int SUBDIVISION_COST;
    private final int[][] pricing
            = {{20, 40, 100, 130, 150, 200},
            {40, 140, 250, 320, 400, 450},
            {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700},
            {150, 400, 450, 600, 700, 800},
            {200, 450, 500, 700, 800, 900}};

    public Plot(int x, int y) {
        this.x = x;
        this.y = y;
        this.subdivisions = 0;
        recalculateValue();
    }

    public Plot(int x, int y, int subdivisions, int subdivisionCost) {
        this.SUBDIVISION_COST = subdivisionCost;
        this.x = x;
        this.y = y;
        this.subdivisions = subdivisions;
        recalculateValue();
    }

    public Plot(Plot plot) {
        this.x = plot.getX();
        this.y = plot.getY();
        this.value = plot.getValue();
    }

    public Plot() {
    }

    public int[][] getPricing() {
        return this.pricing;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the subdivisions
     */
    public int getSubdivisions() {
        return subdivisions;
    }

    /**
     * @param subdivisions the subdivisions to set
     * @param subdivisionCost
     */
    public void setSubdivisions(int subdivisions, int subdivisionCost) {
        this.subdivisions = subdivisions;
        this.SUBDIVISION_COST = subdivisionCost;
        recalculateValue();
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void recalculateValue() {
        int sub = (this.getSubdivisions() * SUBDIVISION_COST);
        if (x == 0 || y == 0 || x > 6 || y > 6) {
            value = 0;
        } else {
            value = pricing[x - 1][y - 1] - sub;
        }
    }

    @Override
    public String toString() {
        String temp = "" + this.id + " : ";
        temp += this.getDimension();
        temp += " : $" + this.getValue();

        return temp;
    }

    /*
    @Override
    public String toString() {
        String temp = "";
        temp += "Plot ";
        temp += this.hashCode();
        temp += " ";
        temp += "(";
        temp += this.x;
        temp += ",";
        temp += this.y;
        temp += ")\n";
        for (int i = 0; i < this.y; ++i) {
            for (int j = 0; j < this.x; ++j) {
                temp += ("[" + this.value + "]");
            }
            temp += "\n";
        }

        return temp;
    }*/
    public String getDimension() {
        String temp = "";

        temp += "(";
        temp += this.x;
        temp += ",";
        temp += this.y;
        temp += ")";
        return temp;
    }

}
