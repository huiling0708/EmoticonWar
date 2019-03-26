package main.java.content.enemy;

import main.java.auxiliary.Audio;
import main.java.auxiliary.ElementBean;
import main.java.base.IElement;
import main.java.content.enemy.base.CountEnemy;
import main.java.content.enemy.base.IEnemy;
import main.java.content.substance.trap.Bomb;

/**
 * @author 左边牙齿疼
 * @Description: 扔炸弹的大兵
 * @date 2019/3/24
 */
@IEnemy(probability = 80, number = 2, score = 0)
@IElement(leftImage = "soldier.png", xSpeed = 0, rewardScore = 800)
public class SoldierEnemy extends CountEnemy {

    public SoldierEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected void doSomething() {
        ElementBean.Substance.getService().add(new Bomb(this));
        Audio.EnemyThrowBomb.play();
    }

    @Override
    protected int getCountMax() {
        return 80;
    }
}
