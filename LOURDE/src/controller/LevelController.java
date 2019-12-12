package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.Level;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.LevelDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomLevel;
import row.RowMolbyLevel;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;
import static row.RowMolby.buttonActionRow;

@SuppressWarnings("ALL")
public class LevelController{


    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewLevel;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorLevel;

    @FXML
    private StackPane stackDialog;

    private ButtonMolby buttonCamera;
    private CreateCustomLevel custom;
    private JFXDialogLayout content;

    private Boolean expanded = false;
    private Boolean animationExpanded = false;
    private JFXDialog dialog;
    private FileChooser fileChooser;
    private static File file;


    public Boolean burgerExpand = false;


    @FXML
    void buttonExpand_Clicked(MouseEvent event) {
        if(!animationExpanded) {
            animationExpanded = true;
            int degres = 180;
            final Map<ImageViewMolby, Integer> arrayDegres = (expanded) ? new HashMap<>() {
                {
                    put(leftUp, -degres);
                    put(rightUp, degres);
                    put(leftDown, degres);
                    put(rightDown, -degres);
                }
            } : new HashMap<>() {
                {
                    put(leftUp, degres);
                    put(rightUp, -degres);
                    put(leftDown, -degres);
                    put(rightDown, degres);
                }
            };
            expanded = !expanded;
            arrayDegres.forEach((k, v) -> {
                RotateTransition rt = new RotateTransition(Duration.millis(500), k);
                rt.setByAngle(v);
                rt.play();
                rt.onFinishedProperty().set(actionEvent -> {
                    animationExpanded = false;
                    Double valueVertical = (expanded) ? 20.0 : 0.0;
                    timeLineAnimation(50.0, listViewLevel.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewLevel.refresh();
                    }
                });
            });
        }
    }




    public void initialize() throws ExecutionException, InterruptedException {
        InitializeDataListView();
        InitializeChooserFile();
        InitializeDialogs();
        InitializeButtonAdd();
    }

    private void InitializeChooserFile(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
    }

    private void openDialog(GridPaneMolby gridPaneMolby){
        content.setBody(gridPaneMolby);
        dialog.setContent(content);
        dialog.show();
    }

    public void closeDialog(){
        listViewLevel.getSelectionModel().clearAndSelect(-1);
        dialog.close();
    }

