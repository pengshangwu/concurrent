package yxxy.c_027;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 读多写少
 * 1、允许多个线程共享读锁
 * 2、写锁同一时间只有一个拥有
 * 3、写线程在操作时，禁止读线程读共享变量
 * <p>
 * 可以由写锁降级为读锁，不会产生死锁的问题
 * 读锁(还没有释放)升级到写锁会产生死锁的问题
 */
public class T02_ReadWriteLock {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 读锁
    private Lock r = readWriteLock.readLock();
    // 写锁
    private Lock w = readWriteLock.writeLock();

    private Map<String, String> map = new HashMap<>();

    public String test() {
        String v1 = null;

        try {
            r.lock();
            v1 = map.get("k1");
            System.out.println(Thread.currentThread().getName() + ", r");

            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }finally {
            r.unlock();
        }

//        if(v1 != null) {
//            return v1;
//        }

//        w.lock();
//        try {
//            v1 = map.get("k1");
//            // 模拟数据库连接，当缓存没数据，进入查询后再回种到缓存中，当下一个线程进来的时候已经存在数据则不和数据库连接
//            if(v1 == null) {
//                System.out.println(Thread.currentThread().getName() + ", w");
//                map.put("k1", "v1");
//                return map.get("k1");
//            }
//        }finally {
//            w.unlock();
//        }
        return v1;
    }

    public void test01() {
        w.lock();
        try {

            System.out.println(Thread.currentThread().getName() + ", w");

        }finally {
            w.unlock();
        }
    }

    // 锁的升级，会造成死锁
    public void  upgrade() {

        r.lock();
        System.out.println(Thread.currentThread().getName() + ", r");

        w.lock();
        System.out.println(Thread.currentThread().getName() + ", w");
        w.unlock();

        r.unlock();

    }

    // 锁的降级
    public void degrade() {

        w.lock();
        System.out.println(Thread.currentThread().getName() + ", w");

        r.lock();
        System.out.println(Thread.currentThread().getName() + ", r");
        r.unlock();

        w.unlock();

    }

    public static void main(String[] args) {

        T02_ReadWriteLock readWriteLock = new T02_ReadWriteLock();

        for(int i = 0; i < 10; i++){
            new Thread(readWriteLock::test, "t" + i).start();
        }
        for(int i = 0; i < 10; i++){
            new Thread(readWriteLock::test01, "t" + i).start();
        }

//        for(int i = 0; i < 10; i++){
//            new Thread(readWriteLock::upgrade, "t" + i).start();
//        }
//        for(int i = 0; i < 10; i++){
//            new Thread(readWriteLock::degrade, "t" + i).start();
//        }




    }




}
