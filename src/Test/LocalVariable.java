package Test;

/**
 * 验证局部变量线程是否安全
 */
public class LocalVariable {

    public void add() {
        int sum = 0;
        for (int i = 0; i < 100000; i++) {
            sum += 1;
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", " + sum);
    }

    public People get() {
        return new People();
    }

    public void add(People p) {
        People people = p;
        for (int i = 0; i < 100000; i++) {
            p.setAge();
        }
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", " + p.getAge());
    }

    public void add(int value) {
        for (int i = 0; i < 100000; i++) {
            value++;
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", " + value);
    }

    public void addAge() {
        People p = new People();
        for (int i = 0; i < 100000; i++) {
            p.setAge();
        }
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", " + p.getAge());
    }

    public static void main(String[] args) {
        // 方法内的局部参数不存在并发问题
//        LocalVariable l = new LocalVariable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> l.add()).start();
//        }

        // 参数是引用类型，但是每次调用的时候都是创建一个新的People对象
//        People p = new People();
//        LocalVariable l = new LocalVariable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> l.add(l.get())).start();
//        }

        // 参数类型为引用类型(如果内部使用局部参数来接收)，但是该类型被多个线程共用，存在并发问题
//        People p = new People();
//        LocalVariable l = new LocalVariable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> l.add(p)).start();
//        }

        // 参数类型为引用类型(如果内部使用局部参数来接收)，但是该类型依据被多个线程共用，存在并发问题
//        People p = new People();
//        LocalVariable l = new LocalVariable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> l.add(p)).start();
//        }

        // 局部参数不存在并发问题
//        LocalVariable l = new LocalVariable();
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> l.addAge()).start();
//        }
    }
}

class People{
    private int age = 0;

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age++;
    }
}
