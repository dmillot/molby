package molbyui.controls;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class DropShadowMolby {

    public DropShadowMolby(){

    }

    public static DropShadow dropShadowRow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(0,0,0));
        dropShadow.setHeight(30);
        dropShadow.setWidth(20);
        dropShadow.setRadius(13.25);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(2);
        dropShadow.setSpread(0);
        return dropShadow;
    }

    public static DropShadow dropShadowRow(Color color){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(color);
        dropShadow.setHeight(30);
        dropShadow.setWidth(20);
        dropShadow.setRadius(13.25);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(2);
        dropShadow.setSpread(0);
        return dropShadow;
    }

    public static DropShadow dropShadowIconRow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(0,0,0));
        dropShadow.setHeight(25);
        dropShadow.setWidth(15);
        dropShadow.setRadius(13.25);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1);
        dropShadow.setSpread(0);
        return dropShadow;
    }

    public static DropShadow dropShadowIconRow(Color color){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(color);
        dropShadow.setHeight(25);
        dropShadow.setWidth(15);
        dropShadow.setRadius(13.25);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(1);
        dropShadow.setSpread(0);
        return dropShadow;
    }

    public static DropShadow dropShadowLabel(Color color){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(color);
        dropShadow.setHeight(2);
        dropShadow.setWidth(2);
        dropShadow.setRadius(0);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(1);
        dropShadow.setSpread(0);
        return dropShadow;
    }
}
