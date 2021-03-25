import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/yangcheng33/article/details/47708631 lock、tryLock等区别
 * https://blog.csdn.net/weixin_30439031/article/details/97179338  interrupte的用途
 */
// 链表中的各个节点有可能是独占模式，也有可能是共享模式(可能是错误，队列中全是独占)
public class ReentrantLockTest {
    public static void main(String[] args) throws Exception {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.tryLock();
        lock.tryLock(1000, TimeUnit.SECONDS);
        lock.lockInterruptibly();

        try {

        }catch (Exception e) {

        }finally {
            lock.unlock();
        }


    }
}
