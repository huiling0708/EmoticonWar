package main.java.auxiliary;

import main.java.base.ITimer;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
public abstract class CommonUtils {

    private static final Random RANDOM = new Random();//随机数

    /**
     * 获取整形随机数 不判断开始范围于结束范围
     *
     * @param start
     * @param end
     * @return
     */
    public static int nextInt(int start, int end) {
        return start == end ? start : start + RANDOM.nextInt(end - start);
    }

    /**
     * 获取图片
     *
     * @param imageName
     * @return
     */
    public static Image getImage(String imageName) {
        return new ImageIcon(Constant.RESOURCES_PATH + imageName).getImage();
    }

    /**
     * 开启一个 指定频率的定时器
     *
     * @param period
     * @param t
     */
    public static void task(long period, ITimer t) {
        java.util.Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //当结束开关打开时，清除所有定时器
                if (Constant.TIMER_STOP_ON_OFF) {
                    timer.cancel();
                    return;
                }
                t.run();
            }
        };
        timer.schedule(timerTask, 0, period);
    }
}
