package main.java.content.enemy;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.ElementBean;
import main.java.base.IElement;
import main.java.content.enemy.base.CountEnemy;
import main.java.content.enemy.base.IEnemy;
import main.java.content.substance.trap.Lightning;

/**
 * @author 左边牙齿疼
 * @Description: 飞翔敌人
 * @date 2019/3/24
 */
@IEnemy(probability = 50, number = 1, score = 3000)
@IElement(leftImage = "enemyFlyLeft.gif", rightImage = "enemyFlyRight.gif",
        defense = 0, rewardScore = 1000)
public class FlyEnemy extends CountEnemy {

    public FlyEnemy(int x, int y) {
        super(x, y);
        this.y = CommonUtils.nextInt(50, 150);
        this.xSpeed = CommonUtils.nextInt(5, 25);
    }

    @Override
    protected void xMove() {
        if (this.x < 0 || this.x > Constant.FRAME_WIDTH) {
            this.encounterSide();//到达屏幕两边时反向
        }
        super.xMove();
    }

    @Override
    protected void doSomething() {
        ElementBean.Substance.getService().add(new Lightning(this));
        Audio.EnemyThrowLightning.play();
    }

    @Override
    protected int getCountMax() {
        return 15;
    }

    @Override
    public float getQuality() {
        if (this.hp.health()) {
            return 0;
        }
        return super.getQuality();
    }
}
