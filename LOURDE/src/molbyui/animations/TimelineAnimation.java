package molbyui.animations;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.util.Duration;

public class TimelineAnimation {
    public TimelineAnimation(){

    }

    public static Timeline timeLineAnimation(Double duration, Property<Number> property, Double value){
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration),new KeyValue( property,value)));
        return timeline;
    }

    public static Timeline timeLineAnimation(Double duration, Property<Number> property, Double value, Interpolator interpolator){
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration),new KeyValue( property,value, interpolator)));
        return timeline;
    }
}
