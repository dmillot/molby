package molbyui.customs;

import controller.MainController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import molbyui.controls.ButtonMolby;
import molbyui.controls.GridPaneMolby;
import molbyui.controls.ImageViewMolby;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import controller.LevelController;

public class CreateCustomLevel extends CreateCustom {

    public LevelController controller;

    public void setController(LevelController controllerParam){
        controller = controllerParam;
    }

    private final ArrayList<String> pictureFormat = new ArrayList<>() {
        {
            add("png");
            add("jpeg");
            add("jpg");
        }
    };

    public CreateCustomLevel(){

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

    public ButtonMolby createButtonCamera(Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, FileChooser fileChooser, GridPaneMolby gridAdd){
        ButtonMolby buttonMolby = new ButtonMolby(false, new ImageViewMolby("./src/view/img/camera.png", false, true, 55.0), Cursor.HAND, 150.0, 150.0, Paint.valueOf("#003988"),"-fx-background-color: rgba(0, 82, 161 , 0.5);");
        assignPositionGrid(buttonMolby, indexColumn, indexRow, hPos, vPos);
        Circle clip = new Circle(75, 75, 75);
        buttonMolby.setClip(clip);

        buttonMolby.setOnMouseClicked(mouseEvent -> {
            File file = fileChooser.showOpenDialog(MainController.getStage());
            if (file != null && isPicture(file)) {
                gridAdd.replaceChild(0, createImageViewMolby(file.getAbsolutePath(), 1, 0, HPos.CENTER, VPos.TOP, buttonMolby));
                try {
                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                    };
                    Files.copy(file.toPath(), Paths.get("./src/view/img/level/wait.jpg"), options);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonMolby.setOnMouseExited(mouseEvent -> buttonMolby.setVisible(false));

        return buttonMolby;
    }


    private Boolean isPicture(File file)
    {
        if(file.getName().contains(".") && file.getName().lastIndexOf(".")!= 0)
        {
            return pictureFormat.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        }
        return false;
    }

}
