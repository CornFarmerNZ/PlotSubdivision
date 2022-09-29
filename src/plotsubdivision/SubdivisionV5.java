/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plotsubdivision;

import java.util.ArrayList;
import java.util.List;

// The main class for testing is GUI.java
// Program process:
// GUI.java -> instanceOfThis.subdivide() -> bruteForce() or greedyAlgorithm() or optimalStrategy() -> GUI.java
//
/*
 *
 * @author tim
 */
public class SubdivisionV5 { //V5, for the amount of blood sweat and tears while sorting this out haha

    Plot starterPlot;
    int subdivisionCost;

    public SubdivisionV5(int x, int y, int subdivisionCost) {
        starterPlot = new Plot(x, y);
        this.subdivisionCost = subdivisionCost;

    }

    public static void main(String[] args) {
        SubdivisionV5 V5 = new SubdivisionV5(9, 9, 50);
        /*List<Plot> listBruteForce = V5.bruteForce(V5.starterPlot);

        System.out.println("Optimal plots: ");
        for (Plot p : listBruteForce) {
            System.out.println(p);
        }
        System.out.println("Total sum (brute-force): " + V5.getValueSum(listBruteForce));

        List<Plot> listGreedyAlgorithm = V5.subdivide("Greedy algorithm");
        for (Plot p : listGreedyAlgorithm) {
            System.out.println(p);
        }
        System.out.println("Total sum (greedy algorithm): ");*/

        List<Plot> listOptimalStrategy = V5.optimalStrategy();
        if (listOptimalStrategy != null) {
            for (Plot p : listOptimalStrategy) {
                System.out.println(p);

            }
        }
    }

    /**
     * Essentially a 'subdivision-method' factory. Chooses a method based on
     * method parameters, which are passed into via the GUI.
     *
     *
     * @param method algorithm to subdivide with. "Brute force", "Greedy
     * choice", "Optimal strategy".
     * @return The resulting list of subdivided plots.
     */
    public List<Plot> subdivide(String method) {
        List<Plot> temp;
        if (method.equals("Brute force")) {
            temp = this.bruteForce(starterPlot);
        } else if (method.equals("Greedy choice")) {
            temp = new ArrayList<>();
            int[][] plotArray = new int[starterPlot.getX()][starterPlot.getY()];
            temp = this.greedyAlgorithm(plotArray, temp);
        } else if (method.equals("Optimal strategy")) {
            temp = this.optimalStrategy();
            /* Comparator<Plot> plotComparator = (p1, p2) -> (p1.getY() - p1.getX() - p2.getY() - p2.getX());
            Collections.sort(temp, plotComparator);*/
        } else {
            temp = null;
        }
        List<Plot> toRemove = new ArrayList<>();
        int idCounter = 1;
        for (Plot p : temp) {
            if (p.getX() == 0 || p.getY() == 0) {
                toRemove.add(p);
            } else {
                p.id = idCounter++;
            }
        }
        temp.removeAll(toRemove);
        return temp;

    }

