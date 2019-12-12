package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.Group;
import entity.User;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;
import model.GroupDao;
import model.UserDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomGroup;
import row.*;

import java.text.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static row.RowMolby.buttonActionRow;

@SuppressWarnings("ALL")
public class GroupController {

    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewGroup;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorGroup;

    @FXML
    private StackPane stackDialog;

    private CreateCustomGroup custom;
    private JFXDialogLayout content;

    private Boolean expanded = false;
    private Boolean animationExpanded = false;
    private JFXDialog dialog;

    private static ListViewMolby userGroupArea;
    private ListViewMolby userAvailableArea;

    public Boolean burgerExpand = false;

    private ArrayList<User> userAvailable;


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
                    timeLineAnimation(50.0, listViewGroup.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewGroup.refresh();
                    }
                });
            });
        }
    }


    public void initialize() throws ExecutionException, InterruptedException {
        InitializeDataListView();
        InitializeDialogs();
        InitializeButtonAdd();
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

    private void openDialog(GridPaneMolby gridPaneMolby){
        content.setBody(gridPaneMolby);
        dialog.setContent(content);
        dialog.show();
    }

    public void closeDialog(){
        listViewGroup.getSelectionModel().clearAndSelect(-1);
        dialog.close();
    }
    
    private void InitializeDataListView() throws ExecutionException, InterruptedException {
        listViewGroup.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<Group>> task = new Task<ArrayList<Group>>() {
            @Override
            public ArrayList<Group> call() {
                return GroupDao.getGroups();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewGroup.getItems().addAll(RowMolbyGroup.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyGroup.controller = this;
            RowMolbyUser.controllerBis = this;
            loadingScreen.setVisible(false);
        });
    }


    private void InitializeDialogs(){
        custom = new CreateCustomGroup();
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
        Double columnFirst = (expand) ? 300.0 : 250.0;
        Double columnSecond = (expand) ? 134.0 : 100.0;
        Double columnThree = (expand) ? 100.0 : 75.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby) anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby) anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).prefWidthProperty(), columnFirst).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).maxWidthProperty(), columnFirst).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(7).prefWidthProperty(), columnFirst).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(7).maxWidthProperty(), columnFirst).play();
            for (int i = 1; i < 6; i++) {
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).prefWidthProperty(), columnSecond).play();
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).maxWidthProperty(), columnSecond).play();
            }
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(6).prefWidthProperty(), columnThree).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(6).maxWidthProperty(), columnThree).play();
            for (Object paneList : listViewGroup.getItems()) {
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).prefWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).maxWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(7)).prefWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(7)).maxWidthProperty(), columnFirst).play();

                for (int i = 1; i < 6; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).prefWidthProperty(), columnSecond).play();
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).maxWidthProperty(), columnSecond).play();
                }
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(6)).prefWidthProperty(), columnThree).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(6)).maxWidthProperty(), columnThree).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(1)).translateXProperty(), (expand) ? 50.0 : 0).play();
                Double positionXLast = 84.0;
                for (int i = 2; i < 7; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).translateXProperty(), (expand) ? positionXLast : 0).play();
                    positionXLast += 34.0;
                }
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(7)).translateXProperty(), (expand) ? 254.0 : 0).play();
                timeLineAnimation(duree, ((PaneMolby) paneList).getChild(8).translateXProperty(), (expand) ? 295.0 : 0).play();
                timeLineAnimation(duree, ((PaneMolby) paneList).getChild(9).translateXProperty(), (expand) ? 295.0 : 0).play();
            }
            timeLineAnimation(duree, ((StackPane) anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby) anchorLoader.getChild(0)).setPrefWidth(valueAction);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setPrefWidth(columnFirst);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setMaxWidth(columnFirst);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(6).setPrefWidth(columnThree);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(6).setMaxWidth(columnThree);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(7).setPrefWidth(columnFirst);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(7).setMaxWidth(columnFirst);
            for (int i = 1; i < 6; i++) {
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setPrefWidth(columnSecond);
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setMaxWidth(columnSecond);
            }
            for (Object paneList : listViewGroup.getItems()) {
                ((Label) ((PaneMolby) paneList).getChild(0)).setPrefWidth(columnFirst);
                ((Label) ((PaneMolby) paneList).getChild(0)).setMaxWidth(columnFirst);
                ((Label) ((PaneMolby) paneList).getChild(7)).setPrefWidth(columnFirst);
                ((Label) ((PaneMolby) paneList).getChild(7)).setMaxWidth(columnFirst);

                for (int i = 1; i < 6; i++) {
                    ((Label) ((PaneMolby) paneList).getChild(i)).setPrefWidth(columnSecond);
                    ((Label) ((PaneMolby) paneList).getChild(i)).setMaxWidth(columnSecond);
                }
                ((Label) ((PaneMolby) paneList).getChild(6)).setPrefWidth(columnThree);
                ((Label) ((PaneMolby) paneList).getChild(6)).setMaxWidth(columnThree);
                ((Label) ((PaneMolby) paneList).getChild(1)).setTranslateX((expand) ? 50.0 : 0);
                Double positionXLast = 84.0;
                for (int i = 2; i < 7; i++) {
                    ((Label) ((PaneMolby) paneList).getChild(i)).setTranslateX((expand) ? positionXLast : 0);
                    positionXLast += 34.0;
                }
                ((Label) ((PaneMolby) paneList).getChild(7)).setTranslateX((expand) ? 254.0 : 0);
                ((PaneMolby) paneList).getChild(8).setTranslateX((expand) ? 295.0 : 0);
                ((PaneMolby) paneList).getChild(9).setTranslateX((expand) ? 295.0 : 0);
            }
            ((StackPane) anchorLoader.getChild(1)).setPrefWidth(valueAction);
        }
    }


    public static EventHandler switchButton(ButtonMolby button, PaneMolby paneMolby, Group group){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
                button.setGraphic(new ImageViewMolby("./src/view/img/star.png", false, true, 35.0, dropShadowIconRow()));
                if(group != null) {
                    for (User user : group.getUsers()) {
                        if (user.getId() == Integer.parseInt(paneMolby.getId().replace("paneLittleUserGroup", ""))) {
                            user.setLeader(true);
                        } else {
                            user.setLeader(false);
                        }
                    }
                }
                for(Object paneItem : userGroupArea.getItems()){
                    if(!((PaneMolby)paneItem).getId().equals(paneMolby.getId())){
                        ((ButtonMolby)((PaneMolby)paneItem).getChild(1)).setGraphic(new ImageViewMolby("./src/view/img/unstar.png", false, true, 35.0, dropShadowIconRow()));
                    }
                }
            }
        };
    }

    private void checkAlreadyUser(){
        for(int i = 0; i < userGroupArea.getItems().size(); i++){
            for(int j = 0; j < userAvailableArea.getItems().size(); j++){
                if(Integer.parseInt(((PaneMolby)userAvailableArea.getItems().get(j)).getId().replace("paneLittleUserAvailable", "")) == Integer.parseInt(((PaneMolby)userGroupArea.getItems().get(i)).getId().replace("paneLittleUserGroup", ""))){
                    userAvailableArea.getItems().remove(j);
                    break;
                }
            }
        }
    }

    public void createDialogAddEdit(Group group){
        GridPaneMolby gridDialog = new GridPaneMolby("contentDialog", 700.0, 650.0, 6, 3, new HashMap<>(){{
            put(4,40.0);
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

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        String valueName = (group != null) ? group.getLabel() : null;
        Integer valueAvailble = (group != null) ? group.getAvailableXp() : null;
        Integer valuePointsToGive = (group != null) ? group.getPointsToGive() : null;
        LocalDate dateStart = (group != null) ?  group.getDateStart().toLocalDate() : today;
        LocalDate dateEnd = (group != null) ?  group.getDateEnd().toLocalDate() : tomorrow;

        String valueButton = (group != null) ? "UPDATE" : "ADD";
        ArrayList<User> users = (group != null) ? group.getUsers() : new ArrayList<>();

        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        TextFieldMolby nameText = custom.InitializeTextField(font, valueName, required, false, "jfx-text-field-second");
        TextFieldMolby availableXPText = custom.InitializeTextField(font, String.valueOf(valueAvailble), required, true, "jfx-text-field-second");
        TextFieldMolby pointGiveText = custom.InitializeTextField(font, String.valueOf(valuePointsToGive), required, true, "jfx-text-field-second");

        DatePickerMolby datePickerStart = new DatePickerMolby(dateStart, required, Paint.valueOf("#801430"));
        DatePickerMolby datePickerEnd = new DatePickerMolby(dateEnd, required, Paint.valueOf("#801430"));

        userAvailable = UserDao.getAvailableUser(dateStart, dateEnd);

        userGroupArea = custom.createListView(RowMolbyUser.createLittleRowsLeader(users, "paneLittleUserGroup", group), 200, 250);
        userAvailableArea = custom.createListView(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"), 200, 250);
        checkAlreadyUser();

        datePickerStart.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(
                                datePickerEnd.getValue().plusDays(-1)) || item.isBefore(LocalDate.now())
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #B34763;");
                        }
                    }
                };
            }
        });

        datePickerStart.valueProperty().addListener((ov, oldValue, newValue) -> {
            userGroupArea.getItems().clear();
            userAvailableArea.getItems().clear();
            userAvailable = UserDao.getAvailableUser(newValue, datePickerEnd.getValue());
            userGroupArea.getItems().addAll(RowMolbyUser.createLittleRowsLeader(users, "paneLittleUserGroup", group));
            for(User user: users){
                for(int i = 0; i < userAvailable.size(); i++){
                    if(userAvailable.get(i).getId() == user.getId()){
                        userAvailable.remove(userAvailable.get(i));
                    }
                }
            }
            System.out.print(userAvailable.size());
            userAvailableArea.getItems().addAll(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"));
            checkAlreadyUser();
            userGroupArea.refresh();
            userAvailableArea.refresh();
        });

        datePickerEnd.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(
                                datePickerStart.getValue().plusDays(1)) || item.isBefore(LocalDate.now())
                        ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #B34763;");
                        }
                    }
                };
            }
        });

        datePickerEnd.valueProperty().addListener((ov, oldValue, newValue) -> {
            userGroupArea.getItems().clear();
            userAvailableArea.getItems().clear();
            userAvailable = UserDao.getAvailableUser(datePickerStart.getValue(),newValue);
            userGroupArea.getItems().addAll(RowMolbyUser.createLittleRowsLeader(users, "paneLittleUserGroup", group));
            for(User user: users){
                for(int i = 0; i < userAvailable.size(); i++){
                    if(userAvailable.get(i).getId() == user.getId()){
                        userAvailable.remove(userAvailable.get(i));
                    }
                }
            }
            userAvailableArea.getItems().addAll(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"));
            checkAlreadyUser();
            userGroupArea.refresh();
            userAvailableArea.refresh();
        });


        GridPaneMolby gridPoints = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 1);
        gridPoints.addChildChaining(
                custom.createVBox(100.0,100.0, 0, 0, padding).addChildChaining(
                        new LabelMolby("Available XP:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        availableXPText
                )
        ).addChildChaining(
                custom.createVBox(100.0,100.0, 2, 0,padding).addChildChaining(
                        new LabelMolby("Points to give:",fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        pointGiveText
                )
        );

        GridPaneMolby gridDate = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 2);
        gridDate.addChildChaining(
                custom.createVBox(100.0,100.0, 0, 0, padding).addChildChaining(
                        new LabelMolby("Date start:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        datePickerStart
                )
        ).addChildChaining(
                custom.createVBox(100.0,100.0, 2, 0,padding).addChildChaining(
                        new LabelMolby("Date end:",fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        datePickerEnd
                )
        );

        GridPaneMolby gridUser = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 100.0);}}, 1, 3);
        gridUser.addChildChaining(
                custom.createVBox(250.0,100.0, 0, 0, padding).addChildChaining(
                        new LabelMolby("User's group:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        userGroupArea
                )
        ).addChildChaining(
                custom.createVBox(100.0,100.0, 1, 0, padding, 16.0, 76.0, 25.0).addChildChaining(
                    RowMolby.buttonActionRow("button-remove-xp", new ImageViewMolby("./src/view/img/arrow-right.png", false, true, 20.0, dropShadowIconRow()), new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(userGroupArea.getSelectionModel().getSelectedItem() != null){
                                PaneMolby paneMolby = (PaneMolby)userGroupArea.getSelectionModel().getSelectedItem();
                                paneMolby.setId("paneLittleUserAvailable" + paneMolby.getId().replace("paneLittleUserGroup", ""));
                                userGroupArea.getItems().remove(paneMolby);
                                paneMolby.getChildren().remove(1);
                                if(group != null) {
                                    for (User user : group.getUsers()) {
                                        if (Integer.parseInt(paneMolby.getId().replace("paneLittleUserAvailable", "")) == user.getId()) {
                                            group.getUsers().remove(user);
                                            break;
                                        }
                                    }
                                }
                                userAvailableArea.getItems().add(paneMolby);
                                userGroupArea.refresh();
                                userAvailableArea.refresh();
                            }

                        }
                    }, Paint.valueOf("#670017"), 50.0)
                ).addChildChaining(
                    RowMolby.buttonActionRow("button-add-xp", new ImageViewMolby("./src/view/img/arrow-left.png", false, true, 20.0, dropShadowIconRow()),  new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(userAvailableArea.getSelectionModel().getSelectedItem() != null){
                                PaneMolby paneMolby = (PaneMolby)userAvailableArea.getSelectionModel().getSelectedItem();
                                userAvailableArea.getItems().remove(paneMolby);
                                paneMolby.setId("paneLittleUserGroup" + paneMolby.getId().replace("paneLittleUserAvailable", ""));
                                paneMolby.addChild(RowMolbyUser.buttonActionRow("button-leader", new ImageViewMolby("./src/view/img/unstar.png", false, true, 35.0, dropShadowIconRow()) , 50.0, 175.0, paneMolby, group));
                                for(User user : userAvailable){
                                    if(group != null && user.getId() == (Integer.parseInt(paneMolby.getId().replace("paneLittleUserGroup", "")))){
                                        group.getUsers().add(user);
                                        break;
                                    }
                                }
                                userGroupArea.getItems().add(paneMolby);
                                userGroupArea.refresh();
                                userAvailableArea.refresh();
                            }
                        }
                    } ,Paint.valueOf("#003988") , 50.0)
                )
        ).addChildChaining(
                custom.createVBox(250.0,100.0, 2, 0,padding).addChildChaining(
                        new LabelMolby("User available:",fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        userAvailableArea
                )
        );


        GridPaneMolby gridButton = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 100.0);}}, 1, 5);
        gridButton.addChildChaining(
                custom.createButtonAddEdit(0, 0, HPos.CENTER, VPos.CENTER, font, 50.0, 250.0, valueButton, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (nameText.getText() != null && availableXPText.getText() != null && pointGiveText.getText() != null && userGroupArea.getItems().size() != 0 && !nameText.getText().equals("") && !availableXPText.getText().equals("") && !pointGiveText.getText().equals("")) {
                            if (group != null) {
                                Group updateGroup = new Group(group.getId(), nameText.getText(), group.getUsers(), Integer.parseInt(availableXPText.getText()), Integer.parseInt(pointGiveText.getText()), 0,  java.sql.Date.valueOf(datePickerStart.getValue()), java.sql.Date.valueOf(datePickerEnd.getValue()));
                                GroupDao.update(updateGroup);
                                Integer indexUpdated = null;
                                for(Object pane : listViewGroup.getItems()){
                                    if(group.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneGroup",""))){
                                        indexUpdated = listViewGroup.getItems().indexOf(pane);
                                        listViewGroup.getItems().remove(pane);
                                        break;
                                    }
                                }
                                listViewGroup.getItems().add(indexUpdated, RowMolbyGroup.createRow(updateGroup, burgerExpand));
                            } else {
                                ArrayList<User> users = new ArrayList<>();
                                for(Object paneMolby : userGroupArea.getItems()){
                                    Boolean leader = (((ImageViewMolby)((ButtonMolby)((PaneMolby)paneMolby).getChild(1)).getGraphic()).getImage().getUrl().contains("unstar")) ? false : true;
                                    String[] nameFirst = ((LabelMolby)((PaneMolby)paneMolby).getChild(0)).getText().split(" ");
                                    users.add(new User(Integer.parseInt(((PaneMolby)paneMolby).getId().replace("paneLittleUserGroup", "")), leader, nameFirst[0], nameFirst[1]));
                                }
                                Group newGroup = new Group(nameText.getText(), users, Integer.parseInt(availableXPText.getText()), Integer.parseInt(pointGiveText.getText()), 0,  java.sql.Date.valueOf(datePickerStart.getValue()), java.sql.Date.valueOf(datePickerEnd.getValue()));
                                GroupDao.create(newGroup);
                                listViewGroup.getItems().add(RowMolbyGroup.createRow(newGroup, burgerExpand));
                            }
                            listViewGroup.refresh();
                            dialog.close();
                        } else {
                            nameText.validate();
                            availableXPText.validate();
                            pointGiveText.validate();
                            if (userGroupArea.getItems().size() == 0) {
                                userGroupArea.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
                            }
                        }
                    }
                })).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 50.0, 250.0)
        );


        GridPaneMolby gridPaneMolby = gridDialog.addChildChaining(buttonClose).addChildChaining(
                custom.createVBox(100.0,100.0, 1, 0,padding).addChildChaining(
                        new LabelMolby("Name:", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
                ).addChildChaining(
                        nameText
                )
        ).addChildChaining(gridPoints).addChildChaining(gridDate).addChildChaining(gridUser).addChildChaining(gridButton);
        openDialog(gridPaneMolby);
    }


    public void createDialogDelete(Group group){
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
                        GroupDao.remove(group);
                        for(Object pane : listViewGroup.getItems()){
                            if(group.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneGroup",""))){
                                listViewGroup.getItems().remove(pane);
                                break;
                            }
                        }
                        listViewGroup.refresh();
                        closeDialog();
                    }
                })).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby("Delete this group: " + group.getLabel() + " ?" , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }

}
