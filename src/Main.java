import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Realization real = new Realization();
        real.setA(5);
        real.setB("I am serialized");
        String res = ClassSerialize.serialize(real);
        System.out.println("serialized: "+res);
        real = ClassSerialize.deserialize(res,Realization.class);
        System.out.println("deserialized: "+ real.getA()+"\n"+real.getB()+"\n"+real.getC());
    }
}