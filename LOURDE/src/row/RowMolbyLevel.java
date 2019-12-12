package row;

import controller.LevelController;
import entity.Level;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import molbyui.controls.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static molbyui.controls.DropShadowMolby.dropShadowRow;
import static row.RowMolby.buttonActionRow;
import static row.RowMolby.pictureRow;

public class RowMolbyLevel {

    public static LevelController controller;

    public static ArrayList<PaneMolby> createRows(ArrayList<Level> levels, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (Level level : levels) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneLevel" + level.getId());
            paneRow.addChild(pictureRow("level",String.valueOf(level.getId())));
            paneRow.addChilds(labelStylizing(paneRow, level, expand));
            paneRow.addChilds(actionButtonRow(paneRow, level, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static ArrayList<PaneMolby> createLittleRows(ArrayList<Level> levels) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 15);
        Effect dropShadow = dropShadowRow();
        for (Level level : levels) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneLittleLevel" + level.getId());
            paneRow.addChild(new LabelMolby(level.getLabel(), colorListView,fontListView, true, 50.0, paneRow, dropShadow, 5.0, 400.0));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(Level level, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("paneLevel" + level.getId());
        paneRow.addChild(pictureRow("level",String.valueOf(level.getId())));
        paneRow.addChilds(labelStylizing(paneRow, level, expand));
        paneRow.addChilds(actionButtonRow(paneRow, level, expand));
        return paneRow;
    }



    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, Level level, Boolean expand){
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnGrid = (expand) ? 885.0 : 590.0;
        return new ArrayList<>(asList(
                new LabelMolby(level.getLabel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 126.0, 180.0),
                new LabelMolby(level.getDescription(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 326.0, columnGrid),
                new LabelMolby(String.valueOf(level.getCostXp()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 936.0, 125.0, expand),
                new LabelMolby(String.valueOf(level.getRequiredXp()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 1081.0, 125.0, expand)
        ));

    }

    public static List<RipplerMolby> actionButtonRow(PaneMolby paneRow, Level level, Boolean expand) {
        return new ArrayList<>(asList(
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/delete.png", false, true, 35.0, dropShadowIconRow()), 1215.0, mouseEvent -> controller.createDialogDelete(level), expand),
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/edit.png", false, true, 35.0, dropShadowIconRow()), 1290.0, mouseEvent -> controller.createDialogAddEdit(level), expand)
        ));
    }


}
