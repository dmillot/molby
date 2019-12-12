package molbyui.customs;

import controller.YearGroupController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import molbyui.controls.ButtonMolby;
import molbyui.controls.ImageViewMolby;

public class CreateCustomYearGroup extends CreateCustom {

    public YearGroupController controller;

    public void setController(YearGroupController controllerParam){
        controller = controllerParam;
    }

    public CreateCustomYearGroup(){

    }

    public ButtonMolby createButtonClose(Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos){
        ButtonMolby buttonMolby = new ButtonMolby("close-button",true, new ImageViewMolby("./src/view/img/close.png", false, true, 20.0), Cursor.HAND, 60.0, 60.0, Paint.valueOf("#003988"), new Insets(5), -44.0, "button-close");
        assignPositionGrid(buttonMolby, indexColumn, indexRow , hPos, vPos);
        buttonMolby.setOnMouseClicked(mouseEvent -> controller.closeDialog());
        return buttonMolby;
    }

    public ButtonMolby createButtonCancel(Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, Font font, Double height, Double width){
        ButtonMolby buttonMolby =  new ButtonMolby("cancel-button", font, Paint.valueOf("#003988"), "CANCEL", height, width, "button-cancel-other");
        assignPositionGrid(buttonMolby, indexColumn, indexRow , hPos, vPos);
        buttonMolby.setOnMouseClicked(mouseEvent -> controller.closeDialog());
        return buttonMolby;
    }

}
