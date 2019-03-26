package main.java.content.enemy.base;

/**
 * @author 左边牙齿疼
 * @Description: 计数敌人 达到计数以后执行某个动作
 * @date 2019/3/24
 */
public abstract class CountEnemy extends Enemy {

    public CountEnemy(int x, int y) {
        super(x, y);
    }

    private int count = 0;

    @Override
    public void action() {
        if (count > getCountMax()) {
            this.doSomething();
            count = 0;
        } else {
            count++;
        }
        super.action();
    }

    /**
     * 到达计数时间后执行
     */
    protected abstract void doSomething();

    /**
     * 计数大小
     *
     * @return
     */
    protected abstract int getCountMax();
}
