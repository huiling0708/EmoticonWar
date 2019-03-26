package main.java.content.enemy;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.base.IElement;
import main.java.content.enemy.base.CountEnemy;
import main.java.content.enemy.base.IEnemy;

/**
 * @author 左边牙齿疼
 * @Description: 疯狂
 * @date 2019/3/24
 */
@IEnemy(probability = 30, number = 1, score = 2000)
@IElement(leftImage = "enemyCrazyLeft.gif", rightImage = "enemyCrazyRight.gif",
        defense = 100, rewardScore = 1000)
public class CrazyEnemy extends CountEnemy {

    public CrazyEnemy(int x, int y) {
        super(x, y);
        Audio.SinisterSmile.play();
    }

    @Override
    protected void doSomething() {
        this.xSpeed = CommonUtils.nextInt(-10, 30);
    }

    @Override
    protected int getCountMax() {
        return 10;
    }
}
