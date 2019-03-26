package main.java.content.substance.trap;

import main.java.auxiliary.CommonUtils;
import main.java.base.BaseElement;
import main.java.base.IElement;
import main.java.base.IHurtPlayer;
import main.java.content.enemy.base.Enemy;

/**
 * @author 左边牙齿疼
 * @Description: 足球
 * @date 2019/3/24
 */
@IElement("football.gif")
public class Football extends BaseElement implements IHurtPlayer {

    public Football(Enemy enemy) {
        super(enemy.getX(), enemy.getY());
        this.xSpeed = CommonUtils.nextInt(10,20);
    }
}
