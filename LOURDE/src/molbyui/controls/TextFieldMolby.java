package molbyui.controls;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.Effect;
import javafx.scene.text.Font;

import javax.swing.event.ChangeListener;

public class TextFieldMolby extends JFXTextField {

    public TextFieldMolby(){

    }

    private Boolean _onlyNumber = false;


    public TextFieldMolby(Font fontListView){
        this.setFont(fontListView);
    }

    public TextFieldMolby(Font fontListView, String prompt){
        this.setFont(fontListView);
        this.setPromptText(prompt);
    }

    public TextFieldMolby(Font fontListView, Boolean onlyNumber){
        this.setFont(fontListView);
        if(onlyNumber){ setOnlyNumber(); }
    }

    public TextFieldMolby(Font fontListView, Boolean onlyNumber, String prompt){
        this.setFont(fontListView);
        if(onlyNumber){ setOnlyNumber(); }
        this.setPromptText(prompt);
    }


    public TextFieldMolby(Font fontListView, Effect dropShadow){
        this.setFont(fontListView);
        this.setEffect(dropShadow);
    }

    private void onlyNumber(){
        _onlyNumber = true;
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public Boolean isOnlyNumber(){
        return _onlyNumber;
    }

    public void setOnlyNumber(){
        onlyNumber();
    }

}
