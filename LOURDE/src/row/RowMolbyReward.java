package row;

import controller.RewardController;
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
import static row.RowMolby.pictureRow;

public class RowMolbyReward {
    
    public static RewardController controller;

    public static ArrayList<PaneMolby> createRows(ArrayList<entity.Reward> rewards, Boolean expand) {
        ArrayList<PaneMolby> paneMolbies = new ArrayList<>();
        for (entity.Reward reward : rewards) {
            PaneMolby paneRow = new PaneMolby();
            paneRow.setId("paneReward" + reward.getId());
            paneRow.addChild(pictureRow("reward",String.valueOf(reward.getId())));
            paneRow.addChilds(labelStylizing(paneRow, reward, expand));
            paneRow.addChilds(actionButtonRow(paneRow, reward, expand));
            paneMolbies.add(paneRow);
        }
        return paneMolbies;
    }

    public static PaneMolby createRow(entity.Reward reward, Boolean expand) {
        PaneMolby paneRow = new PaneMolby();
        paneRow.setId("paneReward" + reward.getId());
        paneRow.addChild(pictureRow("reward",String.valueOf(reward.getId())));
        paneRow.addChilds(labelStylizing(paneRow, reward, expand));
        paneRow.addChilds(actionButtonRow(paneRow, reward, expand));
        return paneRow;
    }


    public static List<LabelMolby> labelStylizing(PaneMolby paneRow, entity.Reward reward, Boolean expand){
        Color colorListView = Color.rgb(214,216,218);
        Font fontListView = Font.font("Segoe UI Light", 30);
        Double maxHeight = 100.0;
        Effect dropShadow = dropShadowRow();
        Double columnGrid = (expand) ? 805.0 : 510.0;
        return new ArrayList<>(asList(
                new LabelMolby(reward.getLabel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 126.0, 180.0),
                new LabelMolby(reward.getDescription(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 326.0, columnGrid),
                new LabelMolby(reward.getLevel(), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 856.0, 125.0, expand),
                new LabelMolby(String.valueOf(reward.getCostXp()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 1001.0, 125.0, expand),
                new LabelMolby(String.valueOf(reward.getNbAvailable()), colorListView, fontListView, true, maxHeight, paneRow, dropShadow, 1146.0, 80.0, expand)
        ));

    }

    public static List<RipplerMolby> actionButtonRow(PaneMolby paneRow, entity.Reward reward, Boolean expand) {
        return new ArrayList<>(asList(
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/delete.png", false, true, 35.0, dropShadowIconRow()), 1225.0, mouseEvent -> controller.createDialogDelete(reward), expand),
                buttonActionRow(paneRow, new ImageViewMolby("./src/view/img/edit.png", false, true, 35.0, dropShadowIconRow()), 1300.0, mouseEvent -> controller.createDialogAddEdit(reward), expand)
        ));
    }

}


