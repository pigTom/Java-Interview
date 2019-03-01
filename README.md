# Java 面试准备

### Java多线程

线程有五种状态
* NEW 线程还没有调用start方法时的状态
* RUNNABLE 线程正在运行时的状态，在runnable状态的线程表示正在Java虚拟机中运行，
但是它可能正在等待从操作系统那里获取资源，比如获取CPU资源。  
* BLOCKED 线程在等待监控锁时的阻塞状态。在BLOCKED状态的线程正在等待监控锁去进入同步块、同步方法，
或者调用wait()方法后重新进行同步块、同步方法发生阻塞状态。
* WAITING 线程正在等待时的状态。以下几个方法会造成WAITING状态：  
  
      Object.wait() with no timeout  
      Thread.join() with no timeout
      LockSupport.part()
  处在WAITING状态的线程正在等待另一个线程完成某个特定动作。例如线程调用了
Object.wait方法，等待直到其它线程调用Object.notify或者Object.notifyAll方法
* TIMED_WAITING 正在等待的线程，等待有指定的超时时间。以下几个方法会造成TIMED_WAITING:  
      
      Thread.speep()
      Object.wait() with timeout
      Object.join() with timeout
      LockSupport.parkNanos
      LockSupport.parkUntil
* TERMINATED 线程终止时的状态。线程已经完成了执行任务。
 
两种线程锁：对象锁和类锁
通过它们都叫监控锁？
    
> **Thread#join**  
> public final synchronized void join(long millis)  
> 假设调用方为线程A，被调用方为线程B  
> 调用方(线程A)至多等待*millis*毫秒到这个线程(线程B)执行完。如果*millis*=0意味着调用方(线程A)一直等待直到这个线程结束,
这个与调用无参的join()效果一样  
> **注意**：只能当线程B的状态为isAlive  (A thread is alive if it has been started and has not yet died)时，
> 线程A才会等待。如果线程B的状态为NEW，TERMINATED，线程A不会等待线程B。

> **Object#wait**  
> 调用者调用该方法后会陷入等待，如果指定时间不为0，则线程状态为TIMED_WAITING。
> 如果没有指定时间或者指定时间为0，则线程状态为WAITING。  
> 调用者必须获取该对象的监控锁才能调用该方法，调用该方法后，会释放任何及所有与该对象有关的同步链。
但是不会释放与该对象无关的同步链。
> 调用者陷入等待后只有该对象调用notify、notifyAll、被interrupt或者超时时间到，调用才能被唤醒或中止。  
> 还有一种情况叫做*spurious wakeup*，For more information on this topic, see section 14.2,
"Condition Queues," in Brian Goetz and others' <em>Java Concurrency
in Practice</em> (Addison-Wesley, 2006) or Item 69 in Joshua
Bloch's <em>Effective Java, Second Edition</em> (Addison-Wesley,2008).

> **Object#nofity**  
> 唤醒一个正在等待获取该对象的监控锁的对象。如果有多个线程要获取该监控锁，
则随机唤醒一个线程。在当前线程释放该锁之前，唤醒的线程是不会执行的。
唤醒的线程通常将会和其它线程一起竞争该对象的监控锁来同步线程。
唤醒的线程没有可靠的特权或者不得条件支争取下一个获得该对象监控锁。
各个想要获得该锁的线程处于公平竞争。  
> 该方法只能被获得该对象监控锁的线程执行。
> 获得该线程锁有三种方式：
- 执行该对象实例的同步方法块
- 执行以该对象为监控锁的同步块
- 执行该对象类的静态同步方法块

