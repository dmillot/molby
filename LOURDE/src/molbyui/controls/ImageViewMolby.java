package molbyui.controls;


import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class ImageViewMolby extends ImageView {

    public ImageViewMolby(){

    }

    public ImageViewMolby (Image image, Boolean smooth, Boolean setPreserverRatio, Double height){
        this.setImage(image);
        this.setSmooth(smooth);
        this.setPreserveRatio(setPreserverRatio);
        this.setFitHeight(height);
    }

    public ImageViewMolby(Image image, Boolean smooth, Boolean setPreserverRatio, Double height, Effect dropShadow){
        this.setImage(image);
        this.setSmooth(smooth);
        this.setPreserveRatio(setPreserverRatio);
        this.setFitHeight(height);
        this.setEffect(dropShadow);
    }

    public ImageViewMolby (String url, Boolean smooth, Boolean setPreserverRatio, Double height){
        File file = new File(url);
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert localUrl != null;
        this.setImage(new Image(localUrl, false));
        this.setSmooth(smooth);
        this.setPreserveRatio(setPreserverRatio);
        this.setFitHeight(height);
    }

    public ImageViewMolby (String url, Boolean smooth, Boolean setPreserverRatio, Double height, Effect dropShadow){
        File file = new File(url);
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert localUrl != null;
        this.setImage(new Image(localUrl, false));
        this.setSmooth(smooth);
        this.setPreserveRatio(setPreserverRatio);
        this.setFitHeight(height);
        this.setEffect(dropShadow);
    }
}
