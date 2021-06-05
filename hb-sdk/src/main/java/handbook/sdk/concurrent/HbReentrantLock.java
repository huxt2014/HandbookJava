package handbook.sdk.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class HbReentrantLock {

    public static void main(String[] args){

        ReentrantLock reentrantLock = new ReentrantLock();
        /*
        使用Sync来同步，Sync底层又依赖UnSafe

        Lock.lock方法按如下方式实现：
        1. 先使用Unsafe来做一个CAS操作: 0 -> 1
        2. 如果成功了，那么说明获得了锁（第一次），然后把当前线程记录到Sync中。
        3. 如果失败了，那么可能是：
            a：已经获得了锁，那么计数器+1
            b：其他线程占用了锁，那么调用Sync.lock

        Sync维护了一个lock free队列，Sync.lock方法按如下方式实现
        1. 将线程放到tail
        2. 使用pack将当前线程挂起
        3. 被唤起后，将节点移除队列

        fair lock的特点：除非已经获得了锁，或者当前没有其他线程占用锁，不然放到tail等待
         */
        reentrantLock.lock();
        try{
            System.out.println("ReentrantLock do");
        }finally {
            /*
            调用Sync.release：
            1. 计数器-1，如果：
                a：计数器不为0，完事
                b：计数器为0，应该唤起其他线程，执行步骤2
            2. 从队列head找到第一个需要唤醒的线程，使用unpack
             */
            reentrantLock.unlock();
        }

    }
}
