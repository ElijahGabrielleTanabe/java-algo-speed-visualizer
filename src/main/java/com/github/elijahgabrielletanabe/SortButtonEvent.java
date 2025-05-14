package com.github.elijahgabrielletanabe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SortButtonEvent implements EventHandler<ActionEvent>
{
    private boolean selected;

    public SortButtonEvent()
    {
        this.selected = false;
    }

    @Override
    public void handle(ActionEvent event)
    {
        if (!(event.getSource() instanceof Button)) { throw new IllegalArgumentException("Not Button"); }
        
        this.selected = !this.selected;
    }

    public boolean isSelected() { return this.selected; }
}
