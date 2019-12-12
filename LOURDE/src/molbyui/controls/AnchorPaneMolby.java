package molbyui.controls;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
@DefaultProperty("children")

public class AnchorPaneMolby extends AnchorPane {
    public Node getChild(Integer index) {
        return this.getChildren().get(index);
    }

    public boolean addChilds(ObservableList childrens) {
        return this.getChildren().setAll(childrens);
    }

    public Integer count(){
        return this.getChildren().size();
    }

    public boolean addChilds(Node children) {
        return this.getChildren().setAll(children);
    }
}
