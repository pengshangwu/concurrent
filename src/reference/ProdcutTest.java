package reference;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

public class ProdcutTest {

    public static void main(String[] args) {

//        WeakReference<Product> weakReference = new WeakReference<>(new Product());
//
//        System.out.println(weakReference.get());
//
//        System.gc();
//
//        System.out.println(weakReference.get());

        BigDecimal bigDecimal = new BigDecimal(100);
        System.out.println(bigDecimal);
    }

}
