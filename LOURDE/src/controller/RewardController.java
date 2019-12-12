package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.LevelDao;
import model.RewardDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomReward;
import row.RowMolbyReward;

import java.io.File;
import java.io.IOException;
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
public class RewardController {



    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewReward;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorReward;
    @FXML
    private GridPaneMolby gridViewReward;

    @FXML
    private StackPane stackDialog;

    private ButtonMolby buttonCamera;
    private CreateCustomReward custom;
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
                    timeLineAnimation(50.0, listViewReward.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewReward.refresh();
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
        listViewReward.getSelectionModel().clearAndSelect(-1);
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
        listViewReward.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<entity.Reward>> task = new Task<ArrayList<entity.Reward>>() {
            @Override
            public ArrayList<entity.Reward> call() {
                return RewardDao.getRewards();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewReward.getItems().addAll(RowMolbyReward.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyReward.controller = this;
            loadingScreen.setVisible(false);
        });
    }

    private void InitializeDialogs(){
        custom = new CreateCustomReward();
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
        Double columnGrid = (expand) ? 805.0 : 510.0;
        Double positionX = (burgerExpand) ? 295.0 : 0.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby)anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby)anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).prefWidthProperty(), columnGrid).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).maxWidthProperty(), columnGrid).play();
            for (Object paneList : listViewReward.getItems()) {
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).prefWidthProperty(), columnGrid).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).maxWidthProperty(), columnGrid).play();
                for (int i = 3; i < 8; i++) {
                    timeLineAnimation(duree, ((PaneMolby) paneList).getChild(i).translateXProperty(), positionX).play();
                }
            }
            timeLineAnimation(duree, ((StackPane) anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby) anchorLoader.getChild(0)).setPrefWidth(valueAction);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).setPrefWidth(columnGrid);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).setMaxWidth(columnGrid);
            for (Object paneList : listViewReward.getItems()) {
                ((Label) ((PaneMolby) paneList).getChild(2)).setPrefWidth(columnGrid);
                ((Label) ((PaneMolby) paneList).getChild(2)).setMaxWidth(columnGrid);
                for (int i = 3; i < 8; i++) {
                    ((PaneMolby) paneList).getChild(i).setTranslateX(positionX);
                }
            }
            ((StackPane) anchorLoader.getChild(1)).setPrefWidth(valueAction);
        }
    }



    public void createDialogAddEdit(entity.Reward reward){
        GridPaneMolby gridDialog = new GridPaneMolby("contentDialog", 500.0, 775.0, 12, 3, new HashMap<>(){{
            put(1,20.0);
            put(3,20.0);
            put(5,20.0);
            put(6,200.0);
            put(7,20.0);
            put(9,40.0);
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

        String imageURL = (reward != null) ?  "./src/view/img/reward/"+ reward.getId() +".jpg" : "./src/view/img/default.png";
        String valueName = (reward != null) ? reward.getLabel() : null;
        String valueDescription = (reward != null) ? reward.getDescription() : null;
        Integer valuePrice = (reward != null) ? reward.getCostXp() : null;
        Integer valueAvailable = (reward != null) ? reward.getNbAvailable() : null;
        String valueButton = (reward != null) ? "UPDATE" : "ADD";
        Integer alreadySelected =  (reward != null) ? reward.getIdLevel() : null;

        TextFieldMolby nameText = custom.InitializeTextField(font, valueName, required, false, "jfx-text-field-second");
        TextAreaMolby descriptionTextArea = custom.InitializeTextArea(font, valueDescription, required, "jfx-text-area-second");
        ListViewMolby levelArea = custom.createListView(LevelDao.getLevels(), alreadySelected);
        TextFieldMolby priceText = custom.InitializeTextField(font, String.valueOf(valuePrice), required, true, "jfx-text-field-second");
        TextFieldMolby availableText = custom.InitializeTextField(font, String.valueOf(valueAvailable), required, true, "jfx-text-field-second");

        buttonCamera = custom.createButtonCamera(1, 0, HPos.CENTER, VPos.TOP, fileChooser, gridDialog);
        ImageViewMolby imageViewMolby = custom.createImageViewMolby(imageURL, 1, 0, HPos.CENTER, VPos.TOP, buttonCamera);
        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        GridPaneMolby gridRequiredPrice = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 8);
        gridRequiredPrice.addChildChaining(
                custom.createVBox(100.0,100.0, 0, 0, padding).addChildChaining(
                        new LabelMolby("Price:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        priceText
                )
        ).addChildChaining(
                custom.createVBox(100.0,100.0, 2, 0,padding).addChildChaining(
                        new LabelMolby("Available:",fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        availableText
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
                ).addChildChaining(
                        custom.createVBox(100.0,200.0, 1, 6, padding).addChildChaining(
                                new LabelMolby("Level:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                        ).addChildChaining(
                                levelArea
                        )
                )
                .addChildChaining(gridRequiredPrice).addChildChaining(custom.createButtonAddEdit(1, 10, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0, valueButton, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(nameText.getText() != null && descriptionTextArea.getText() != null && priceText.getText() != null && availableText.getText() != null  && levelArea.getSelectionModel().getSelectedItem() != null && !nameText.getText().equals("") && !descriptionTextArea.getText().equals("") && !priceText.getText().equals("") && !availableText.getText().equals("")){
                                    if(reward != null){
                                        Integer indexUpdated = null;
                                        for(Object pane : listViewReward.getItems()){
                                            if(reward.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneReward",""))){
                                                indexUpdated = listViewReward.getItems().indexOf(pane);
                                                listViewReward.getItems().remove(pane);
                                                break;
                                            }
                                        }
                                        Map.Entry<Integer,String> entry = custom.getLevelListView(levelArea).entrySet().iterator().next();
                                        entity.Reward update = new entity.Reward(reward.getId(),  nameText.getText(), Integer.parseInt(priceText.getText()), Integer.parseInt(availableText.getText()), descriptionTextArea.getText(), entry.getKey(), entry.getValue(), ((Image)((ImageViewMolby)gridDialog.getChild(0)).getImage()).getUrl());
                                        RewardDao.update(update);
                                        CopyOption[] options = new CopyOption[]{
                                                StandardCopyOption.REPLACE_EXISTING,
                                                StandardCopyOption.COPY_ATTRIBUTES
                                        };
                                        try {
                                            Files.copy(Paths.get("./src/view/img/reward/wait.jpg"), Paths.get("./src/view/img/reward/" + reward.getId()),options);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        listViewReward.getItems().add(indexUpdated, RowMolbyReward.createRow(update, burgerExpand));
                                    } else {
                                        Map.Entry<Integer,String> entry = custom.getLevelListView(levelArea).entrySet().iterator().next();
                                        entity.Reward create = new entity.Reward(nameText.getText(), Integer.parseInt(priceText.getText()), Integer.parseInt(availableText.getText()), descriptionTextArea.getText(), entry.getKey(), entry.getValue(), ((Image)((ImageViewMolby)gridDialog.getChild(0)).getImage()).getUrl());
                                        create.setId(RewardDao.create(create));
                                        CopyOption[] options = new CopyOption[]{
                                                StandardCopyOption.REPLACE_EXISTING,
                                                StandardCopyOption.COPY_ATTRIBUTES
                                        };
                                        try {
                                            Files.copy(Paths.get("./src/view/img/reward/wait.jpg"), Paths.get("./src/view/img/reward/" + create.getId() + ".jpg"),options);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        listViewReward.getItems().add(RowMolbyReward.createRow(create, burgerExpand));
                                    }
                                    listViewReward.refresh();
                                    dialog.close();
                                } else {
                                    nameText.validate();
                                    descriptionTextArea.validate();
                                    priceText.validate();
                                    availableText.validate();
                                    if(levelArea.getSelectionModel().getSelectedItem() != null){
                                        //NOTIFICATION ERROR
                                    }
                                }
                            }
                        }
                ));
        openDialog(gridPaneMolby);
    }


    public void createDialogDelete(entity.Reward reward){
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
                        RewardDao.remove(reward);
                        for(Object pane : listViewReward.getItems()){
                            if(reward.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneReward",""))){
                                listViewReward.getItems().remove(pane);
                                break;
                            }
                        }
                        listViewReward.refresh();
                        closeDialog();
                    }
                })).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby("Do you really want delete this reward: " + reward.getLabel() + " ?" , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }




}

