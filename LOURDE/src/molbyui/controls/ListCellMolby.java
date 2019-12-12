

package molbyui.controls;

import com.jfoenix.utils.JFXNodeUtils;
import java.util.Iterator;
import java.util.Set;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

@SuppressWarnings({"deprecation", "DeprecatedIsStillUsed", "unchecked", "rawtypes"})
public class ListCellMolby<T> extends ListCell<T> {


    private final RipplerMolby cellRippler = new RipplerMolby(this)  {


        protected Node getMask() {
            Region clip = new Region();
            JFXNodeUtils.updateBackground(this.getBackground(), clip);
            double width = this.control.getLayoutBounds().getWidth();
            double height = this.control.getLayoutBounds().getHeight();
            clip.resize(width, height);
            return clip;
        }

        protected void positionControl(Node control) {
        }
    };
    protected Node cellContent;
    private Rectangle clip;
    private Timeline expandAnimation;
    private Timeline gapAnimation;
    private double animatedHeight = 0.0D;
    private boolean playExpandAnimation = false;
    private boolean selectionChanged = false;
    /** @deprecated */
    @Deprecated
    private BooleanProperty expandedProperty = new SimpleBooleanProperty(false);
    private static final String DEFAULT_STYLE_CLASS = "jfx-list-cell";


    public ListCellMolby() {
        this.initialize();
        this.initListeners();
    }

    private void initListeners() {
        this.listViewProperty().addListener((listObj, oldList, newList) -> {
            if (newList != null && this.getListView() instanceof ListViewMolby) {
                ((ListViewMolby)newList).currentVerticalGapProperty().addListener((o, oldVal, newVal) -> {
                    this.cellRippler.rippler.setClip(null);
                    if (newVal.doubleValue() != 0.0D) {
                        this.playExpandAnimation = true;
                        this.getListView().requestLayout();
                    } else {
                        double gap = this.clip.getY() * 2.0D;
                        this.gapAnimation = new Timeline(new KeyFrame(Duration.millis(240.0D), new KeyValue(this.translateYProperty(), -gap / 2.0D - gap * (double)this.getIndex(), Interpolator.EASE_BOTH)));
                        this.gapAnimation.play();
                        this.gapAnimation.setOnFinished((finish) -> {
                            this.requestLayout();
                            Platform.runLater(() -> this.getListView().requestLayout());
                        });
                    }

                });
                this.selectedProperty().addListener((o, oldVal, newVal) -> {
                    if (newVal) {
                        this.selectionChanged = true;
                    }

                });
            }

        });
    }

    protected void layoutChildren() {
        super.layoutChildren();
        this.cellRippler.resizeRelocate(0.0D, 0.0D, this.getWidth(), this.getHeight());
        double gap = this.getGap();
        if (this.clip == null) {
            this.clip = new Rectangle(0.0D, gap / 2.0D, this.getWidth(), this.getHeight() - gap);
            this.setClip(this.clip);
        } else {
            if (gap != 0.0D) {
                if (!this.playExpandAnimation && !this.selectionChanged) {
                    if (this.gapAnimation != null) {
                        this.gapAnimation.stop();
                    }

                    this.setTranslateY(0.0D);
                    this.clip.setY(gap / 2.0D);
                    this.clip.setHeight(this.getHeight() - gap);
                } else {
                    if (this.playExpandAnimation) {
                        this.setTranslateY(-gap / 2.0D + -gap * (double)this.getIndex());
                        this.clip.setY(gap / 2.0D);
                        this.clip.setHeight(this.getHeight() - gap);
                        this.gapAnimation = new Timeline(new KeyFrame(Duration.millis(240.0D), new KeyValue(this.translateYProperty(), 0, Interpolator.EASE_BOTH)));
                        this.playExpandAnimation = false;
                    } else {
                        this.clip.setY(0.0D);
                        this.clip.setHeight(this.getHeight());
                        this.gapAnimation = new Timeline(new KeyFrame(Duration.millis(240.0D), new KeyValue(this.clip.yProperty(), gap / 2.0D, Interpolator.EASE_BOTH), new KeyValue(this.clip.heightProperty(), this.getHeight() - gap, Interpolator.EASE_BOTH)));
                    }

                    this.playExpandAnimation = false;
                    this.selectionChanged = false;
                    this.gapAnimation.play();
                }
            } else {
                this.setTranslateY(0.0D);
                this.clip.setY(0.0D);
                this.clip.setHeight(this.getHeight());
            }

            this.clip.setX(0.0D);
            this.clip.setWidth(this.getWidth());
        }

        if (!this.getChildren().contains(this.cellRippler)) {
            this.makeChildrenTransparent();
            this.getChildren().add(0, this.cellRippler);
            this.cellRippler.rippler.clear();
        }



        if (this.getGraphic() != null && this.getGraphic().getStyleClass().contains("sublist-container")) {
            this.getStyleClass().add("sublist-item");
        } else {
            this.getStyleClass().remove("sublist-item");
        }

    }

