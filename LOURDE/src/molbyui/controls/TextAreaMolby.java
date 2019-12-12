package molbyui.controls;

import com.jfoenix.controls.JFXTextArea;
import javafx.scene.effect.Effect;
import javafx.scene.text.Font;

public class TextAreaMolby extends JFXTextArea {

    public TextAreaMolby(){

    }

    public TextAreaMolby(Font fontListView){
        this.setFont(fontListView);
    }

    public TextAreaMolby(Font fontListView, String prompt){
        this.setFont(fontListView);
        this.setPromptText(prompt);
    }

    public TextAreaMolby(Font fontListView, Effect dropShadow){
        this.setFont(fontListView);
        this.setEffect(dropShadow);
    }
}
