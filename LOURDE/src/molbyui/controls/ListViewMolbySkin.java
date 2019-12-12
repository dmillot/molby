package molbyui.controls;

import com.jfoenix.effects.JFXDepthManager;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;


@SuppressWarnings({"rawtypes", "unchecked"})
public class ListViewMolbySkin<T> extends ListViewSkin<T> {
    private final VirtualFlow<ListCell<T>> flow = (VirtualFlow)this.getChildren().get(0);

    public ListViewMolbySkin(ListViewMolby<T> listView) {
        super(listView);
        JFXDepthManager.setDepth(this.flow, listView.depthProperty().get());
        listView.depthProperty().addListener((o, oldVal, newVal) -> JFXDepthManager.setDepth(this.flow, newVal));
    }

    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 200.0D;
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        int itemsCount = ((ListView)this.getSkinnable()).getItems().size();
        if (!this.getSkinnable().maxHeightProperty().isBound() && itemsCount > 0) {
            double fixedCellSize = this.getSkinnable().getFixedCellSize();
            double computedHeight = fixedCellSize != -1.0D ? fixedCellSize * (double)itemsCount + this.snapVerticalInsets() : this.estimateHeight();
            double height = super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
            if (height > computedHeight) {
                height = computedHeight;
            }

            return this.getSkinnable().getMaxHeight() > 0.0D && computedHeight > this.getSkinnable().getMaxHeight() ? this.getSkinnable().getMaxHeight() : height;
        } else {
            return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
        }
    }

    @SuppressWarnings("unchecked")
    private double estimateHeight() {
        double borderWidth = this.snapVerticalInsets();
        ListViewMolby<T> listview = (ListViewMolby)this.getSkinnable();
        double gap = listview.isExpanded() ? ((ListViewMolby)this.getSkinnable()).getVerticalGap() * (double)((ListView)this.getSkinnable()).getItems().size() : 0.0D;
        double cellsHeight = 0.0D;

        for(int i = 0; i < this.flow.getCellCount(); ++i) {
            ListCell<T> cell = this.flow.getCell(i);
            cellsHeight += cell.getHeight();
        }

        return cellsHeight + gap + borderWidth;
    }

    private double snapVerticalInsets() {
        return this.getSkinnable().snappedBottomInset() + this.getSkinnable().snappedTopInset();
    }
}
