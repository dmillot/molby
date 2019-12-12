package molbyui.controls;

import javafx.beans.DefaultProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

@DefaultProperty("children")
public class PaneMolby extends Pane {

    public PaneMolby(){

    }

    public PaneMolby(String classCSS){
        this.getStyleClass().add(classCSS);
    }

    public Node getChild(Integer index) {
        return this.getChildren().get(index);
    }

    public void addChild(Node child){
        this.getChildren().add(child);
    }

    public void addChilds(List childs){
        this.getChildren().addAll(childs);
    }
}