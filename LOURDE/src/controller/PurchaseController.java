package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import entity.Purchase;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.PurchaseDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomPurchase;
import row.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;

@SuppressWarnings("ALL")
public class PurchaseController {

    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewPurchase;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorPurchase;

    @FXML
    private StackPane stackDialog;

    private CreateCustomPurchase custom;
    private JFXDialogLayout content;


    private Boolean expanded = false;
    private Boolean animationExpanded = false;
    private JFXDialog dialog;


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
                    timeLineAnimation(50.0, listViewPurchase.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewPurchase.refresh();
                    }
                });
            });
        }
    }


    public void initialize() throws ExecutionException, InterruptedException {
        InitializeDataListView();
        InitializeDialogs();
    }
    
    private void openDialog(GridPaneMolby gridPaneMolby){
        content.setBody(gridPaneMolby);
        dialog.setContent(content);
        dialog.show();
    }

    public void closeDialog(){
        listViewPurchase.getSelectionModel().clearAndSelect(-1);
        dialog.close();
    }
    
    private void InitializeDataListView() throws ExecutionException, InterruptedException {
        listViewPurchase.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<Purchase>> task = new Task<ArrayList<Purchase>>() {
            @Override
            public ArrayList<Purchase> call() {
                return PurchaseDao.getPurchases();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewPurchase.getItems().addAll(RowMolbyPurchase.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyPurchase.controller = this;
            loadingScreen.setVisible(false);
        });
    }

    private void InitializeDialogs(){
        custom = new CreateCustomPurchase();
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
        Double columnFirst = (expand) ? 225.0 : 150.0;
        Double columnFirstSecond = (expand) ? 370.0 : 250.0;
        Double columnFirstThird= (expand) ? 300.0 : 200.0;
        Double columnSecond = 100.0;
        Double columnThird = 75.0;

        AnchorPaneMolby anchorLoader = (AnchorPaneMolby) anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby) anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).prefWidthProperty(), columnFirstSecond).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).maxWidthProperty(), columnFirstSecond).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).prefWidthProperty(), columnFirstThird).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).maxWidthProperty(), columnFirstThird).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(4).prefWidthProperty(), columnFirst).play();
            timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(4).maxWidthProperty(), columnFirst).play();

            for (Object paneList : listViewPurchase.getItems()) {
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).prefWidthProperty(), columnFirstSecond).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(0)).maxWidthProperty(), columnFirstSecond).play();

                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).prefWidthProperty(), columnFirstThird).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).maxWidthProperty(), columnFirstThird).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(4)).prefWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(4)).maxWidthProperty(), columnFirst).play();

                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(1)).translateXProperty(), (expand) ? 120.0 : 0).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).translateXProperty(), (expand) ? 120.0 : 0).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(3)).translateXProperty(), (expand) ? 220.0 : 0).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(4)).translateXProperty(), (expand) ? 220.0 : 0).play();
                for(int i = 5; i < 9; i++){
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).translateXProperty(), (expand) ? 295.0 : 0).play();
                }
                timeLineAnimation(duree, ((RipplerMolby) ((PaneMolby) paneList).getChild(9)).translateXProperty(), (expand) ? 295.0 : 0).play();
                timeLineAnimation(duree, ((RipplerMolby) ((PaneMolby) paneList).getChild(10)).translateXProperty(), (expand) ? 295.0 : 0).play();
            }
            timeLineAnimation(duree, ((StackPane) anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby) anchorLoader.getChild(0)).setPrefWidth(valueAction);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setPrefWidth(columnFirstSecond);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(0).setMaxWidth(columnFirstSecond);

            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).setPrefWidth(columnFirstThird);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(2).setMaxWidth(columnFirstThird);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(4).setPrefWidth( columnFirst);
            ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(4).setMaxWidth(columnFirst);

            for (Object paneList : listViewPurchase.getItems()) {
                ((Label) ((PaneMolby) paneList).getChild(0)).setPrefWidth(columnFirstSecond);
                ((Label) ((PaneMolby) paneList).getChild(0)).setMaxWidth(columnFirstSecond);

                ((Label) ((PaneMolby) paneList).getChild(2)).setPrefWidth(columnFirstThird);
                ((Label) ((PaneMolby) paneList).getChild(2)).setMaxWidth(columnFirstThird);
                ((Label) ((PaneMolby) paneList).getChild(4)).setPrefWidth(columnFirst);
                ((Label) ((PaneMolby) paneList).getChild(4)).setMaxWidth(columnFirst);

                ((Label) ((PaneMolby) paneList).getChild(1)).setTranslateX((expand) ? 120.0 : 0);
                ((Label) ((PaneMolby) paneList).getChild(2)).setTranslateX((expand) ? 120.0 : 0);
                ((Label) ((PaneMolby) paneList).getChild(3)).setTranslateX((expand) ? 220.0 : 0);
                ((Label) ((PaneMolby) paneList).getChild(4)).setTranslateX((expand) ? 220.0 : 0);
                for(int i = 5; i < 9; i++){
                    ((Label) ((PaneMolby) paneList).getChild(i)).setTranslateX((expand) ? 295.0 : 0);
                }
                ((RipplerMolby) ((PaneMolby) paneList).getChild(9)).setTranslateX((expand) ? 295.0 : 0);
                ((RipplerMolby) ((PaneMolby) paneList).getChild(10)).setTranslateX((expand) ? 295.0 : 0);
            }
            ((StackPane) anchorLoader.getChild(1)).setPrefWidth(valueAction);
        }
    }


    public void createDialogValid(Purchase purchase){
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
                custom.createButtonConfirm(0, 0, HPos.LEFT, VPos.CENTER, font, 100.0, 250.0, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Integer indexPurchaseUpdated = null;
                                for(Object pane : listViewPurchase.getItems()){
                                    if(purchase.getUserId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("panePurchase","").split("/")[0]) && purchase.getRewardId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("panePurchase","").split("/")[1])){
                                        indexPurchaseUpdated = listViewPurchase.getItems().indexOf(pane);
                                        listViewPurchase.getItems().remove(pane);
                                        break;
                                    }
                                }
                                purchase.setValidationDate(Timestamp.valueOf(LocalDateTime.now()));
                                PurchaseDao.updatePurchase(purchase);
                                listViewPurchase.getItems().add(indexPurchaseUpdated, RowMolbyPurchase.createRow(purchase, burgerExpand));
                                listViewPurchase.refresh();
                                dialog.close();
                            }
                        }
                )).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby(purchase.getUserName() + " " + purchase.getUserFirstName() + " want to buy this reward: " + purchase.getRewardName() + "." , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }


    public void createDialogReject(Purchase purchase){
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
                custom.createButtonReject(0, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                for(Object pane : listViewPurchase.getItems()){
                                    if(purchase.getUserId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("panePurchase","").split("/")[0]) && purchase.getRewardId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("panePurchase","").split("/")[1])){
                                        listViewPurchase.getItems().remove(pane);
                                        break;
                                    }
                                }
                                PurchaseDao.deletePurchase(purchase);
                                listViewPurchase.refresh();
                                dialog.close();
                            }
                        }
                )).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 100.0, 250.0)
        );

        gridDialog.addChildChaining(
                new LabelMolby(purchase.getUserName() + " " + purchase.getUserFirstName() + " can't by this reward: " + purchase.getRewardName() + "." , fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 0)
        ).addChildChaining(gridButton);
        openDialog(gridDialog);
    }

}