    private void InitializeButtonAdd(){
        RipplerMolby rippler = buttonActionRow(null, buttonAdd, 0.0, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createDialogAddEdit(null);
            }
        }, false);
        gridMenu.addChildColumnAt(rippler, 2);
    }

    private void InitializeDataListView() throws ExecutionException, InterruptedException {
        listViewLevel.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<Level>> task = new Task<ArrayList<Level>>() {
            @Override
            public ArrayList<Level> call() {
                return LevelDao.getLevels();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewLevel.getItems().addAll(RowMolbyLevel.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyLevel.controller = this;
            loadingScreen.setVisible(false);
        });
    }

    private void InitializeDialogs(){
        custom = new CreateCustomLevel();
        custom.setController(this);
        content = new JFXDialogLayout();
        dialog = new JFXDialog(stackDialog, null,JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent jfxDialogEvent) {
                stackDialog.setDisable(true);
                stackDialog.setVisible(false);
            }
        });
        dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent jfxDialogEvent) {
                stackDialog.setDisable(false);
                stackDialog.setVisible(true);
            }
        });
    }


    public void actionAnchor(Boolean expand, AnchorPaneMolby anchorView, Double duration){
        Double valueAction = (expand) ? 1831.0 : 1536.0;
        Double duree = (expand) ? (duration != null) ? duration : 500.0 : 200.0;
        Double columnGrid = (expand) ? 885.0 : 590.0;
        Double positionX = (burgerExpand) ? 295.0 : 0.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby)anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby)anchorLoader.getChild(0);
        if(duration == null){
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby)anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby)gridPaneList.getChild(1)).getColumn(2).prefWidthProperty(), columnGrid).play();
            timeLineAnimation(duree, ((GridPaneMolby)gridPaneList.getChild(1)).getColumn(2).maxWidthProperty(), columnGrid).play();
            for(Object paneList : listViewLevel.getItems()){
                timeLineAnimation(duree,((Label)((PaneMolby)paneList).getChild(2)).prefWidthProperty(), columnGrid).play();
                timeLineAnimation(duree,((Label)((PaneMolby)paneList).getChild(2)).maxWidthProperty(), columnGrid).play();
                for(int i = 3; i < 7; i++){
                    timeLineAnimation(duree,((PaneMolby)paneList).getChild(i).translateXProperty(), positionX).play();
                }
            }
            timeLineAnimation(duree, ((StackPane)anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby)anchorLoader.getChild(0)).setPrefWidth(valueAction);
            ((GridPaneMolby)gridPaneList.getChild(1)).getColumn(2).setPrefWidth(columnGrid);
            ((GridPaneMolby)gridPaneList.getChild(1)).getColumn(2).setMaxWidth(columnGrid);
            for(Object paneList : listViewLevel.getItems()){
                ((Label)((PaneMolby)paneList).getChild(2)).setPrefWidth(columnGrid);
                ((Label)((PaneMolby)paneList).getChild(2)).setMaxWidth(columnGrid);
                for(int i = 3; i < 7; i++){
                    ((PaneMolby)paneList).getChild(i).setTranslateX(positionX);
                }
            }
            ((StackPane)anchorLoader.getChild(1)).setPrefWidth(valueAction);
        }

    }


    public void createDialogAddEdit(Level level){
        GridPaneMolby gridDialog = new GridPaneMolby("contentDialog", 500.0, 650.0, 10, 3, new HashMap<>(){{
            put(1,20.0);
            put(3,20.0);
            put(5,20.0);
            put(7,40.0);
        }}, new HashMap<>(){{
            put(0,50.0);
            put(2,50.0);
        }});

        Font font = Font.font("Segoe UI Light", 25.0);
        Font fontLabel = Font.font("Segoe UI", 25.0);
        Font fontDescription = Font.font("Segoe UI Light", 20.0);
        Insets padding = new Insets(0);
        Color colorGray= Color.rgb(220, 220, 220, 0.8);
        Color colorMain = Color.rgb(0,0,0);

        RequiredFieldValidator required = new RequiredFieldValidator();
        required.setMessage("Field must be completed.");
        String imageURL = (level != null) ? "./src/view/img/level/"+ level.getId() + ".jpg": "./src/view/img/default.png";
        String valueName = (level != null) ? level.getLabel() : null;
        String valueDescription = (level != null) ? level.getDescription() : null;
        Integer valuePrice = (level != null) ? level.getCostXp() : null;
        Integer valueRequired = (level != null) ? level.getRequiredXp() : null;
        String valueButton = (level != null) ? "UPDATE" : "ADD";

        TextFieldMolby nameText = custom.InitializeTextField(font, valueName, required, false, "jfx-text-field-second");
        TextAreaMolby descriptionTextArea = custom.InitializeTextArea(font, valueDescription, required, "jfx-text-area-second");
        TextFieldMolby priceText = custom.InitializeTextField(font, String.valueOf(valuePrice), required, true, "jfx-text-field-second");
        TextFieldMolby requiredText = custom.InitializeTextField(font, String.valueOf(valueRequired), required, true, "jfx-text-field-second");

        buttonCamera = custom.createButtonCamera(1, 0, HPos.CENTER, VPos.TOP, fileChooser, gridDialog);
        ImageViewMolby imageViewMolby = custom.createImageViewMolby(imageURL, 1, 0, HPos.CENTER, VPos.TOP, buttonCamera);
        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        GridPaneMolby gridRequiredPrice = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 6);
        gridRequiredPrice.addChildChaining(
                custom.createVBox(100.0,100.0, 0, 0, padding).addChildChaining(
                        new LabelMolby("Price:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                    priceText
                )
        ).addChildChaining(
                custom.createVBox(100.0,100.0, 2, 0,padding).addChildChaining(
                        new LabelMolby("Required:",fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                    requiredText
                )
        );

        GridPaneMolby gridPaneMolby = gridDialog.addChildChaining(imageViewMolby).addChildChaining(buttonCamera).addChildChaining(buttonClose)
        .addChildChaining(
                custom.createVBox(100.0,100.0, 1, 2,padding).addChildChaining(
                    new LabelMolby("Name:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
            ).addChildChaining(
                nameText
            )
        ).addChildChaining(
                        custom.createVBox(100.0,200.0, 1, 4, padding).addChildChaining(
                    new LabelMolby("Description:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
            ).addChildChaining(
                descriptionTextArea
            )
        ).addChildChaining(gridRequiredPrice).addChildChaining(custom.createButtonAddEdit(1, 8, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0, valueButton, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(nameText.getText() != null && descriptionTextArea.getText() != null && priceText.getText() != null && requiredText.getText() != null && !nameText.getText().equals("") && !descriptionTextArea.getText().equals("") && !priceText.getText().equals("") && !requiredText.getText().equals("")){
                        if(level != null){
                            Integer indexLevelUpdated = null;
                            for(Object pane : listViewLevel.getItems()){
                                if(level.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneLevel",""))){
                                    indexLevelUpdated = listViewLevel.getItems().indexOf(pane);
                                    listViewLevel.getItems().remove(pane);
                                    break;
                                }
                            }
                            Level levelUpdate = new Level(level.getId(), nameText.getText(), descriptionTextArea.getText(), Integer.parseInt(priceText.getText()), Integer.parseInt(requiredText.getText()), "images/developers/"+level.getId()+".jpg");
                            LevelDao.update(levelUpdate);
                            CopyOption[] options = new CopyOption[]{
                                    StandardCopyOption.REPLACE_EXISTING,
                                    StandardCopyOption.COPY_ATTRIBUTES
                            };
                            try {
                                Files.copy(Paths.get("./src/view/img/level/wait.jpg"), Paths.get("./src/view/img/level/" + level.getId()),options);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            listViewLevel.getItems().add(indexLevelUpdated, RowMolbyLevel.createRow(levelUpdate, burgerExpand));
                        } else {
                            Level levelNew = new Level(nameText.getText(), descriptionTextArea.getText(), Integer.parseInt(priceText.getText()), Integer.parseInt(requiredText.getText()), null);
                            levelNew.setId(LevelDao.create(levelNew));
                            CopyOption[] options = new CopyOption[]{
                                    StandardCopyOption.REPLACE_EXISTING,
                                    StandardCopyOption.COPY_ATTRIBUTES
                            };
                            try {
                                Files.copy(Paths.get("./src/view/img/level/wait.jpg"), Paths.get("./src/view/img/level/" + levelNew.getId()  + ".jpg"),options);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            listViewLevel.getItems().add(RowMolbyLevel.createRow(levelNew, burgerExpand));
                        }
                        listViewLevel.refresh();
                        dialog.close();
                    } else {
                        nameText.validate();
                        descriptionTextArea.validate();
                        priceText.validate();
                        requiredText.validate();
                    }
                }
            }
        ));
        openDialog(gridPaneMolby);

    }


    public void createDialogDelete(Level level){
        GridPaneMolby gridDialog = new GridPaneMolby("contentDialog", 700.0, 150.0, 4, 3, new HashMap<>(){{
            put(1,40.0);
        }}, new HashMap<>(){{
            put(0,50.0);
            put(2,50.0);
        }});

        Font font = Font.font("Segoe UI Light", 25.0);
        Font fontLabel = Font.font("Segoe UI", 25.0);
        Font fontDescription = Font.font("Segoe UI Light", 20.0);
        Insets padding = new Insets(0);
        Color colorGray= Color.rgb(220, 220, 220, 0.8);
        Color colorMain = Color.rgb(0,0,0);

        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        GridPaneMolby gridButton = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 2);
        gridButton.addChildChaining(
        custom.createButtonDelete(0, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LevelDao.remove(level);
                for(Object pane : listViewLevel.getItems()){
                    if(level.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneLevel",""))){
                        listViewLevel.getItems().remove(pane);
                        break;
                    }
                }
                listViewLevel.refresh();
                closeDialog();
            }
        })).addChildChaining(
            custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby("Do you really want delete this level: " + level.getLabel() + " ?" , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }




}
