package com.youme.talktest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadManager {
    private Lock lock = new ReentrantLock();
    private Condition ZhuBo  = lock.newCondition();
    private Condition audience1 = lock.newCondition();
    private Condition audience2 = lock.newCondition();
    public boolean ZhuBoReady = false;
    public boolean A1Ready = false;
    public boolean A2Ready = false;
    public void ZbAwait() {
        //阻塞主播线程
        try {
            lock.lock();
            System.out.println("执行 ZbAwait 方法，主播线程阻塞");
            ZhuBo.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println("ZbAwait 锁释放了");
        }
    }

    public void ZbSignal() {
        //唤醒主播线程
        try {
            lock.lock();
            System.out.println("执行ZbSignal方法，主播线程唤醒");
            ZhuBo.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void A1Await() {
        //阻塞听众1线程
        try {
            lock.lock();
            System.out.println("执行 A1Await 方法，听众1线程阻塞");
            audience1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println("A1Await 锁释放了");
        }
    }

    public void A1Signal() {
        //唤醒听众1线程
        try {
            lock.lock();
            System.out.println("执行A1Signal方法，听众1线程唤醒");
            audience1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void A2Await() {
        //阻塞听众2线程
        try {
            lock.lock();
            System.out.println("执行 A2Await 方法，听众2线程阻塞");
            audience2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println("A2Await 锁释放了");
        }
    }

    public void A2Signal() {
        //唤醒听众2线程
        try {
            lock.lock();
            System.out.println("执行A2Signal方法，听众2线程唤醒");
            audience2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
