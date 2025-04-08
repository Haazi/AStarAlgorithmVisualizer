import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {
    Node parent;
    int col, row;
    int gCost; // gCost is our weight
    int hCost; // hCost is the heuristic(estimated) value
    int fCost; // fCost = gCost + hCost
    boolean start, goal, solid, open, checked;

    public Node(int col, int row){
        this.row = row;
        this.col = col;

        setBackground(Color.white);
        setForeground(Color.BLACK);
        addActionListener(this);
    }

    // Setting a Start Node
    public void setAsStart(){
        setBackground(new Color(14, 139, 235));
        setForeground(Color.BLACK);
        setText("S");
        start = true;
    }

    // Setting a Goal(Ending) Node
    public void setAsGoal(){
        setBackground(new Color(14, 139, 235));
        setForeground(Color.black);
        setText("G  ");
        goal = true;
    }

    /*
     Setting a Solid Node
     (Solid Node will act as an obstacle in path)
     */
    public void setAsSolid(){
        setBackground(new Color(26, 26, 26));
        setForeground(Color.BLACK);
        solid = true;
    }

    public void setAsOpen(){
        open = true;
    }

    public void setAsChecked(){
        if(start == false && checked == false){
            setBackground(new Color(196, 56, 37));
        }
        checked = true;
    }

    public void setAsPath(){
        setBackground(new Color(38,195,69));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Color will be changed to orange when button is clicked
        setBackground(new Color(37, 150, 190));
    }
}
