package molbyui.controls;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ButtonMolby extends JFXButton {

    public ButtonMolby(){

    }

    public ButtonMolby(String id, Font font, Paint rippler, String background, String text, Double height, Double width){
        this.setId(id);
        this.setFont(font);
        this.setText(text);
        this.setRipplerFill(rippler);
        this.setStyle(background);
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setCursor(Cursor.HAND);
    }

    public ButtonMolby(String id, Font font, Paint rippler, String text, Double height, Double width, String style){
        this.setId(id);
        this.getStyleClass().add(style);
        this.setFont(font);
        this.setText(text);
        this.setRipplerFill(rippler);
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setCursor(Cursor.HAND);
    }


    public ButtonMolby(String id, Font font, Paint rippler, String background, String text, Integer rowIndex, Integer columnIndex){
        this.setId(id);
        this.setFont(font);
        this.setText(text);
        this.setRipplerFill(rippler);
        this.setStyle(background);
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setCursor(Cursor.HAND);
        GridPane.setColumnIndex(this, columnIndex);
        GridPane.setRowIndex(this, rowIndex);
    }

    public ButtonMolby(String id, Font font, Paint rippler, String background, String text, Integer rowIndex, Integer columnIndex, Double width){
        this.setId(id);
        this.setFont(font);
        this.setText(text);
        this.setRipplerFill(rippler);
        this.setStyle(background);
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setCursor(Cursor.HAND);
        this.setPrefWidth(width);
        GridPane.setColumnIndex(this, columnIndex);
        GridPane.setRowIndex(this, rowIndex);
    }

    public ButtonMolby(String style, ImageViewMolby imageViewMolby, Cursor cursor, Double height, Double width){
        this.setGraphic(imageViewMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setRipplerFill(Paint.valueOf("#FFFFFFFF"));
        this.getStyleClass().add(style);
    }

    public ButtonMolby(String style, ImageViewMolby imageViewMolby, Cursor cursor, Double height, Double width, Paint rippler, Boolean clipped){
        this.setGraphic(imageViewMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setWrapText(false);
        this.setPrefWidth(width);
        this.setRipplerFill(rippler);
        this.getStyleClass().add(style);
        if(clipped){
            Circle clip = new Circle(width / 2, height / 2, width / 2);
            this.setClip(clip);
        }
    }

    public ButtonMolby(String style, FontAwesomeIconView iconMolby, Cursor cursor, Double height, Double width, Paint rippler, Boolean clipped){
        this.setGraphic(iconMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setRipplerFill(rippler);
        this.getStyleClass().add(style);
        if(clipped){
            Circle clip = new Circle(width / 2, height / 2, width / 2);
            this.setClip(clip);
        }
    }

    public ButtonMolby(Boolean visible, ImageViewMolby imageViewMolby, Cursor cursor, Double height, Double width, Paint rippler, String background){
        this.setVisible(visible);
        this.setGraphic(imageViewMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setRipplerFill(rippler);
        this.setStyle(background);
    }

    public ButtonMolby(String id, Boolean visible, ImageViewMolby imageViewMolby, Cursor cursor, Double height, Double width, Paint rippler, String background, Insets padding, Double translateX){
        this.setId(id);
        this.setVisible(visible);
        this.setGraphic(imageViewMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setRipplerFill(rippler);
        this.setStyle(background);
        this.setPadding(padding);
        this.setTranslateY(translateX);

    }

    public ButtonMolby(String id, Boolean visible, ImageViewMolby imageViewMolby, Cursor cursor, Double height, Double width, Paint rippler, Insets padding, Double translateX, String style){
        this.setId(id);
        this.getStyleClass().add(style);
        this.setVisible(visible);
        this.setGraphic(imageViewMolby);
        this.setCursor(cursor);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setRipplerFill(rippler);
        this.setPadding(padding);
        this.setTranslateY(translateX);

    }

}

