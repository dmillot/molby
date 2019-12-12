package row;

import controller.YearGroupController;
import entity.YearGroup;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import molbyui.controls.ImageViewMolby;
import molbyui.controls.LabelMolby;
import molbyui.controls.PaneMolby;
import molbyui.controls.RipplerMolby;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static molbyui.controls.DropShadowMolby.dropShadowRow;
import static row.RowMolby.buttonActionRow;

public class RowMolbyYearGroup {

    public static YearGroupController controller;

    public static ArrayList<PaneMolby> createRows(ArrayList<YearGroup> yearGroups, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (YearGroup yearGroup : yearGroups) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneYearGroup" + yearGroup.getId());
            paneRow.addChilds(labelStylizing(paneRow, yearGroup, expand));
            paneRow.addChilds(actionButtonRow(paneRow, yearGroup, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(YearGroup yearGroup, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("paneYearGroup" + yearGroup.getId());
        paneRow.addChilds(labelStylizing(paneRow, yearGroup, expand));
        paneRow.addChilds(actionButtonRow(paneRow, yearGroup, expand));
        return paneRow;
    }


    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, YearGroup yearGroup, Boolean expand) {
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Font fontDate= Font.font("Segoe UI Light", 22);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnFirst = (expand) ? 650.0 : 500.0;
        Double columnSecond = (expand) ? 325.0 : 275.0;
        Double columnThird = (expand) ? 170.0 : 125.0;
        String [] dateStart = String.valueOf(yearGroup.getDateStart()).split("-");
        String [] dateEnd = String.valueOf(yearGroup.getDateEnd()).split("-");
        return new ArrayList<>(asList(
                new LabelMolby(yearGroup.getLabel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 5.0, columnFirst),
                new LabelMolby(dateStart[2] + "/" + dateStart[1] + "/" + dateStart[0], colorListView, fontDate, true, maxHeight, paneRow, dropShadow, 507.0, columnSecond),
                new LabelMolby(dateEnd[2] + "/" + dateEnd[1] + "/" + dateEnd[0], colorListView, fontDate, true, maxHeight, paneRow, dropShadow, 802.0, columnSecond),
                new LabelMolby(String.valueOf(yearGroup.getUsers().size()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 1105.0, columnThird)
        ));
    }

    public static List<RipplerMolby> actionButtonRow(PaneMolby paneRow, YearGroup yearGroup, Boolean expand) {
        return new ArrayList<>(asList(
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/delete.png", false, true, 35.0, dropShadowIconRow()), 1220.0, mouseEvent -> controller.createDialogDelete(yearGroup), expand),
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/edit.png", false, true, 35.0, dropShadowIconRow()), 1295.0, mouseEvent -> controller.createDialogAddEdit(yearGroup), expand)
        ));
    }
}


