package molbyui.controls;

import javafx.beans.DefaultProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
@DefaultProperty("children")

public class VBoxMolby extends VBox {

    public VBoxMolby(){

    }

    public VBoxMolby(Double prefWidth, Double prefHeight, Insets padding){
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        this.setPadding(padding);
    }

    public VBoxMolby(Double prefWidth, Double minHeight, Double prefHeight, Insets padding){
        this.setPrefHeight(prefHeight);
        this.setMinHeight(minHeight);
        this.setPrefWidth(prefWidth);
        this.setPadding(padding);
    }

    public Node getChild(Integer index) {
        return this.getChildren().get(index);
    }

    public void addChild(Node child){
        this.getChildren().add(child);
    }

    public void addChild(Integer index, Node child){
        this.getChildren().add(index,child);
    }

    public VBoxMolby addChildChaining(Node child){
        this.getChildren().add(child);
        return this;
    }

    public VBoxMolby addChildChaining(Integer index, Node child){
        this.getChildren().add(index,child);
        return this;
    }

    public Integer count(){
        return this.getChildren().size();
    }
}
