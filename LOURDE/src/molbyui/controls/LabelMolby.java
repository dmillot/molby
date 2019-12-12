package molbyui.controls;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class LabelMolby extends Label {

    public LabelMolby(){
    }

    public LabelMolby(String text, Font fontListView){
        this.setText(text);
        this.setFont(fontListView);
        this.setWrapText(true);
    }

    public LabelMolby(String text, Font fontListView, Effect dropShadow){
        this.setText(text);
        this.setFont(fontListView);
        this.setEffect(dropShadow);
        this.setWrapText(true);
    }

    public LabelMolby(String text, Font fontListView, Color color, Effect dropShadow){
        this.setText(text);
        this.setFont(fontListView);
        this.setTextFill(color);
        this.setEffect(dropShadow);
        this.setWrapText(true);
    }

    public LabelMolby(String text, Font fontListView, Color color, Effect dropShadow, Integer indexColumn, Integer indexRow){
        this.setText(text);
        this.setFont(fontListView);
        this.setTextFill(color);
        this.setEffect(dropShadow);
        GridPane.setColumnIndex(this,indexColumn);
        GridPane.setRowIndex(this,indexRow);
        this.setWrapText(true);
    }

    public LabelMolby(String text, Font fontListView, Color color, Effect dropShadow, Integer indexColumn, Integer indexRow, HPos hPos){
        this.setText(text);
        this.setFont(fontListView);
        this.setTextFill(color);
        this.setEffect(dropShadow);
        GridPane.setColumnIndex(this,indexColumn);
        GridPane.setRowIndex(this,indexRow);
        GridPane.setHalignment(this, hPos);
        this.setWrapText(true);
    }

    public LabelMolby(String text, Color colorListView, Font fontListView, Boolean wrapText, Double maxHeight, PaneMolby paneMolby, Effect dropShadow){
        this.setText(text);
        this.setTextFill(colorListView);
        this.setFont(fontListView);
        this.setWrapText(wrapText);
        this.setMaxHeight(maxHeight);
        this.layoutYProperty().bind(paneMolby.heightProperty().subtract(this.heightProperty()).divide(2));
        this.setEffect(dropShadow);
        this.setWrapText(true);

    }

    public LabelMolby(String text, Color colorListView, Font fontListView, Boolean wrapText, Double maxHeight, PaneMolby paneMolby, Effect dropShadow, Double XPosition, Double maxWidth, Boolean expand){
        this.setText(text);
        this.setTextFill(colorListView);
        this.setFont(fontListView);
        this.setWrapText(wrapText);
        this.setMaxHeight(maxHeight);
        this.layoutYProperty().bind(paneMolby.heightProperty().subtract(this.heightProperty()).divide(2));
        this.setEffect(dropShadow);
        this.setLayoutX(XPosition);
        this.setMaxWidth(maxWidth);
        if(expand) {
            this.setTranslateX(295);
        }
        this.setWrapText(true);
    }

    public LabelMolby(String text, Color colorListView, Font fontListView, Boolean wrapText, Double maxHeight, PaneMolby paneMolby, Effect dropShadow, Double XPosition, Double maxWidth, Boolean expand, Double translateX){
        this.setText(text);
        this.setTextFill(colorListView);
        this.setFont(fontListView);
        this.setWrapText(wrapText);
        this.setMaxHeight(maxHeight);
        this.layoutYProperty().bind(paneMolby.heightProperty().subtract(this.heightProperty()).divide(2));
        this.setEffect(dropShadow);
        this.setLayoutX(XPosition);
        this.setMaxWidth(maxWidth);
        if(expand && translateX != null) {
            this.setTranslateX(translateX);
        }
        this.setWrapText(true);
    }

    public LabelMolby(String text, Color colorListView, Font fontListView, Boolean wrapText, Double maxHeight, PaneMolby paneMolby, Effect dropShadow, Double XPosition, Double maxWidth){
        this.setText(text);
        this.setTextFill(colorListView);
        this.setFont(fontListView);
        this.setWrapText(wrapText);
        this.setMaxHeight(maxHeight);
        this.layoutYProperty().bind(paneMolby.heightProperty().subtract(this.heightProperty()).divide(2));
        this.setEffect(dropShadow);
        this.setLayoutX(XPosition);
        this.setMaxWidth(maxWidth);
        this.setWrapText(true);
    }





}
