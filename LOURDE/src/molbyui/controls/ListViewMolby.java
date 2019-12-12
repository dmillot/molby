package molbyui.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.*;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.SizeConverter;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

import java.util.*;

@SuppressWarnings("ALL")
public class ListViewMolby<T> extends ListView<T> {

    private final ObjectProperty<Integer> depthProperty = new SimpleObjectProperty(0);
    private final ReadOnlyDoubleWrapper currentVerticalGapProperty = new ReadOnlyDoubleWrapper();
    private final BooleanProperty showTooltip = new SimpleBooleanProperty(false);
    /** @deprecated */
    @Deprecated
    private ObjectProperty<Node> groupnode = new SimpleObjectProperty(new Label("GROUP"));
    /** @deprecated */
    @Deprecated
    private ReadOnlyObjectWrapper<Integer> overAllIndexProperty = new ReadOnlyObjectWrapper(-1);
    /** @deprecated */
    @Deprecated
    private ObjectProperty<ObservableList<ListViewMolby<?>>> sublistsProperty = new SimpleObjectProperty(FXCollections.observableArrayList());
    /** @deprecated */
    @Deprecated
    private LinkedHashMap<Integer, ListViewMolby<?>> sublistsIndices = new LinkedHashMap();
    private static final String DEFAULT_STYLE_CLASS = "jfx-list-view";
    private boolean allowClear = true;
    private final StyleableDoubleProperty verticalGap;
    private final StyleableBooleanProperty expanded;
    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;


    public void setParam(Boolean expanded, Double gap, Integer depth){
        this.setExpanded(expanded);
        this.setVerticalGap(gap);
        this.depthProperty().set(depth);
    }


    public ListViewMolby() {
        this.verticalGap = new SimpleStyleableDoubleProperty(StyleableProperties.VERTICAL_GAP, this, "verticalGap", 0.0D);
        this.expanded = new SimpleStyleableBooleanProperty(StyleableProperties.EXPANDED, this, "expanded", false);
        this.setCellFactory((listView) -> new ListCellMolby());
        this.initialize();
    }

    protected Skin<?> createDefaultSkin() {
        return new ListViewMolbySkin<>(this);
    }

    public ObjectProperty<Integer> depthProperty() {
        return this.depthProperty;
    }

    public int getDepth() {
        return this.depthProperty.get();
    }

    public void setDepth(int depth) {
        this.depthProperty.set(depth);
    }

    ReadOnlyDoubleProperty currentVerticalGapProperty() {
        return this.currentVerticalGapProperty.getReadOnlyProperty();
    }

    private void expand() {
        this.currentVerticalGapProperty.set(this.verticalGap.get());
    }

    private void collapse() {
        this.currentVerticalGapProperty.set(0.0D);
    }

    public final BooleanProperty showTooltipProperty() {
        return this.showTooltip;
    }

    public final boolean isShowTooltip() {
        return this.showTooltipProperty().get();
    }

    public final void setShowTooltip(boolean showTooltip) {
        this.showTooltipProperty().set(showTooltip);
    }

    /** @deprecated */
    @Deprecated
    public Node getGroupnode() {
        return this.groupnode.get();
    }

    /** @deprecated */
    @Deprecated
    public void setGroupnode(Node node) {
        this.groupnode.set(node);
    }

    /** @deprecated */
    @Deprecated
    public ReadOnlyObjectProperty<Integer> overAllIndexProperty() {
        return this.overAllIndexProperty.getReadOnlyProperty();
    }

