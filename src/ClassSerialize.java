import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;

public class ClassSerialize {

    public static String serialize(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Save.class))
                continue;
            if (Modifier.isPrivate(field.getModifiers()))
                field.setAccessible(true);
            stringBuilder.append(field.getName() + "=");
            if (field.getType() == int.class) {
                stringBuilder.append(field.getInt(object));
            } else if (field.getType() == String.class) {
                stringBuilder.append((String) field.get(object));
            } else if (field.getType() == long.class) {
                stringBuilder.append(field.getLong(object));
            }
            stringBuilder.append(":");
        }
        return stringBuilder.toString();
    }

    public static <T> T deserialize(String string, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        T res = (T) tClass.getDeclaredConstructor().newInstance();
        String[] st = string.split(":");
        for (String s : st) {
            String[] sp = s.split("=");
            if (sp.length != 2)
                throw new InvalidParameterException(string);
            String name = sp[0];
            String value = sp[1];
            Field field = tClass.getDeclaredField(name);
            if (Modifier.isPrivate(field.getModifiers()))
                field.setAccessible(true);
            if (field.isAnnotationPresent(Save.class)) {
                if (field.getType() == int.class) {
                    field.setInt(res, Integer.parseInt(value));
                } else if (field.getType() == String.class) {
                    field.set(res, value);
                } else if (field.getType() == long.class) {
                    field.setLong(res, Long.parseLong(value));
                }
            }

        }
        return res;
    }
}
