package row;

import controller.GroupController;
import entity.Group;
import entity.User;
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

public class RowMolbyGroup {
    
    public static GroupController controller;

    public static ArrayList<PaneMolby> createRows(ArrayList<Group> groups, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (Group group : groups) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneGroup" + group.getId());
            paneRow.addChilds(labelStylizing(paneRow, group, expand));
            paneRow.addChilds(actionButtonRow(paneRow, group, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(Group group, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("paneGroup" + group.getId());
        paneRow.addChilds(labelStylizing(paneRow, group, expand));
        paneRow.addChilds(actionButtonRow(paneRow, group, expand));
        return paneRow;
    }


    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, Group group, Boolean expand) {
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Font fontDate= Font.font("Segoe UI Light", 21);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnFirst = (expand) ? 300.0 : 250.0;
        Double columnSecond = (expand) ? 132.0 : 100.0;
        Double columnThird = (expand) ? 133.0 : 100.0;
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        String leader = "EMPTY";
        for(User user : group.getUsers()){
            if(user.isLeader()){
                leader = user.getName() + " " + user.getFirstName();
            }
        }
        return new ArrayList<>(asList(
                new LabelMolby(group.getLabel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 5.0, columnFirst),
                new LabelMolby(String.valueOf(group.getAvailableXp()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 254.0, columnThird),
                new LabelMolby(String.valueOf(group.getPointsToGive()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 374.0, columnThird),
                new LabelMolby(String.valueOf(group.getTotalXp()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 494.0, columnThird),
                new LabelMolby(date.format(group.getDateStart()), colorListView, fontDate, true, maxHeight, paneRow, dropShadow, 614.0, columnSecond),
                new LabelMolby(date.format(group.getDateEnd()), colorListView, fontDate, true, maxHeight, paneRow, dropShadow, 734.0, columnSecond),
                new LabelMolby(String.valueOf(group.getUsers().size()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 865.0, columnSecond),
                new LabelMolby(leader, colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 949.0, columnFirst)
        ));
    }

    public static List<RipplerMolby> actionButtonRow(PaneMolby paneRow, entity.Group group, Boolean expand) {
        return new ArrayList<>(asList(
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/delete.png", false, true, 35.0, dropShadowIconRow()), 1219.0, mouseEvent -> controller.createDialogDelete(group), expand),
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/edit.png", false, true, 35.0, dropShadowIconRow()), 1294.0, mouseEvent -> controller.createDialogAddEdit(group), expand)
        ));
    }
}


