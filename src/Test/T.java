package Test;

public class T {

    static int i = 9;
    static int j = 9;
    int m = 10;

    public int add(int a, int b) {
        int k = 10;
        int c = a + b;
        return c;
    }

    public static void main(String[] args) {
        T t = new T();
        t.add(3, 4);
    }

}
