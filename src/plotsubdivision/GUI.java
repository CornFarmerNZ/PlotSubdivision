/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plotsubdivision;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/*
  All features completed, except:
1. Subdivision cost calculation for Optimal Strategy.
2. Optimal Strategy Tetris algorithm is too basic to draw large plots.

Therefore, Optimal Strategy doesn't take into account the Subdivision cost,
and the GUI isn't correctly done for big plots, for that mode.

---------------------------------------------------------------------------------
TEST PREFACE:
---------------------------------------------------------------------------------
Brute force:
Highest recommended is 9x5 or so, before it gets too slow.
Has no other known issues.
-----------------
Greedy choice:
50x50 is able to be represented well. Anything higher needs Plot labels toggled off.
Has no known issues. Insanely fast algorithm speed. Test 250x200 for a pretty mozaic.
-----------------
Optimal Strategy:
Most low-middle sized plots work well, e.g.,7x7 and 12x12, but some issues with fitting
plot perfectly with 'Tetris', especially larger ones. O(n^2) speed,
so in the middle between Greedy and Brute-force methods.
Suitable for up to around 100x100 (may have to wait a few seconds).



 * @author tim
 */
public class GUI {

    static int currentValue = 0;
    static boolean plotLabels = true;
    static boolean coloursInitialised = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Plot Subdivision GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JPanel panelTop = new JPanel();
        JLabel title = new JLabel("Plot Subdivision GUI");
        title.setFont(new Font("Serif", Font.BOLD, 40));

        panelTop.add(title);

        JPanel panelOptionsHeader = new JPanel();
        JPanel panelOptionsSub = new JPanel();
        JLabel labelHeader = new JLabel("Select the dimension of the plot you wish to subdivide");
        labelHeader.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JLabel labelSub = new JLabel(" (9x5 highest recommended for brute-force)");
        labelSub.setFont(new Font("Sans Serif", Font.ITALIC, 14));
        panelOptionsHeader.add(labelHeader);
        panelOptionsSub.add(labelSub);

        JPanel panelOptions2 = new JPanel();
        JTextField inputX = new JTextField("6");
        inputX.setPreferredSize(new Dimension(50, 20));
        JTextField inputY = new JTextField("3");
        inputY.setPreferredSize(new Dimension(50, 20));
        JLabel inputXLabel = new JLabel("X units (m): ");
        JLabel inputYLabel = new JLabel("Y units (m): ");

        panelOptions2.add(inputXLabel);
        panelOptions2.add(inputX);
        panelOptions2.add(inputYLabel);
        panelOptions2.add(inputY);

        JPanel panelOptions1 = new JPanel();
        JTextField inputSubdivisionCost = new JTextField("50");
        inputSubdivisionCost.setPreferredSize(new Dimension(50, 20));
        JLabel inputSubdivisionCostLabel = new JLabel("cost per unit m: ");
        Choice mode = new Choice();
        mode.addItem("Optimal strategy");
        mode.addItem("Greedy choice");
        mode.addItem("Brute force");

        panelOptions1.add(inputSubdivisionCostLabel);
        panelOptions1.add(inputSubdivisionCost);
        panelOptions1.add(mode);
        JButton buttonReColour = new JButton("Re-colour");

        JLabel msLabel = new JLabel();

        JPanel panelResults = new JPanel();
        JPanel panelResults2 = new JPanel();
        JLabel labelResults = new JLabel("Sum of resulting plots is: $" + currentValue);
        labelResults.setFont(new Font("Sans Serif", Font.ITALIC, 16));

        DPanel panelDrawing = new DPanel();

        JList textResults = new JList();

        JScrollPane scrollResults = new JScrollPane(textResults);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollResults.setPreferredSize(new Dimension(150, 400));

