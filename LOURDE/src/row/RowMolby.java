package row;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import molbyui.controls.*;

@SuppressWarnings("SuspiciousNameCombination")
public class RowMolby {

    public static ImageViewMolby pictureRow(String folder, String id){
        ImageViewMolby pictureRow = new ImageViewMolby("./src/view/img/" + folder +"/" + id +".jpg" , false, true, 100.0);
        int radiusCircle = 45;
        pictureRow.setTranslateX(-(pictureRow.getBoundsInLocal().getWidth() / 2) + radiusCircle);
        Circle clip = new Circle(pictureRow.getBoundsInLocal().getWidth() / 2, pictureRow.getFitHeight() / 2, radiusCircle);
        pictureRow.setClip(clip);
        return pictureRow;
    }

    public static ImageViewMolby pictureRow(String url, Double height){
        ImageViewMolby pictureRow = new ImageViewMolby(url, false, false, height);
        pictureRow.maxHeight(150);
        pictureRow.maxWidth(150);
        if(pictureRow.getLayoutBounds().getWidth() > 150){
            pictureRow.setFitWidth(150);
        }
        if(pictureRow.getLayoutBounds().getHeight() > 150){
            pictureRow.setFitHeight(150);
        }
        Circle clip = new Circle(pictureRow.getBoundsInLocal().getWidth() / 2, pictureRow.getFitHeight() / 2, pictureRow.getBoundsInLocal().getWidth() / 2);
        pictureRow.setClip(clip);
        return pictureRow;
    }

    public static RipplerMolby buttonActionRow(ImageViewMolby imageViewRowIcon, EventHandler<MouseEvent> mouseEvent,String background, Paint colorRipple){
        HBoxMolby button= new HBoxMolby(76.0, Pos.CENTER, imageViewRowIcon, Cursor.HAND, background);
        button.setOnMouseClicked(mouseEvent);
        RipplerMolby rippler;
        rippler = new RipplerMolby(button, RipplerMolby.RipplerMask.CIRCLE, colorRipple);
        return rippler;
    }

    public static ButtonMolby buttonActionRow(String style, ImageViewMolby imageViewRowIcon, EventHandler<MouseEvent> mouseEvent, Paint colorRipple, Double width){
        ButtonMolby button= new ButtonMolby(style, imageViewRowIcon, Cursor.HAND, width, width, colorRipple, true);
        button.setOnMouseClicked(mouseEvent);
        return button;
    }

    public static ButtonMolby buttonActionRow(String style, ImageViewMolby imageViewRowIcon, EventHandler<MouseEvent> mouseEvent, Paint colorRipple, Double width, Integer indexColumn){
        ButtonMolby button= new ButtonMolby(style, imageViewRowIcon, Cursor.HAND, width, width, colorRipple, true);
        GridPane.setColumnIndex(button, indexColumn);
        button.setOnMouseClicked(mouseEvent);
        return button;
    }


    public static RipplerMolby buttonActionRow(PaneMolby paneRow, ImageViewMolby imageViewRowIcon, Double XPosition, EventHandler<MouseEvent> mouseEvent, Boolean expand){
        HBoxMolby button= new HBoxMolby(76.0, Pos.CENTER, imageViewRowIcon, Cursor.HAND);
        button.setOnMouseClicked(mouseEvent);
        RipplerMolby rippler;
        if(paneRow != null) {
            rippler = new RipplerMolby(button, RipplerMolby.RipplerMask.CIRCLE, Paint.valueOf("#ffffff"), XPosition, paneRow, expand);
        } else {
            rippler = new RipplerMolby(button, RipplerMolby.RipplerMask.CIRCLE, Paint.valueOf("#ffffff"), XPosition,expand);
        }
        return rippler;
    }


    public static RipplerMolby buttonActionRow(PaneMolby paneRow, ImageViewMolby imageViewRowIcon, Double XPosition, EventHandler<MouseEvent> mouseEvent, Boolean expand, Double width){
        HBoxMolby button= new HBoxMolby(width, Pos.CENTER, imageViewRowIcon, Cursor.HAND);
        button.setOnMouseClicked(mouseEvent);
        RipplerMolby rippler;
        if(paneRow != null) {
            rippler = new RipplerMolby(button, RipplerMolby.RipplerMask.CIRCLE, Paint.valueOf("#ffffff"), XPosition, paneRow, expand);
        } else {
            rippler = new RipplerMolby(button, RipplerMolby.RipplerMask.CIRCLE, Paint.valueOf("#ffffff"), XPosition,expand);
        }
        return rippler;
    }


}

