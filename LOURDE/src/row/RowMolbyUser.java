package row;

import controller.GroupController;
import controller.UserController;
import entity.Group;
import entity.User;
import javafx.scene.Cursor;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import molbyui.controls.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static molbyui.controls.DropShadowMolby.dropShadowRow;
import static row.RowMolby.pictureRow;

@SuppressWarnings({"unchecked", "SuspiciousNameCombination"})
public class RowMolbyUser {
    
    public static UserController controller;
    public static GroupController controllerBis;

    public static ArrayList<PaneMolby> createRows(ArrayList<User> users, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (User user : users) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneUser" + user.getId());
            paneRow.addChild(pictureRow("user",String.valueOf(user.getId())));
            paneRow.addChilds(labelStylizing(paneRow, user, expand));
            paneRow.addChild(actionButtonRow(paneRow, user, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(User user, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("paneUser" + user.getId());
        paneRow.addChild(pictureRow("user",String.valueOf(user.getId())));
        paneRow.addChilds(labelStylizing(paneRow, user, expand));
        paneRow.addChild(actionButtonRow(paneRow, user, expand));
        return paneRow;
    }

    public static ArrayList<PaneMolby> createLittleRows(ArrayList<User> users, String id) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 15);
        Effect dropShadow = dropShadowRow();
        for (User user : users) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId(id + user.getId());
            paneRow.addChild(new LabelMolby(user.getName() + " " + user.getFirstName(), colorListView,fontListView, true, 50.0, paneRow, dropShadow, 5.0, 200.0));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static ArrayList<PaneMolby> createLittleRowsLeader(ArrayList<User> users, String id, Group group) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 15);
        Effect dropShadow = dropShadowRow();
        for (User user : users) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId(id + user.getId());
            paneRow.addChild(new LabelMolby(user.getName() + " " + user.getFirstName(), colorListView,fontListView, true, 50.0, paneRow, dropShadow, 5.0, 150.0));
           String leader = (user.isLeader()) ? "star" : "unstar";
            paneRow.addChild(buttonActionRow("button-leader", new ImageViewMolby("./src/view/img/"+ leader +".png", false, true, 35.0, dropShadowIconRow()) , 50.0, 175.0, paneRow, group));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }


    public static ButtonMolby buttonActionRow(String style, ImageViewMolby imageViewRowIcon, Double width, Double translateX, PaneMolby paneMolby, Group group){
        ButtonMolby button= new ButtonMolby(style, imageViewRowIcon, Cursor.HAND, width, width);
        button.setOnMouseClicked(GroupController.switchButton(button, paneMolby, group));
        button.setTranslateX(translateX);
        return button;
    }

    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, User user, Boolean expand){
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnFirst = (expand) ? 250.0 : 200.0;
        Double columnLast = (expand) ? 240.0 : 175.0;
        return new ArrayList<>(asList(
                new LabelMolby(user.getName(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 125.0, columnFirst),
                new LabelMolby(user.getFirstName(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 345.0, columnFirst, expand, 50.0),
                new LabelMolby(String.valueOf(user.getExperience()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 565.0, 125.0, expand, 100.0),
                new LabelMolby(user.getActualLevel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 715.0, columnLast, expand, 100.0),
                new LabelMolby((user.getGroupName() != null) ? user.getGroupName() : "EMPTY", colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 905.0, columnLast, expand, 165.0),
                new LabelMolby((user.getPromoName() != null) ? user.getPromoName() : "EMPTY", colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 1100.0, columnLast, expand, 230.0)
        ));

    }

    public static RipplerMolby actionButtonRow(PaneMolby paneRow, User user, Boolean expand) {
        return RowMolby.buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/edit.png", false, true, 35.0, dropShadowIconRow()), 1325.0, mouseEvent -> controller.createDialogAddEdit(user), expand);
    }

}


