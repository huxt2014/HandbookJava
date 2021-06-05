package handbook.sdk.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HbReentrantReadWriteLock {

    public static void main(String[] args){
        /*
        1. 内部维护有两个lock：readLock和writeLock
        2. 两个lock共享一个sync
        3. sync中的state被拆成了两个16bit计数器，分别统计sharedCount和exclusiveCount
        4. sync中的state使用lock free方式维护
         */
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        /*
        不可以升级
        1. 以下情况会acquire失败：
            a：有writeLock，但不是当前线程
            b：待补充
        2. 失败的话，以SHARED模式放到队列中
         */
        rwLock.readLock().lock();
        try{
            System.out.println("get read lock");
        } finally {
            rwLock.readLock().unlock();
        }

        /*
        可以降级
        1. 以下情况会acquire失败
            a：有任意的ReadLock(所以不能先ReadLock，再WriteLock)
            b：WriteLock不是当前线程
        2. 放到队列中(参考ReentrantLock)
         */
        rwLock.writeLock().lock();
        rwLock.readLock().lock();
        try{
            System.out.println("get write read lock");
        } finally {
            rwLock.writeLock().unlock();
            rwLock.readLock().unlock();
        }
    }
}
