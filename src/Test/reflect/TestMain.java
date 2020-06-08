package Test.reflect;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestMain {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field field = Foo.class.getDeclaredField("value");

        System.out.println(field.get(new Foo()));

        Unsafe unsafe = Unsafe.getUnsafe();

        long offset = Unsafe.ARRAY_INT_BASE_OFFSET;

        System.out.println(offset);


    }


}
