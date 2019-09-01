package com.tang.java.Thread;
/**
 * tangdunhong
 * 2019/8/21
 * 2:41 PM
 **/
public class VolatileTest {
    private volatile BaseB[] baseB;

    public BaseB[] getBaseB() {
        return baseB;
    }

    public void setBaseB(BaseB[] baseB) {
        this.baseB = baseB;
    }

    Runnable r1 = () -> {
        BaseA ba = baseB[0].getBaseA();
        int i = 1;
        while (!baseB[0].getBaseA().isFlag()) {
            i ++;
        }
        System.out.println("i = " + i);

    };

    Runnable r2 = () -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getBaseB()[0].getBaseA().setFlag(true);
        System.out.println(getBaseB()[0].getBaseA().isFlag());
//         是否flag会刷新到主存
//        while (true) {
//
//        }
    };

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        BaseA a = new BaseA();
        BaseB[] baseB = new BaseB[10];
        baseB[0] = new BaseB();
        baseB[0].setBaseA(a);
        test.setBaseB(baseB);
        Thread t1 = new Thread(test.r1);
        Thread t2 = new Thread(test.r2);

        t1.start();
        t2.start();

        System.out.println("start");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1;
        System.out.println("Over");
    }

    // volatile 修饰的变量只能保证读的可见性，也就是说volatile不保证写一定会写到主内存中
    // volatile 修饰对象或数组可以保存其可见性，但是一旦发生引用传递则不可以保存其可见性。


    void spin() {
        int i;
        for (i = 0; i < 32767; i++) {
        }
    }

    /**
     *
     * void spin();
     *     Code:
     *        0: iconst_0                // 将常数0 加载到操作数栈中
     *        1: istore_1                // 将常数0 从操作数栈中取出 赋值给变量1
     *        2: iload_1                 // 将变量1的值0加载到操作数栈中
     *        3: bipush        100       // 将常数100加载到操作数栈中
     *        5: if_icmpge     14        // 比较栈中的两个数 如果前面大于等于后面的数就 跳到14行
     *        8: iinc          1, 1      // 变量1加一
     *       11: goto          2         // 跳到2行
     *       14: return
     */


    void dsin() {
        double i;
        for (i = 0; i < 32768; i++) {
        }
    }
/**
 * void dsin();
 *     Code:
 *        0: dconst_0                          // 将double0加载到操作数栈中
 *        1: dstore_1                          // 将操作数栈的赋给变量1和变量2
 *        2: dload_1                           // 将变量1和变量2的值加入到操作数栈中
 *        3: ldc2_w        #27                 // 将常数 double 32768.0d加入到操作数栈中
 *        6: dcmpg                             // 弹出并比较操作数栈中的两个double数
 *        7: ifge          17                  // 如果比较结果大于等于0 则跳到17
 *       10: dload_1                           // 将变量1和变量2的值加入到操作数栈中
 *       11: dconst_1                          // 将常数1d加载到操作数栈中
 *       12: dadd                              // 将操作数栈中的第一个数加入前一个double数中
 *       13: dstore_1                          // 将操作数栈中的double数赋给变量1和2
 *       14: goto          2                   // 掉转到2行
 *       17: return
 */

double doubleLocals(double d1, double d2) {
    return d1 + d2;
}
/**
 *  double doubleLocals(double, double);
 *     Code:
 *        0: dload_1            // 将变量1，2加载到操作数栈中
 *        1: dload_3            // 将变量3，4加载到操作数栈中
 *        2: dadd               // 两个double相加
 *        3: dreturn            // 返回double数 (返回时操作数栈中只有返回的数据了）
 * }
 */

    void sspin() {
        short i;
        for (i = 0; i < 100; i++) {
            ;
        }
    }
    /**
     *  void sspin();
     *     Code:
     *        0: iconst_0               // 加载0到operand stack
     *        1: istore_1               // 从operand stack中取值赋值给1变量
     *        2: iload_1                // 将1变量中的值赋加载到operand stack中
     *        3: bipush        100      // 将100加载到operand stack中
     *        5: if_icmpge     16       // 比较两int数据，大于等于就跳转到16行
     *        8: iload_1                // 加载变量1的值到栈中
     *        9: iconst_1               // 加载1到栈中
     *       10: iadd                   // 将栈中的第一个值加到第二个值上
     *       11: i2s                    // 将int强转为short
     *       12: istore_1               // 将栈中的值加载到变量1中
     *       13: goto          2        // 跳转到2行
     *       16: return                 // 返回
     * }
     *
     * Process finished with exit code 0
     */
}
