import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel {
    // Maximum number of rows and columns on panel
    final int maxCol = 40;
    final int maxRow = 35;

    // Size of the node on panel
    final int nodeSize = 70;

    // Screen width and height according to number of rows and columns
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    // 2-D Node Array
    Node[][] node = new Node[maxCol][maxRow];

    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;

    /*
     Using stepsTaken variable to have limited steps because if
     start node is totally surrounded by obstacles then search
     methods will keep on running infinitely
     */
    int stepsTaken = 0;

    Node startNode; // holds the start node of selected path
    Node goalNode; // holds the end node of selected path
    Node currentNode; // holds current node where we're standing



    // Constructor
    public Panel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));

        this.setFocusable(true);


        // Placing Nodes on panel
        int row = 0, col = 0;

        while((col < maxCol) && (row < maxRow)){
            node[col][row] = new Node(col, row);

            // Adding instantiated node on Panel
            this.add(node[col][row]);
            col++;
            /*
             if columns have reached max value then
             again initialize colum with zero and
             start incrementing rows
             */
            if(col == maxCol){
                col = 0;
                row++;
            }
            
        }

        // setting start and goal node
//        randomStart();
        setStartNode(3, 4);
        setGoalNode(39, 34);

        maze();

//        setSolidNode(goalNode.col, goalNode.row-1);
//        setSolidNode(goalNode.col-1, goalNode.row);
//        setSolidNode(goalNode.col, goalNode.row+1);
//        setSolidNode(goalNode.col+1, goalNode.row);


        setCostOnNode();

    }

    public void randomStart(){
        Random r = new Random();
        int col = r.nextInt(0, maxCol);
        int row = r.nextInt(0, maxRow);

        int col1 = r.nextInt(0, maxCol);
        int row1 = r.nextInt(0, maxRow);

        setGoalNode(col1, row1);
        setStartNode(col, row);
    }

    public void maze(){
        Random rand = new Random();

        for (int i = 0; i < 500; i++) {
            int row = rand.nextInt(maxRow);
            int col = rand.nextInt(maxCol);
            setSolidNode(col, row);
        }
    }

    /*
     Selecting a specific node as Start Node by
     passing rows and columns as arguments
     */
    private void setStartNode(int col, int row){
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }

    /*
     Selecting a specific node as Goal Node by
     passing rows and columns as arguments
     */
    private void setGoalNode(int col, int row){
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    /*
     Putting obstacles by passing rows and columns as arguments
     */
    private void setSolidNode(int col, int row){
//        if(col != startNode.col && row != startNode.row &&
//                col != goalNode.col && row != goalNode.row){
//            node[col][row].setAsSolid();
//        }
        if(!(node[col][row].equals(startNode))){
            if(!node[col][row].equals(goalNode)){
                node[col][row].setAsSolid();
            }
        }
    }

    public void setCostOnNode(){
        int col = 0, row = 0;
        while(col < maxCol && row < maxRow){
            getCost(node[col][row]);
            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node n){
        // Manhattan Distance Method is used to calculate heuristic

        // Calculating gCost : Distance from Start Node to Current Node
        int x = Math.abs(n.col - startNode.col);
        int y = Math.abs(n.row - startNode.row);
        n.gCost = x + y;

        // Calculating hCost : Distance from Current Node to Goal Node
        x = Math.abs(n.col - goalNode.col);
        y = Math.abs(n.row - goalNode.row);
        n.hCost = x + y;

        // Calculating fCost : gCost + hCost
        n.fCost = n.gCost + n.hCost;

        if(n != startNode &&  n != goalNode){
            // Using HTML tag to handle line breaks
//            n.setText("<html>F:" + n.fCost + " </html>");
        }
    }

    public void manualSearch(){
        if(goalReached == false && stepsTaken < 1400){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();

            checkedList.add(currentNode);
            openList.remove(currentNode);

            // Add checkers(if statements) to avoid error
            if(row - 1 >= 0){
                // Open the Up Node
                openNode(node[col][row-1]);
            }

            if(col-1 >= 0){
                // Open the Left Node
                openNode(node[col-1][row]);
            }

            if(row + 1 < maxRow){
                // Open the Down Node
                openNode(node[col][row+ 1]);
            }

            if(col + 1 < maxCol){
                // Open the Right Node
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;    // Assigning random max FCost value
            for (int i = 0; i < openList.size(); i++) {
                // Check if ith Node has better fCost value
                if(openList.get(i).fCost < bestNodeFCost ){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }

                // if fCost is equal then check GCost
                else if(openList.get(i).fCost == bestNodeFCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }

                // Setting the current node that has best FCost/GCost value
                currentNode = openList.get(bestNodeIndex);

                if(currentNode == goalNode){
                    goalReached = true;
                    trackThePath();
                }
            }
            stepsTaken++;
        }
    }

    public void autoSearch(){
        while(goalReached == false && stepsTaken < 1400){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();

            checkedList.add(currentNode);
            openList.remove(currentNode);

            // Add checkers(if statements) to avoid error
            if(row - 1 >= 0){
                // Open the Up Node
                openNode(node[col][row-1]);
            }

            if(col-1 >= 0){
                // Open the Left Node
                openNode(node[col-1][row]);
            }

            if(row + 1 < maxRow){
                // Open the Down Node
                openNode(node[col][row+ 1]);
            }

            if(col + 1 < maxCol){
                // Open the Right Node
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;    // Assigning random max FCost value
            for (int i = 0; i < openList.size(); i++) {
                // Check if ith Node has better fCost value
                if(openList.get(i).fCost < bestNodeFCost ){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }

                // if fCost is equal then check GCost
                else if(openList.get(i).fCost == bestNodeFCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }

                // Setting the current node that has best FCost/GCost value
                currentNode = openList.get(bestNodeIndex);

                if(currentNode == goalNode){
                    goalReached = true;
                    trackThePath();
                }

            }
            stepsTaken++;
        }
    }

    private void openNode(Node n){
        if(n.open == false && n.checked == false && n.solid == false){
            n.setAsOpen();

            n.parent = currentNode;

            openList.add(n);
        }
    }

    /*
    After finding the best path to the goal we'll back track the path
    to the start node and highlight that path
     */
    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            current = current.parent;

            if(current != startNode){
                current.setAsPath();
            }
        }
    }

}
