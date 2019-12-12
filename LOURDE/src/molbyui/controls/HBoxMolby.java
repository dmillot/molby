package molbyui.controls;

import javafx.beans.DefaultProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

@DefaultProperty("children")
public class HBoxMolby extends HBox {
    public Node getChild(Integer index) {
        return this.getChildren().get(index);
    }

    public void addChild(Node child){
        this.getChildren().add(child);
    }

    public HBoxMolby(){

    }

    public HBoxMolby(Double prefWidth, Double prefHeight, Insets padding){
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        this.setPadding(padding);
    }

    public HBoxMolby(Double prefWidth, Double prefHeight, Insets padding, Double spacing){
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        this.setPadding(padding);
        this.setSpacing(spacing);
    }

    public HBoxMolby(Double valueCarre, Pos position, ImageViewMolby image, Cursor cursor){
        this.setMaxWidth(valueCarre);
        this.setPrefWidth(valueCarre);
        this.setMaxHeight(valueCarre);
        this.setPrefHeight(valueCarre);
        this.setAlignment(position);
        this.addChild(image);
        this.setCursor(cursor);
    }

    public HBoxMolby(Double valueCarre, Pos position, ImageViewMolby image, Cursor cursor, Double width){
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setMaxHeight(valueCarre);
        this.setPrefHeight(valueCarre);
        this.setAlignment(position);
        this.addChild(image);
        this.setCursor(cursor);
    }

    public HBoxMolby(Double valueCarre, Pos position, ImageViewMolby image, Cursor cursor, String background){
        this.setMaxWidth(valueCarre);
        this.setPrefWidth(valueCarre);
        this.setMaxHeight(valueCarre);
        this.setPrefHeight(valueCarre);
        this.setAlignment(position);
        this.addChild(image);
        this.setStyle(background);
        this.setCursor(cursor);
    }

    public HBoxMolby(Double valueCarre, Pos position, ImageViewMolby image, Cursor cursor, String background, Double width){
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setMaxHeight(valueCarre);
        this.setPrefHeight(valueCarre);
        this.setAlignment(position);
        this.addChild(image);
        this.setStyle(background);
        this.setCursor(cursor);
    }


    public HBoxMolby addChildChaining(Node child){
        this.getChildren().add(child);
        return this;
    }

    public HBoxMolby addChildChaining(Integer index, Node child){
        this.getChildren().add(index,child);
        return this;
    }
}