    /**
     * Brute force, baby! max recommended is 9x5 size, since this is O(2^n)
     * algorithm. Slooooow.
     *
     * @param plot Plot to subdivide into sub-plots.
     * @return a list of sub-plots.
     */
    public List<Plot> bruteForce(Plot plot) {
        List<Plot> optimalX = new ArrayList<>();
        List<Plot> optimalY = new ArrayList<>();

        //subdividing starter plot
        for (int i = 1; i < plot.getX(); ++i) {
            Plot plot1 = new Plot(plot.getX() - i, plot.getY(), plot.getSubdivisions(), subdivisionCost);
            Plot plot2 = new Plot(i, plot.getY(), plot.getY() + plot.getSubdivisions(), subdivisionCost);

            List<Plot> list1 = bruteForce(plot1);
            List<Plot> list2 = bruteForce(plot2);
            boolean bigger1 = false;
            boolean bigger2 = false;
            if (getValueSum(list1) > plot1.getValue() && !list1.isEmpty()) {
                bigger1 = true;
            }
            if (getValueSum(list2) > plot2.getValue() && !list2.isEmpty()) {
                bigger2 = true;
            }

            if (bigger1 == true && bigger2 == true) {
                if (getValueSum(list1) + getValueSum(list2) > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalX)) {
                        optimalX.clear();
                        optimalX.addAll(list1);
                        optimalX.addAll(list2);
                    }
                }
            } else if (bigger1 == true && bigger2 == false) {
                if (getValueSum(list1) + plot2.getValue() > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalX)) {
                        optimalX.clear();
                        optimalX.addAll(list1);
                        optimalX.add(plot2);
                    }
                }
            } else if (bigger1 == false && bigger2 == true) {
                if (getValueSum(list2) + plot1.getValue() > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalX)) {
                        optimalX.clear();
                        optimalX.addAll(list2);
                        optimalX.add(plot1);
                    }
                }
            } else if (plot1.getValue() + plot2.getValue() > plot.getValue()) {
                if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalX)) {
                    optimalX.clear();
                    optimalX.add(plot1);
                    optimalX.add(plot2);
                }
            }

        }
        for (int j = 1; j < plot.getY(); ++j) {
            Plot plot1 = new Plot(plot.getX(), plot.getY() - j, plot.getSubdivisions(), subdivisionCost);
            Plot plot2 = new Plot(plot.getX(), j, plot.getX() + plot.getSubdivisions(), subdivisionCost);
            if (plot1.getX() == 5 && plot1.getY() == 5 && plot.getX() == 5 && plot.getY() == 9) {
                System.out.println("");
            }

            List<Plot> list1 = bruteForce(plot1); //5,3
            List<Plot> list2 = bruteForce(plot2); //1,3

            boolean bigger1 = false;
            boolean bigger2 = false;
            if (getValueSum(list1) > plot1.getValue() && !list1.isEmpty()) {
                bigger1 = true;
            }
            if (getValueSum(list2) > plot2.getValue() && !list2.isEmpty()) {
                bigger2 = true;
            }

            if (bigger1 == true && bigger2 == true) {
                if (getValueSum(list1) + getValueSum(list2) > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalY)) {
                        optimalY.clear();
                        optimalY.addAll(list1);
                        optimalY.addAll(list2);
                    }
                } else if (bigger1 == true && bigger2 == false) {
                    if (getValueSum(list1) + plot2.getValue() > plot.getValue()) {
                        if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalY)) {
                        }
                        optimalY.clear();
                        optimalY.addAll(list1);
                        optimalY.add(plot2);
                    }
                }
            } else if (bigger1 == false && bigger2 == true) {
                if (getValueSum(list2) + plot1.getValue() > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalY)) {
                        optimalY.clear();
                        optimalY.addAll(list2);
                        optimalY.add(plot1);
                    }
                }
            } else {
                if (plot1.getValue() + plot2.getValue() > plot.getValue()) {
                    if (getValueSum(list1) + getValueSum(list2) > getValueSum(optimalY)) {
                        optimalY.clear();
                        optimalY.add(plot1);
                        optimalY.add(plot2);
                    }
                }
            }

        }

        //if optimalX listBruteForce is bigger than optimalY and optimalXY
        if (getValueSum(optimalX)
                > getValueSum(optimalY)) {
            if (getValueSum(optimalX) > plot.getValue()) {
                return optimalX;
            }
        } else {  //else, if optimalY is bigger than optimalXY
            if (getValueSum(optimalY) > plot.getValue()) {
                return optimalY;
            }
        }
        List<Plot> optimalXY = new ArrayList<>();

        optimalXY.add(plot);

        optimalXY.add(
                new Plot(0, 0));
        return optimalXY;
    }

    /**
     * tries to subdivide the largest possible plot (in this case, 6x6) at each
     * possible subdivision.
     *
     * @param plotArray represents the entire plot as integers. Passed as an
     * argument into each recursion so that changes are added in each iteration.
     * @param list holds the result - the chosen sub-plots.
     * @return The resulting list of subdivided plots.
     */
    public List<Plot> greedyAlgorithm(int[][] plotArray, List<Plot> list) {
        /*plotArray = new int[][]{{1, 1, 1, 1, 1, 1, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}};*/

        int largestX = starterPlot.getPricing().length;
        int largestY = starterPlot.getPricing()[0].length;
        int currentPlotId = IdGenerator.getIdGenerator().getID();

        int startingX = starterPlot.getX();
        int startingY = starterPlot.getY();

        //find first available empty plot, starting left to right, top to bottom.
        for (int i = 0; i < plotArray[0].length; ++i) {
            for (int j = 0; j < plotArray.length; ++j) {
                if (plotArray[j][i] == 0 && largestX > 0) {
                    startingX = j;
                    startingY = i;
                    i = plotArray[0].length;
                    j = plotArray.length;
                }
            }
        }

        int movementX = 0;
        int movementY = 0;
        int xSubdivisions = 0;
        int ySubdivisions = 0;

        //Regardless of whether it moves the entire 6x6, the movement is tracked in variables movementX,movementY.
        for (int i = startingY; i < starterPlot.getY(); ++i) {
            boolean moved = false;
            for (int j = startingX; j < starterPlot.getX(); ++j) {

                if (i - startingY < largestY && j - startingX < largestX) {
                    plotArray[j][i] = currentPlotId;
                    movementX++;
                    moved = true;
                }
            }
            if (moved) {
                movementY++;
                try {
                    if (plotArray[largestX + startingX][i] == 0) {
                        ySubdivisions++;
                    }
                    if (plotArray[largestY + startingY][i] == 0) {
                        xSubdivisions++;
                    }
                } catch (Exception e) {

                }
            }

        }

        movementX = movementX / movementY;

        Plot newPlot = new Plot(movementX, movementY);
        newPlot.setSubdivisions(ySubdivisions + xSubdivisions, subdivisionCost);
        list.add(newPlot);

        //plot print.
        for (int X = 0; X < plotArray[0].length; ++X) {
            for (int Y = 0; Y < plotArray.length; ++Y) {
                if (plotArray[Y][X] == 0) {
                    greedyAlgorithm(plotArray, list);
                }
            }
        }

        return list;
    }

    /**
     * 'dynamic programming'. I don't know if this was the 'optimal' optimal
     * method, since i had to check multiple combinations X-wise and Y-wise, at
     * each point element of the array, but it's speed isn't bad.
     *
     * @return The resulting list of subdivided plots.
     */
    public List<Plot> optimalStrategy() {

        ArrayList<Plot>[][] answers = new ArrayList[starterPlot.getX() + 1][starterPlot.getY() + 1];
        //each element in the [][] array is an ArrayList<Plot>

        //filling out the columns and rows where x and y are 0.
        for (int X = 0; X < starterPlot.getX() + 1; ++X) {
            answers[X][0] = new ArrayList<>();
            answers[X][0].add(new Plot(0, 0));
        }
        for (int Y = 0; Y < starterPlot.getY() + 1; ++Y) {
            answers[0][Y] = new ArrayList<>();
            answers[0][Y].add(new Plot(0, 0));
        }

        for (int i = 1; i < starterPlot.getY() + 1; ++i) {
            for (int j = 1; j < starterPlot.getX() + 1; ++j) {
                //if current array position exists on pricing array (6x6 size).
                boolean addBiggest = true;
                int biggestValue = new Plot(j, i).getValue();
                int plotsToCheck = j - 1;
                ArrayList<Plot> biggestList = new ArrayList<>();
                //checks previous results on the X axis: e.g., for 5,5 it checks for (1,5 + 4,5 then 2,5 + 3,5).., etc
                for (int p = 1; p < plotsToCheck; ++p) {
                    int value = getValueSum(answers[p][i]) + getValueSum(answers[j - p][i]);
                    //if this check is bigger than the largest stored value.
                    if (value > biggestValue) {
                        biggestValue = value;
                        biggestList = new ArrayList<>();
                        biggestList.addAll(answers[p][i]);
                        biggestList.addAll(answers[j - p][i]);
                    }
                }//checks previous results on the Y axis: e.g., for 5,5 it checks for (5,1 + 5,4) then (5,2 + 5,3).., etc
                int plotsToCheckY = (i / 2) + 1;
                for (int k = 1; k < plotsToCheckY; ++k) {
                    int valueY = getValueSum(answers[j][k]) + getValueSum(answers[j][i - k]);
                    if (valueY > biggestValue) {
                        //if this check is bigger than the largest stored value.
                        biggestValue = valueY;
                        biggestList = new ArrayList<>();
                        biggestList.addAll(answers[j][k]);
                        biggestList.addAll(answers[j][i - k]);
                    }
                }
                if (getValueSum(biggestList) > new Plot(j, i).getValue()) {
                    //if biggest combination is bigger than a plot created of the current X + Y.
                    //sets addBiggest flag to false (means that the biggest singular X,Y plot won't be used).
                    addBiggest = false;
                }
                if (addBiggest) {
                    //adds single plot (if it's bigger than possible combinations).
                    answers[j][i] = new ArrayList<>();
                    answers[j][i].add(new Plot(j, i));
                    // System.out.print(answers[j][i] + " - ");
                } else {
                    //adds list of combination plots, since they're the biggest combination.
                    ArrayList<Plot> tempList = new ArrayList<>();
                    for (Plot p : biggestList) {
                        //creating new objects of them, since otherwise some of them may be references to the same object.
                        //needed to correctly ID each of them.
                        tempList.add(new Plot(p.getX(), p.getY()));
                    }
                    answers[j][i] = tempList;
                }
            }
            //System.out.println("");
        }

        for (int i = 0; i < answers[0].length; ++i) {
            for (int j = 0; j < answers.length; ++j) {
                System.out.print(getValueSum(answers[j][i]) + " ");
            }
            System.out.println("");
        }

        return answers[starterPlot.getX()][starterPlot.getY()];
    }

    /**
     * Iterates through a list of Plot objects and sums up their values.
     *
     * @param plotList list to iterate through
     * @return The resulting list of subdivided plots.
     */
    public int getValueSum(List<Plot> plotList) {
        int temp = 0;
        for (Plot p : plotList) {
            temp += p.getValue();
        }
        return temp;
    }

}
