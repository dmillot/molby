package row;

import controller.PurchaseController;
import entity.Purchase;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import molbyui.controls.ImageViewMolby;
import molbyui.controls.LabelMolby;
import molbyui.controls.PaneMolby;
import molbyui.controls.RipplerMolby;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static molbyui.controls.DropShadowMolby.dropShadowRow;
import static row.RowMolby.buttonActionRow;

public class RowMolbyPurchase {

    public static PurchaseController controller;

    public static ArrayList<PaneMolby> createRows(ArrayList<Purchase> purchases, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (Purchase purchase : purchases) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("panePurchase" + purchase.getUserId() + "/" + purchase.getRewardId());
            paneRow.addChilds(labelStylizing(paneRow, purchase, expand));
            paneRow.addChilds(actionButtonRow(paneRow, purchase, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(Purchase purchase, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("panePurchase" + purchase.getUserId() + "/" + purchase.getRewardId());
        paneRow.addChilds(labelStylizing(paneRow, purchase, expand));
        paneRow.addChilds(actionButtonRow(paneRow, purchase, expand));
        return paneRow;
    }


    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, Purchase purchase, Boolean expand) {
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Font fontLabel= Font.font("Segoe UI Light", 22);
        Font fontDateTime= Font.font("Segoe UI Light", 18);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnFirst = (expand) ? 225.0 : 150.0;
        Double columnFirstSecond = (expand) ? 370.0 : 250.0;
        Double columnFirstThird = (expand) ? 300.0 : 200.0;
        Double columnSecond = 100.0;
        Double columnThird = 75.0;

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        return new ArrayList<>(asList(
                new LabelMolby(purchase.getUserName() + " " + purchase.getUserFirstName(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 5.0, columnFirstSecond),
                new LabelMolby(String.valueOf(purchase.getUserPoints()), colorListView, fontLabel, true, maxHeight, paneRow, dropShadow, 255.0, columnThird, expand, 120.0),
                new LabelMolby(purchase.getRewardName(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 350.0, columnFirstThird , expand, 220.0),
                new LabelMolby(String.valueOf(purchase.getRewardPrice()), colorListView, fontLabel, true, maxHeight, paneRow, dropShadow, 570.0, columnThird, expand,220.0),
                new LabelMolby(purchase.getLevelName(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 665.0, columnFirst , expand, 295.0),
                new LabelMolby(String.valueOf(purchase.getRewardRemaining()), colorListView, fontLabel, true, maxHeight, paneRow, dropShadow, 865.0, columnThird, expand, 295.0),
                new LabelMolby( (purchase.getValidationDate() != null) ? format.format(purchase.getValidationDate()) : "NOT VALIDATE", colorListView, fontDateTime, true, maxHeight, paneRow, dropShadow, 930.0, columnSecond, expand, 295.0),
                new LabelMolby( (purchase.getUseDate() != null) ? format.format(purchase.getUseDate()) : "NOT USE", colorListView, fontDateTime, true, maxHeight, paneRow, dropShadow, 1050.0, columnSecond, expand, 295.0),
                new LabelMolby(format.format(purchase.getRequestDate()), colorListView, fontDateTime, true, maxHeight, paneRow, dropShadow, 1170.0, columnSecond, expand, 295.0)
        ));
    }

    public static List<RipplerMolby> actionButtonRow(PaneMolby paneRow, Purchase purchase, Boolean expand) {
        return new ArrayList<>(asList(
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/valid.png", false, true, 35.0, dropShadowIconRow()), 1280.0, mouseEvent -> controller.createDialogValid(purchase), expand, 65.0),
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/close.png", false, true, 30.0, dropShadowIconRow()), 1340.0, mouseEvent -> controller.createDialogReject(purchase), expand, 65.0)
        ));
    }
}


