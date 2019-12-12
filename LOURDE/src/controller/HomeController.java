package controller;

import javafx.fxml.FXML;
import molbyui.controls.*;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;

@SuppressWarnings("ALL")
public class HomeController {

    @FXML
    private AnchorPaneMolby anchorHome;
    public Boolean burgerExpand = false;

    public void initialize() {

    }


    public void actionAnchor(Boolean expand, AnchorPaneMolby anchorView, Double duration){
        Double duree = (expand) ? (duration != null) ? duration : 500.0 : 200.0;
        Double valueAction = (expand) ? 1831.0 : 1536.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby) anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby)anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby)anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby)anchorLoader.getChild(0)).setPrefWidth(valueAction);
        }
    }
}
