package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.Group;
import entity.User;
import entity.YearGroup;
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
import model.UserDao;
import model.YearGroupDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomYearGroup;
import row.RowMolby;
import row.RowMolbyUser;
import row.RowMolbyYearGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static row.RowMolby.buttonActionRow;

@SuppressWarnings("ALL")
public class YearGroupController {

    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewYearGroup;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorYearGroup;

    @FXML
    private StackPane stackDialog;

    private CreateCustomYearGroup custom;
    private JFXDialogLayout content;

    private static ListViewMolby userGroupArea;
    private ListViewMolby userAvailableArea;

    private Boolean expanded = false;
    private Boolean animationExpanded = false;
    private JFXDialog dialog;


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
                    timeLineAnimation(50.0, listViewYearGroup.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewYearGroup.refresh();
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
        listViewYearGroup.getSelectionModel().clearAndSelect(-1);
        dialog.close();
    }
    
    private void InitializeDataListView() throws ExecutionException, InterruptedException {
        listViewYearGroup.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<YearGroup>> task = new Task<ArrayList<YearGroup>>() {
            @Override
            public ArrayList<YearGroup> call() {
                return YearGroupDao.getYearGroups();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewYearGroup.getItems().addAll(RowMolbyYearGroup.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyYearGroup.controller = this;
            loadingScreen.setVisible(false);
        });
    }

    private void InitializeDialogs(){
        custom = new CreateCustomYearGroup();
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
        Double columnFirst = (expand) ? 650.0 : 500.0;
        Double columnSecond = (expand) ? 325.0 : 275.0;
        Double columnThird = (expand) ? 170.0 : 125.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby) anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby) anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).prefWidthProperty(), columnFirst).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).maxWidthProperty(), columnFirst).play();
            for (int i = 1; i < 3; i++) {
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).prefWidthProperty(), columnSecond).play();
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).maxWidthProperty(), columnSecond).play();
            }
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(3).prefWidthProperty(), columnThird).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(3).maxWidthProperty(), columnThird).play();
            for (Object paneList : listViewYearGroup.getItems()) {
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).prefWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).maxWidthProperty(), columnFirst).play();

                for (int i = 1; i < 3; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).prefWidthProperty(), columnSecond).play();
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).maxWidthProperty(), columnSecond).play();
                }
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(3)).prefWidthProperty(), columnThird).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(3)).maxWidthProperty(), columnThird).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(1)).translateXProperty(), (expand) ? 150.0 : 0).play();
                Double positionXLast = 200.0;
                for (int i = 2; i < 4; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).translateXProperty(), (expand) ? positionXLast : 0).play();
                    positionXLast += 50.0;
                }
                timeLineAnimation(duree, ((PaneMolby) paneList).getChild(4).translateXProperty(), (expand) ? 295.0 : 0).play();
                timeLineAnimation(duree, ((PaneMolby) paneList).getChild(5).translateXProperty(), (expand) ? 295.0 : 0).play();
            }
            timeLineAnimation(duree, ((StackPane) anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby) anchorLoader.getChild(0)).setPrefWidth(valueAction);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setPrefWidth(columnFirst);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setMaxWidth(columnFirst);
            for (int i = 1; i < 3; i++) {
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setPrefWidth(columnSecond);
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setMaxWidth(columnSecond);
            }
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(3).setPrefWidth(columnThird);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(3).setMaxWidth(columnThird);
            for (Object paneList : listViewYearGroup.getItems()) {
                ((Label) ((PaneMolby) paneList).getChild(0)).setPrefWidth(columnFirst);
                ((Label) ((PaneMolby) paneList).getChild(0)).setMaxWidth(columnFirst);

                for (int i = 1; i < 3; i++) {
                   ((Label) ((PaneMolby) paneList).getChild(i)).setPrefWidth(columnSecond);
                    ((Label) ((PaneMolby) paneList).getChild(i)).setMaxWidth(columnSecond);
                }
                ((Label) ((PaneMolby) paneList).getChild(3)).setPrefWidth(columnThird);
                ((Label) ((PaneMolby) paneList).getChild(3)).setMaxWidth(columnThird);
                ((Label) ((PaneMolby) paneList).getChild(1)).setTranslateX((expand) ? 150.0 : 0);
                Double positionXLast = 200.0;
                for (int i = 2; i < 4; i++) {
                   ((Label) ((PaneMolby) paneList).getChild(i)).setTranslateX((expand) ? positionXLast : 0);
                    positionXLast += 50.0;
                }
                ((PaneMolby) paneList).getChild(4).setTranslateX((expand) ? 295.0 : 0);
                ((PaneMolby) paneList).getChild(5).setTranslateX((expand) ? 295.0 : 0);
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

    public void createDialogAddEdit(YearGroup yearGroup){
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

        String valueName = (yearGroup != null) ? yearGroup.getLabel() : null;
        LocalDate dateStart = (yearGroup != null) ?  yearGroup.getDateStart().toLocalDate() : today;
        LocalDate dateEnd = (yearGroup != null) ?  yearGroup.getDateEnd().toLocalDate() : tomorrow;

        String valueButton = (yearGroup != null) ? "UPDATE" : "ADD";
        ArrayList<User> users = (yearGroup != null) ? yearGroup.getUsers() : new ArrayList<>();

        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        TextFieldMolby nameText = custom.InitializeTextField(font, valueName, required, false, "jfx-text-field-second");

        DatePickerMolby datePickerStart = new DatePickerMolby(dateStart, required, Paint.valueOf("#801430"));
        DatePickerMolby datePickerEnd = new DatePickerMolby(dateEnd, required, Paint.valueOf("#801430"));

        userAvailable = UserDao.getWithoutYearGroup(dateStart, dateEnd);

        userGroupArea = custom.createListView(RowMolbyUser.createLittleRows(users, "paneLittleUserYearGroup"), 200, 250);
        userAvailableArea = custom.createListView(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"), 200, 250);

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
            userAvailable = UserDao.getWithoutYearGroup(newValue, datePickerEnd.getValue());
            userGroupArea.getItems().addAll(RowMolbyUser.createLittleRows(users, "paneLittleUserYearGroup"));
            for(User user: users){
                for(int i = 0; i < userAvailable.size(); i++){
                    if(userAvailable.get(i).getId() == user.getId()){
                        userAvailable.remove(userAvailable.get(i));
                    }
                }
            }
            userAvailableArea.getItems().addAll(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"));
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
            userAvailable = UserDao.getWithoutYearGroup(datePickerStart.getValue(), newValue);
            userGroupArea.getItems().addAll(RowMolbyUser.createLittleRows(users, "paneLittleUserYearGroup"));
            for(User user: users){
                for(int i = 0; i < userAvailable.size(); i++){
                    if(userAvailable.get(i).getId() == user.getId()){
                        userAvailable.remove(userAvailable.get(i));
                    }
                }
            }
            userAvailableArea.getItems().addAll(RowMolbyUser.createLittleRows(userAvailable, "paneLittleUserAvailable"));
            userGroupArea.refresh();
            userAvailableArea.refresh();
        });


        GridPaneMolby gridDate = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 1);
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

        GridPaneMolby gridUser = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 100.0);}}, 1, 2);
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
                                    paneMolby.setId("paneLittleUserAvailable" + paneMolby.getId().replace("paneLittleUserYearGroup", ""));
                                    userGroupArea.getItems().remove(paneMolby);
                                    if(yearGroup != null) {
                                        for (User user : yearGroup.getUsers()) {
                                            if (Integer.parseInt(paneMolby.getId().replace("paneLittleUserAvailable", "")) == user.getId()) {
                                                yearGroup.getUsers().remove(user);
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
                                    paneMolby.setId("paneLittleUserYearGroup" + paneMolby.getId().replace("paneLittleUserAvailable", ""));
                                    for(User user : userAvailable){
                                        if(yearGroup != null && user.getId() == (Integer.parseInt(paneMolby.getId().replace("paneLittleUserYearGroup", "")))){
                                            yearGroup.getUsers().add(user);
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
                        if (nameText.getText() != null &&  userGroupArea.getItems().size() != 0 && !nameText.getText().equals("")) {
                            if (yearGroup != null) {
                                YearGroup updateYearGroup = new YearGroup(yearGroup.getId(), nameText.getText(), yearGroup.getUsers(),  java.sql.Date.valueOf(datePickerStart.getValue()), java.sql.Date.valueOf(datePickerEnd.getValue()));
                                YearGroupDao.update(updateYearGroup);
                                Integer indexUpdated = null;
                                for(Object pane : listViewYearGroup.getItems()){
                                    if(yearGroup.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneYearGroup",""))){
                                        indexUpdated = listViewYearGroup.getItems().indexOf(pane);
                                        listViewYearGroup.getItems().remove(pane);
                                        break;
                                    }
                                }
                                listViewYearGroup.getItems().add(indexUpdated, RowMolbyYearGroup.createRow(updateYearGroup, burgerExpand));
                            } else {
                                ArrayList<User> users = new ArrayList<>();
                                for(Object paneMolby : userGroupArea.getItems()){
                                    String[] nameFirst = ((LabelMolby)((PaneMolby)paneMolby).getChild(0)).getText().split(" ");
                                    users.add(new User(Integer.parseInt(((PaneMolby)paneMolby).getId().replace("paneLittleUserYearGroup", "")), nameFirst[0], nameFirst[1]));
                                }
                                YearGroup newGroup = new YearGroup(nameText.getText(), users, java.sql.Date.valueOf(datePickerStart.getValue()), java.sql.Date.valueOf(datePickerEnd.getValue()));
                                YearGroupDao.create(newGroup);
                                listViewYearGroup.getItems().add(RowMolbyYearGroup.createRow(newGroup, burgerExpand));
                            }
                            listViewYearGroup.refresh();
                            dialog.close();
                        } else {
                            nameText.validate();
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
        ).addChildChaining(gridDate).addChildChaining(gridUser).addChildChaining(gridButton);
        openDialog(gridPaneMolby);
    }


    public void createDialogDelete(YearGroup yearGroup){
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
                        YearGroupDao.remove(yearGroup);
                        for(Object pane : listViewYearGroup.getItems()){
                            if(yearGroup.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneYearGroup",""))){
                                listViewYearGroup.getItems().remove(pane);
                                break;
                            }
                        }
                        listViewYearGroup.refresh();
                        closeDialog();
                    }
                })).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby("Delete this group: " + yearGroup.getLabel() + " ?" , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }

}
