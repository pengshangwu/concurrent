package Test.thread;

public class TestMain {



    public static void main(String[] args) {

        Pepole p = new Pepole();
        Pepole p2 = new Pepole();

        p.get();
        p2.get();

    }

}

class Pepole {
    private final Object obj = new Object();

    public void get() {
        System.out.println(obj);
    }
}
