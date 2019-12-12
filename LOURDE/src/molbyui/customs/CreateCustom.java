package molbyui.customs;

import com.jfoenix.validation.RequiredFieldValidator;
import entity.Level;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import molbyui.controls.*;
import row.RowMolby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static row.RowMolbyLevel.createLittleRows;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CreateCustom {

    public CreateCustom(){

    }

    public GridPaneMolby createGridPaneChild(Integer numberRows, Integer numberColumn, Map<Integer, Double> valueRow, Map<Integer, Double> valueColumn, Integer indexColumn, Integer indexRow){
        GridPaneMolby gridPriceRequired = new GridPaneMolby(numberRows, numberColumn, valueRow, valueColumn);
        assignPositionGrid(gridPriceRequired, indexColumn, indexRow, HPos.LEFT, VPos.TOP);
        return gridPriceRequired;
    }
    public GridPaneMolby createGridPaneChild(Double height, Integer numberRows, Integer numberColumn, Map<Integer, Double> valueRow, Map<Integer, Double> valueColumn, Integer indexColumn, Integer indexRow){
        GridPaneMolby gridPriceRequired = new GridPaneMolby(height, numberRows, numberColumn, valueRow, valueColumn);
        assignPositionGrid(gridPriceRequired, indexColumn, indexRow, HPos.LEFT, VPos.TOP);
        return gridPriceRequired;
    }


    public VBoxMolby createVBox(Double prefWidth, Double prefHeight, Integer indexColumn, Integer indexRow, Insets padding){
        VBoxMolby vBoxMolby = new VBoxMolby(prefWidth, prefHeight, padding);
        assignPositionGrid(vBoxMolby, indexColumn, indexRow, HPos.LEFT, VPos.TOP);
        return vBoxMolby;
    }

    public VBoxMolby createVBox(Double prefWidth, Double prefHeight, Integer indexColumn, Integer indexRow, Insets padding, Double verticalGap, Double translateY, Double translateX){
        VBoxMolby vBoxMolby = new VBoxMolby(prefWidth, prefHeight, padding);
        vBoxMolby.setSpacing(verticalGap);
        vBoxMolby.setTranslateY(translateY);
        vBoxMolby.setTranslateX(translateX);
        assignPositionGrid(vBoxMolby, indexColumn, indexRow, HPos.LEFT, VPos.TOP);
        return vBoxMolby;
    }

    public HBoxMolby createHBox(Double prefWidth, Double prefHeight, Integer indexColumn, Integer indexRow, Insets padding){
        HBoxMolby hBoxMolby = new HBoxMolby(prefWidth, prefHeight, padding);
        assignPositionGrid(hBoxMolby, indexColumn, indexRow, HPos.LEFT, VPos.TOP);
        return hBoxMolby;
    }

    public void assignPositionGrid(Node element, Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos){
        GridPane.setColumnIndex(element, indexColumn);
        GridPane.setRowIndex(element, indexRow);
        GridPane.setHalignment(element, hPos);
        GridPane.setValignment(element,vPos);
    }

    public ImageViewMolby createImageViewMolby(String url, Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, ButtonMolby buttonCamera){
        ImageViewMolby imageViewMolby = RowMolby.pictureRow(url, 150.0);
        imageViewMolby.setCursor(Cursor.HAND);
        assignPositionGrid(imageViewMolby, indexColumn, indexRow, hPos, vPos);
        imageViewMolby.setOnMouseEntered(mouseEvent -> buttonCamera.setVisible(true));
        return imageViewMolby;
    }

    public ImageViewMolby createImageViewMolby(String url, Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, Cursor cursor){
        ImageViewMolby imageViewMolby = RowMolby.pictureRow(url, 150.0);
        imageViewMolby.setCursor(cursor);
        assignPositionGrid(imageViewMolby, indexColumn, indexRow, hPos, vPos);
        return imageViewMolby;
    }

    public ButtonMolby createButtonAddEdit(Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, Font font, Double height, Double width, String text, EventHandler<MouseEvent> mouseEvent) {
        ButtonMolby button = new ButtonMolby("add-edit-button",font, Paint.valueOf("#003988"), text, height, width, "button-update");
        assignPositionGrid(button, indexColumn, indexRow, hPos, vPos);
        button.setOnMouseClicked(mouseEvent);
        return button;
    }

    public ButtonMolby createButtonDelete(Integer indexColumn, Integer indexRow, HPos hPos, VPos vPos, Font font, Double height, Double width, EventHandler<MouseEvent> mouseEvent) {
        ButtonMolby button = new ButtonMolby("delete-button",font, Paint.valueOf("#42000e"), "DELETE", height, width, "button-delete");
        assignPositionGrid(button, indexColumn, indexRow, hPos, vPos);
        button.setOnMouseClicked(mouseEvent);
        return button;
    }


    public TextAreaMolby InitializeTextArea(Font font, String value, RequiredFieldValidator required, String style){
        TextAreaMolby textAreaMolby = new TextAreaMolby(font);
        textAreaMolby.getStyleClass().add(style);
        textAreaMolby.setText(value);
        textAreaMolby.getValidators().add(required);
        return textAreaMolby;
    }

    public TextFieldMolby InitializeTextField(Font font, String value, RequiredFieldValidator required, Boolean onlyNumber, String style){
        TextFieldMolby textFieldMolby = new TextFieldMolby(font, onlyNumber);
        textFieldMolby.getStyleClass().add(style);
        textFieldMolby.setText(value);
        textFieldMolby.getValidators().add(required);
        return textFieldMolby;
    }

    public TextFieldMolby InitializeTextField(Font font, String value, RequiredFieldValidator required, Boolean onlyNumber, Double translateY, Integer indexColumn){
        TextFieldMolby textFieldMolby = new TextFieldMolby(font, onlyNumber);
        GridPane.setColumnIndex(textFieldMolby, indexColumn);
        textFieldMolby.setText(value);
        textFieldMolby.setTranslateY(translateY);
        textFieldMolby.getValidators().add(required);
        return textFieldMolby;
    }

    public TextFieldMolby InitializeTextField(Font font, String value, RequiredFieldValidator required, Boolean onlyNumber, Double translateY, Integer indexColumn, String style){
        TextFieldMolby textFieldMolby = new TextFieldMolby(font, onlyNumber);
        GridPane.setColumnIndex(textFieldMolby, indexColumn);
        textFieldMolby.getStyleClass().add(style);
        textFieldMolby.setText(value);
        textFieldMolby.setTranslateY(translateY);
        textFieldMolby.getValidators().add(required);
        return textFieldMolby;
    }

    public ListViewMolby createListView(ArrayList<Level> elements, Integer alreadySelected){
        ListViewMolby list = new ListViewMolby();
        list.getStyleClass().add("list-view-row");
        list.getItems().addAll(createLittleRows(elements));
        if(alreadySelected != null){
            for(Object pane : list.getItems()){
                if(Integer.parseInt(((PaneMolby)pane).getId().replace("paneLittleLevel","")) ==  alreadySelected){
                    list.getSelectionModel().select( list.getItems().indexOf(pane));
                    break;
                }
            }
        }
        return list;
    }

    public ListViewMolby createListView(ArrayList<PaneMolby> elements, Integer height, Integer width){
        ListViewMolby list = new ListViewMolby();
        list.setPrefHeight(height);
        list.setMaxHeight(height);
        list.setMinHeight(height);
        list.setPrefWidth(width);
        list.setMaxWidth(width);
        list.setMinWidth(width);
        list.getStyleClass().add("list-view-row");
        //noinspection unchecked
        list.getItems().addAll(elements);
        return list;
    }

    public Map<Integer,String> getLevelListView(ListViewMolby list){
        PaneMolby paneMolby = ((PaneMolby)list.getSelectionModel().getSelectedItem());
        return new HashMap<>() {{
            put(Integer.parseInt(paneMolby.getId().replace("paneLittleLevel", "")), ((LabelMolby) paneMolby.getChild(0)).getText());
        }};
    }




}
