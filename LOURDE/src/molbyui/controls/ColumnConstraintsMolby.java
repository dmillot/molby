package molbyui.controls;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.Priority;

public class ColumnConstraintsMolby extends ColumnConstraints {

    public ColumnConstraintsMolby(){
        this.setHgrow(Priority.SOMETIMES);
    }

    public ColumnConstraintsMolby(Double prefWidth){
        this.setHgrow(Priority.SOMETIMES);
        this.setPrefWidth(prefWidth);
        this.setMaxWidth(ConstraintsBase.CONSTRAIN_TO_PREF);
        this.setMinWidth(ConstraintsBase.CONSTRAIN_TO_PREF);
    }

    public ColumnConstraintsMolby(Double prefWidth, Double minHeight){
        this.setHgrow(Priority.SOMETIMES);
        this.setPrefWidth(prefWidth);
        this.setMaxWidth(ConstraintsBase.CONSTRAIN_TO_PREF);
        this.setMinWidth(minHeight);
    }

}
