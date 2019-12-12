package molbyui.controls;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.DateCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatePickerMolby extends JFXDatePicker {

    public DatePickerMolby(){

    }

    public DatePickerMolby(LocalDate valueName, RequiredFieldValidator required, Paint color){
        this.setDefaultColor(color);
        this.getValidators().add(required);
        this.setValue(valueName);
    }
}
