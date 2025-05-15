package com.github.elijahgabrielletanabe;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SortButtonEvent implements EventHandler<ActionEvent>
{
    private boolean selected;
    private final ArrayList<AlgorithmBase> queueList;
    private final AlgorithmBase ab;

    public SortButtonEvent(ArrayList<AlgorithmBase> queueList, AlgorithmBase ab)
    {
        this.selected = false;
        this.queueList = queueList;
        this.ab = ab;
    }

    @Override
    public void handle(ActionEvent event)
    {
        if (!(event.getSource() instanceof Button)) { throw new IllegalArgumentException("Not Button"); }
        
        Button button = (Button) event.getSource();

        this.selected = !this.selected;

        if (this.selected)
        {
            queueList.add(ab);
            //# Inject CSS
            button.setStyle("-fx-background-color:rgb(215, 215, 215);");
        }
        else
        {
            queueList.remove(ab);
            //# Inject CSS
            button.setStyle("");
        }
    }

    public boolean isSelected() { return this.selected; }

    public void setSelected(boolean selected) { this.selected = selected; } 
}
