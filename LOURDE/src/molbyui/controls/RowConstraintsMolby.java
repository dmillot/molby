package molbyui.controls;

import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class RowConstraintsMolby extends RowConstraints {

    public RowConstraintsMolby(){
        this.setVgrow(Priority.SOMETIMES);
    }



    public RowConstraintsMolby(Double prefHeight){
        this.setVgrow(Priority.SOMETIMES);
        this.setPrefHeight(prefHeight);
        this.setMaxHeight(ConstraintsBase.CONSTRAIN_TO_PREF);
        this.setMinHeight(ConstraintsBase.CONSTRAIN_TO_PREF);
    }

    public RowConstraintsMolby(Double prefHeight, Double minHeight){
        this.setVgrow(Priority.SOMETIMES);
        this.setPrefHeight(prefHeight);
        this.setMaxHeight(ConstraintsBase.CONSTRAIN_TO_PREF);
        this.setMinHeight(minHeight);
    }
}
