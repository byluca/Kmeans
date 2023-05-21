package data;

public class ContinuousItem extends Item{
     ContinuousItem(Attribute attribute, Double value)
    {
        super(attribute, value);
    }

    public double distance(Object a)
    {
        if (!(a instanceof Double)) {
            throw new IllegalArgumentException("Parameter must be an instance of Double.");
        }

        ContinuousAttribute continuousAttribute = (ContinuousAttribute) this.attribute;

        double thisScaledValue = continuousAttribute.getScaledValue((Double) this.value);
        double otherScaledValue = continuousAttribute.getScaledValue((Double) a);

        return Math.abs(thisScaledValue - otherScaledValue);
    }
}
