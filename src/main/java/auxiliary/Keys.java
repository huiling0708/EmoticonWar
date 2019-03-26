package main.java.auxiliary;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
public enum Keys {
    TEST(KeyEvent.VK_SPACE),//测试专用

    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D),
    JUMP(KeyEvent.VK_K),
    CRAZY(KeyEvent.VK_J),
    FIST(KeyEvent.VK_L),
    HELP(KeyEvent.VK_H),
    SHIT(KeyEvent.VK_I);

    private final static Set<Integer> keySet = new HashSet<>();//按键

    Keys(int keyValue) {
        this.keyValue = keyValue;
    }

    private int keyValue;//键值

    /**
     * 是否使用了某键
     *
     * @return
     */
    public boolean use() {
        return keySet.contains(keyValue);
    }

    /**
     * 添加按键
     *
     * @param keyCode
     */
    public static void add(int keyCode) {
        keySet.add(keyCode);
    }

    /**
     * 移除按键
     *
     * @param keyCode
     */
    public static void remove(int keyCode) {
        keySet.remove(keyCode);
    }
}
