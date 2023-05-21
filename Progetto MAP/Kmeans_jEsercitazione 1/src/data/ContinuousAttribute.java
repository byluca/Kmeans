package data;


public class ContinuousAttribute extends Attribute{

    private double max;

    private double min; //Rappresentano gli estremi dell'intervallo di valori(dominio) che l'attributo può realmente assumere

    public ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    public double getScaledValue(double v)
    {
        v = (v-this.min)/(this.max-this.min);
        return v;
    }
}
