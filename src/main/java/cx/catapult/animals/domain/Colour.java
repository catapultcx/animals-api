package cx.catapult.animals.domain;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Colour {
    public static final Colour WHITE = new Colour(1.0F, 1.0F, 1.0F, 1.0F);
    public static final Colour BLACK = new Colour(0.0F, 0.0F, 0.0F, 1.0F);
    public static final Colour RED = new Colour(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Colour GREEN = new Colour(0.0F, 1.0F, 0.0F, 1.0F);
    public static final Colour BLUE = new Colour(0.0F, 0.0F, 1.0F, 1.0F);
    public static final Colour TRANSPARENT = new Colour(0.0F, 0.0F, 0.0F, 0.0F);

    private final float red;
    private final float green;
    private final float blue;
    private final float transparency;

    public Colour(float red, float green, float blue, float transparency) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.transparency = transparency;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Colour colour = (Colour) o;

        return new EqualsBuilder().append(red, colour.red).append(green, colour.green).append(blue, colour.blue).append(transparency, colour.transparency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(red).append(green).append(blue).append(transparency).toHashCode();
    }
}