    /** @deprecated */
    @Deprecated
    void addSublist(ListViewMolby<?> subList, int index) {
        if (!this.sublistsProperty.get().contains(subList)) {
            ((ObservableList)this.sublistsProperty.get()).add(subList);
            this.sublistsIndices.put(index, subList);
            subList.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
                if (newVal.intValue() != -1) {
                    this.updateOverAllSelectedIndex();
                }

            });
        }

    }

    private void updateOverAllSelectedIndex() {
        int preItemsSize;
        Map.Entry entry;
        if (this.getSelectionModel().getSelectedIndex() != -1) {
            int selectedIndex = this.getSelectionModel().getSelectedIndex();
            Iterator<Map.Entry<Integer, ListViewMolby<?>>> itr = this.sublistsIndices.entrySet().iterator();
            preItemsSize = 0;

            while(itr.hasNext()) {
                entry = itr.next();
                if ((Integer)entry.getKey() < selectedIndex) {
                    preItemsSize += ((ListViewMolby)entry.getValue()).getItems().size() - 1;
                }
            }

            this.overAllIndexProperty.set(selectedIndex + preItemsSize);
        } else {
            Iterator<Map.Entry<Integer, ListViewMolby<?>>> itr = this.sublistsIndices.entrySet().iterator();
            ArrayList selectedList = new ArrayList();

            while(itr.hasNext()) {
                entry = itr.next();
                if (((ListViewMolby)entry.getValue()).getSelectionModel().getSelectedIndex() != -1) {
                    selectedList.add(entry.getKey());
                }
            }

            if (selectedList.size() > 0) {
                itr = this.sublistsIndices.entrySet().iterator();
                preItemsSize = 0;

                while(itr.hasNext()) {
                    entry = itr.next();
                    if ((Integer)entry.getKey() < (Integer)selectedList.get(0)) {
                        preItemsSize += ((ListViewMolby)entry.getValue()).getItems().size() - 1;
                    }
                }

                this.overAllIndexProperty.set(preItemsSize + (Integer)selectedList.get(0) + ((ListViewMolby)this.sublistsIndices.get(selectedList.get(0))).getSelectionModel().getSelectedIndex());
            } else {
                this.overAllIndexProperty.set(-1);
            }
        }

    }

    private void initialize() {
        this.getStyleClass().add("jfx-list-view");
        this.expanded.addListener((o, oldVal, newVal) -> {
            if (newVal) {
                this.expand();
            } else {
                this.collapse();
            }

        });
        this.verticalGap.addListener((o, oldVal, newVal) -> {
            if (this.isExpanded()) {
                this.expand();
            } else {
                this.collapse();
            }

        });

        this.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.intValue() != -1) {
                this.updateOverAllSelectedIndex();
            }

        });
    }

    private void clearSelection(ListViewMolby<?> selectedList) {
        if (this.allowClear) {
            this.allowClear = false;
            if (this != selectedList) {
                this.getSelectionModel().clearSelection();
            }

            for(int i = 0; i < this.sublistsProperty.get().size(); ++i) {
                if (((ObservableList)this.sublistsProperty.get()).get(i) != selectedList) {
                    ((ListViewMolby)((ObservableList)this.sublistsProperty.get()).get(i)).getSelectionModel().clearSelection();
                }
            }

            this.allowClear = true;
        }

    }

    public void propagateMouseEventsToParent() {
        this.addEventHandler(MouseEvent.ANY, (e) -> {
            e.consume();
            this.getParent().fireEvent(e);
        });
    }

    public Double getVerticalGap() {
        return this.verticalGap == null ? 0.0D : this.verticalGap.get();
    }

    public StyleableDoubleProperty verticalGapProperty() {
        return this.verticalGap;
    }

    public void setVerticalGap(Double gap) {
        this.verticalGap.set(gap);
        this.refresh();
    }

    public Boolean isExpanded() {
        return this.expanded != null && this.expanded.get();
    }

    public StyleableBooleanProperty expandedProperty() {
        return this.expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded.set(expanded);
    }

    @SuppressWarnings("unchecked")
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if (this.STYLEABLES == null) {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList(Control.getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(ListView.getClassCssMetaData());
            this.STYLEABLES = Collections.unmodifiableList(styleables);
        }

        return this.STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return ListViewMolby.StyleableProperties.CHILD_STYLEABLES;
    }

    private static class StyleableProperties {
        private static final CssMetaData<ListViewMolby<?>, Number> VERTICAL_GAP = new CssMetaData<>("-jfx-vertical-gap", SizeConverter.getInstance(), 0) {
            public boolean isSettable(ListViewMolby<?> control) {
                return control.verticalGap == null || !control.verticalGap.isBound();
            }


            public StyleableDoubleProperty getStyleableProperty(ListViewMolby<?> control) {
                return control.verticalGapProperty();
            }
        };
        private static final CssMetaData<ListViewMolby<?>, Boolean> EXPANDED = new CssMetaData<>("-jfx-expanded", BooleanConverter.getInstance(), false) {
            public boolean isSettable(ListViewMolby<?> control) {
                return control.getHeight() == 0.0D && (control.expanded == null || !control.expanded.isBound());
            }

            public StyleableBooleanProperty getStyleableProperty(ListViewMolby<?> control) {
                return control.expandedProperty();
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        private StyleableProperties() {
        }

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList(Control.getClassCssMetaData());
            Collections.addAll(styleables, VERTICAL_GAP, EXPANDED);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
}