        panelDrawing.setPreferredSize(new Dimension(900, 600));
        panelDrawing.setMaximumSize(new Dimension(900, 600));
        JButton buttonBegin = new JButton("Begin");
        buttonBegin.addActionListener(e -> {
            System.out.println("Calculating...");
            panelDrawing.setXY(Integer.parseInt(inputX.getText()), Integer.parseInt(inputY.getText()));
            textResults.setListData(new Object[0]);

            SubdivisionV5 program = new SubdivisionV5(Integer.parseInt(inputX.getText()), Integer.parseInt(inputY.getText()), Integer.parseInt(inputSubdivisionCost.getText()));
            long timeBefore = System.currentTimeMillis();
            List<Plot> subdividedPlots = program.subdivide(mode.getSelectedItem());
            msLabel.setText("Time taken: " + (System.currentTimeMillis() - timeBefore) + "ms");

            textResults.setListData(subdividedPlots.toArray());

            System.out.println("Calculated");
            System.out.println("Drawing...");
            panelDrawing.setPlotToPaint(subdividedPlots, mode.getSelectedItem());
            if (!coloursInitialised) {
                panelDrawing.setColours();
            }
            panelDrawing.painted = false;
            panelDrawing.repaint();
            System.out.println("Drawn");
            currentValue = 0;

            for (Plot p : subdividedPlots) {
                System.out.println(p);
                currentValue += p.getValue();
            }
            labelResults.setText("Sum of resulting plots is: $" + currentValue);

        }
        );
        JButton buttonToggleText = new JButton("Toggle plot labels");
        buttonToggleText.addActionListener(e -> {
            //toggles the drawing of labels on plot.
            if (plotLabels) {
                plotLabels = false;
            } else {
                plotLabels = true;
            }
            panelDrawing.painted = false;
            panelDrawing.repaint();
        });

        panelOptions1.add(buttonBegin);
        panelOptions1.add(buttonReColour);
        panelOptions1.add(buttonToggleText);

        buttonReColour.addActionListener(e
                -> {
            panelDrawing.painted = false;
            panelDrawing.setColours();
        }
        );

