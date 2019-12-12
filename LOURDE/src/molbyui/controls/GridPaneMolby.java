package molbyui.controls;

import javafx.beans.DefaultProperty;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Map;

@DefaultProperty("children")
public class GridPaneMolby extends GridPane {


    public GridPaneMolby() {

    }

    public GridPaneMolby(Integer numberRows, Integer numberColumn, Map<Integer,Double> valueRow, Map<Integer,Double> valueColumn){
        affectValue(numberRows, numberColumn, valueRow, valueColumn);
    }

    public GridPaneMolby(Double height, Integer numberRows, Integer numberColumn, Map<Integer,Double> valueRow, Map<Integer,Double> valueColumn){
        this.setHeight(height);
        this.setMaxHeight(height);
        affectValue(numberRows, numberColumn, valueRow, valueColumn);
    }

    public GridPaneMolby(String id, Double prefWidth, Double prefHeight, Integer numberRows, Integer numberColumn, Map<Integer,Double> valueRow, Map<Integer,Double> valueColumn){
        affectValue(numberRows, numberColumn, valueRow, valueColumn);
        this.setId(id);
        this.setFocusTraversable(false);
        this.setPrefHeight(prefHeight);
        this.setPrefWidth(prefWidth);
        this.setMaxHeight(Region.USE_PREF_SIZE);
        this.setMaxWidth(Region.USE_PREF_SIZE);
        this.setMinWidth(Region.USE_PREF_SIZE);
        this.setMinHeight(Region.USE_PREF_SIZE);

    }

    private void affectValue(Integer numberRows, Integer numberColumn,  Map<Integer,Double> valueRow, Map<Integer,Double> valueColumn){
        this.getRowConstraints().addAll(new ArrayList<RowConstraintsMolby>() {{
            for(Integer i = 0; i < numberRows; i++) {
                if (valueRow != null && valueRow.containsKey(i)){
                    add(new RowConstraintsMolby(valueRow.get(i)));
                } else {
                    add(new RowConstraintsMolby());
                }
            }
        }});
        this.getColumnConstraints().addAll(new ArrayList<ColumnConstraintsMolby>() {{
            for(Integer i = 0; i < numberColumn; i++) {
                if (valueColumn != null && valueColumn.containsKey(i)){
                    add(new ColumnConstraintsMolby(valueColumn.get(i)));
                } else {
                    add(new ColumnConstraintsMolby());
                }
            }
        }});
    }


    public Node getChild(Integer index) {
        return this.getChildren().get(index);
    }

    public ColumnConstraints getColumn(Integer index) {
        return this.getColumnConstraints().get(index);
    }

    public RowConstraints getRow(Integer index) {
        return this.getRowConstraints().get(index);
    }

    public void addChild(Node child){
        this.getChildren().add(child);
    }

    public void addChildAt(Node child, int index){
        this.getChildren().add(index, child);
    }

    public GridPaneMolby addChildChaining(Node child){
        this.getChildren().add(child);
        return this;
    }

    public void addChildColumnAt(Node child, Integer index){
        setColumnIndex(child,index);
        this.addChild(child);
    }

    public void addChildRowAt(Node child, Integer index){
        setRowIndex(child,index);
        this.addChild(child);
    }

    public void replaceChild(int index, Node element){
        this.getChildren().remove(index);
        this.addChildAt(element, index);

    }
}
