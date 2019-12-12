package molbyui.controls;

import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;


public final class RipplerMaskTypeConverterMolby extends StyleConverter<String, RipplerMolby.RipplerMask> {
    // lazy, thread-safe instatiation
    private static class Holder {
        static final RipplerMaskTypeConverterMolby INSTANCE = new RipplerMaskTypeConverterMolby();
    }

    public static StyleConverter<String, RipplerMolby.RipplerMask> getInstance() {
        return RipplerMaskTypeConverterMolby.Holder.INSTANCE;
    }

    private RipplerMaskTypeConverterMolby() {
    }

    @Override
    public RipplerMolby.RipplerMask convert(ParsedValue<String, RipplerMolby.RipplerMask> value, Font notUsed) {
        String string = value.getValue();
        try {
            return RipplerMolby.RipplerMask.valueOf(string);
        } catch (IllegalArgumentException | NullPointerException exception) {
            return RipplerMolby.RipplerMask.RECT;
        }
    }

    @Override
    public String toString() {
        return "RipplerMaskTypeConverterMolby";
    }
}