        JPanel panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(1200, 500));
        panelMain.add(panelDrawing, BorderLayout.WEST);
        panelMain.add(panelResults2, BorderLayout.EAST);

        panelResults.add(labelResults);

        panelResults2.add(scrollResults);

        panelResults.add(msLabel, BorderLayout.EAST);

        pane.add(panelTop);

        pane.add(panelResults);

        pane.add(panelMain);

        pane.add(panelOptionsHeader);

        pane.add(panelOptionsSub);

        pane.add(panelOptions2);

        pane.add(panelOptions1);

        frame.pack();

        frame.setLocationRelativeTo(
                null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(
                true);
    }

    protected static class DPanel extends JPanel {

        double drawAmountX;
        double drawAmountY;
        int x;
        int y;
        List<Plot> plots = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        Color edges = new Color(215, 232, 128);
        boolean painted = false;
        String mode = "";

        /*  @Override
            public void paintComponent(Graphics g) {
                int x = Integer.parseInt(inputX.getText());
                int y = Integer.parseInt(inputY.getText());
                paintComponentXY(g, x, y);
            }*/
        public DPanel() {
            super();
        }

        public void setXY(int x, int y) {
            drawAmountX = (this.getWidth() - 8) / x;
            drawAmountY = (this.getHeight() - 200) / y;
            this.x = x;
            this.y = y;
        }

        public void setColours() {
            colorList = new ArrayList<>();
            drawAmountX = (this.getWidth() - 8) / x;
            drawAmountY = (this.getHeight() - 200) / y;
            colorList.add(new Color(255, 255, 255));
            for (Plot p : plots) {
                Random rand = new Random();
                int R = rand.nextInt(255);
                int G = rand.nextInt(255);
                int B = rand.nextInt(255);
                colorList.add(new Color(R, G, B));
            }
            this.painted = false;
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics g) {

            if (!this.plots.isEmpty() && !painted) {
                paintPlots(g, this.plots);
            }
        }

        public void setPlotToPaint(List<Plot> plots, String mode) {
            this.plots = plots;
            this.mode = mode;
        }

        private void paintPlots(Graphics g, List<Plot> plots) {
            this.plots = plots;
            int[][] plotArray = new int[this.x][this.y];

            if (mode.equals("Optimal strategy")) {
                plotArray = new int[this.x][this.y];
                for (Plot p : plots) {
                    int plotX = p.getX();
                    int plotY = p.getY();
                    int plotsTaken = 0;
                    for (int i = 0; i < this.x; ++i) {
                        for (int j = this.y - 1; j >= 0; --j) {
                            if (plotArray[i][j] == 0 && plotX > 0 && plotY > 0) {
                                plotArray[i][j] = p.id;
                                plotY--;
                                plotsTaken++;
                            }
                        }
                        if (plotsTaken >= p.getX() * p.getY()) {
                            plotX = 0;
                            i = this.x;
                        } else {
                            plotY = p.getY();
                        }
                    }
                }
                //reverse order of plots
                if (plotArray[0][0] == 0) {
                    plotArray = new int[this.x][this.y];
                    for (int p = plots.size() - 1; p >= 0; --p) {
                        int plotX = plots.get(p).getX();
                        int plotY = plots.get(p).getY();
                        int plotsTaken = 0;
                        for (int i = 0; i < this.x; ++i) {
                            for (int j = this.y - 1; j >= 0; --j) {
                                if (plotArray[i][j] == 0 && plotX > 0 && plotY > 0) {
                                    plotArray[i][j] = plots.get(p).id;
                                    plotY--;
                                    plotsTaken++;
                                }
                            }
                            if (plotsTaken >= plots.get(p).getX() * plots.get(p).getY()) {
                                plotX = 0;
                                i = this.x;
                            } else {
                                plotY = plots.get(p).getY();
                            }
                        }
                    }
                }

            } else {
                for (Plot p : plots) {
                    int plotX = p.getX();
                    int plotY = p.getY();
                    int plotsTaken = 0;
                    for (int i = 0; i < this.y; ++i) {
                        for (int j = 0; j < this.x; ++j) {
                            if (plotArray[j][i] == 0 && plotX > 0 && plotY > 0) {
                                plotArray[j][i] = p.id;
                                plotX--;
                                plotsTaken++;
                            }
                        }
                        if (plotsTaken >= p.getX() * p.getY()) {
                            plotY = 0;
                            i = this.y;
                        } else {
                            plotX = p.getX();
                        }
                    }
                }

                if (plotArray[this.x - 1][this.y - 1] == 0) {
                    plotArray = new int[this.x][this.y];
                    //if left to right, top to bottom arrangement with normal order of plots doesnt work,
                    //try left to right, top to bottom, but with reverse order of plots:
                    for (int p = plots.size() - 1; p >= 0; --p) {
                        int plotX = plots.get(p).getX();
                        int plotY = plots.get(p).getY();
                        int plotsTaken = 0;
                        for (int i = 0; i < this.y; ++i) {
                            for (int j = 0; j < this.x; ++j) {
                                if (plotArray[j][i] == 0 && plotX > 0 && plotY > 0) {
                                    plotArray[j][i] = plots.get(p).id;
                                    plotX--;
                                    plotsTaken++;
                                }
                            }
                            if (plotsTaken >= plots.get(p).getX() * plots.get(p).getY()) {
                                plotY = 0;
                                i = this.y;
                            } else {
                                plotX = plots.get(p).getX();
                            }

                        }

                    }

                }
            }

            for (int l = 0;
                    l
                    < this.y;
                    ++l) {
                for (int k = 0; k < this.x; ++k) {
                    g.setColor(colorList.get(plotArray[k][l]));
                    //System.out.println(g.getColor().toString());
                    //g.fillRect(k * (int) drawAmountX, l * (int) drawAmountY, (int) drawAmountX, (int) drawAmountY);

                    g.fill3DRect(k * (int) drawAmountX, l * (int) drawAmountY, (int) drawAmountX, (int) drawAmountY, true);
                    g.setColor(new Color(0, 0, 0));
                    g.drawRect(k * (int) drawAmountX, l * (int) drawAmountY, (int) drawAmountX, (int) drawAmountY);
                    if (plotLabels) {
                        g.setColor(new Color(255, 255, 255));
                        g.setFont(new Font("Sans Serif", Font.BOLD, 8));
                        g.drawString(plotArray[k][l] + "", (int) (drawAmountX / 2) + k * (int) drawAmountX, (int) (drawAmountY / 2) + l * (int) drawAmountY);
                    }
                }
            }
            painted = true;
        }

    }

}
