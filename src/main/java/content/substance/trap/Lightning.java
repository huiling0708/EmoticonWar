package main.java.content.substance.trap;

import main.java.auxiliary.CommonUtils;
import main.java.base.BaseElement;
import main.java.base.IElement;
import main.java.base.IHurtPlayer;
import main.java.content.enemy.base.Enemy;

/**
 * @author 左边牙齿疼
 * @Description: 闪电
 * @date 2019/3/24
 */
@IElement("lightning.gif")
public class Lightning extends BaseElement implements IHurtPlayer {
    public Lightning(Enemy enemy) {
        super(enemy.getX(), enemy.getY());
        this.ySpeed = CommonUtils.nextInt(5, 15);
    }

    @Override
    public void action() {
        this.y += this.ySpeed;
    }
}