    @SuppressWarnings({"LoopStatementThatDoesntLoop", "rawtypes"})
    protected void makeChildrenTransparent() {
        Iterator var1 = this.getChildren().iterator();

        while(true) {
            while(var1.hasNext()) {
                Node child = (Node)var1.next();
                if (child instanceof Label) {
                    Set<Node> texts = child.lookupAll("Text");

                    for (Node text : texts) {
                        text.setMouseTransparent(true);
                    }
                } else if (child instanceof Shape) {
                    child.setMouseTransparent(true);
                }
            }

            return;
        }
    }

    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setText(null);
            this.setGraphic(null);
            this.setMouseTransparent(true);
            this.setStyle("-fx-background-color:TRANSPARENT;");
        } else {
            this.setMouseTransparent(false);
            this.setStyle(null);
            if (item instanceof Node) {
                this.setText(null);
                Node currentNode = this.getGraphic();
                Node newNode = (Node)item;
                if (currentNode == null || !currentNode.equals(newNode)) {
                    this.cellContent = newNode;
                    this.cellRippler.rippler.cacheRippleClip(false);
                    ((Region)this.cellContent).setMaxHeight(this.cellContent.prefHeight(-1.0D));
                    this.setGraphic(this.cellContent);
                }
            } else {
                this.setText(item == null ? "null" : item.toString());
                this.setGraphic(null);
            }

            boolean isListViewMolby = this.getListView() instanceof ListViewMolby;
            if (isListViewMolby && ((ListViewMolby)this.getListView()).isShowTooltip()) {
                if (item instanceof Label) {
                    this.setTooltip(new Tooltip(((Label)item).getText()));
                } else if (this.getText() != null) {
                    this.setTooltip(new Tooltip(this.getText()));
                }
            }
        }

    }

    private void updateClipHeight(double newHeight) {
        this.clip.setHeight(newHeight - this.getGap());
    }

    /** @deprecated */
    @Deprecated
    public BooleanProperty expandedProperty() {
        return this.expandedProperty;
    }

    /** @deprecated */
    @Deprecated
    public void setExpanded(boolean expand) {
        this.expandedProperty.set(expand);
    }

    /** @deprecated */
    @Deprecated
    public boolean isExpanded() {
        return this.expandedProperty.get();
    }

    private void initialize() {
        this.getStyleClass().add("jfx-list-cell");
        this.setPadding(new Insets(8.0D, 12.0D, 8.0D, 12.0D));
    }

    protected double computePrefHeight(double width) {
        double gap = this.getGap();
        return super.computePrefHeight(width) + gap;
    }

    private double getGap() {
        return this.getListView() instanceof ListViewMolby ? (((ListViewMolby)this.getListView()).isExpanded() ? ((ListViewMolby)this.getListView()).currentVerticalGapProperty().get() : 0.0D) : 0.0D;
    }
}
