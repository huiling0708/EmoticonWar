package main.java.auxiliary.service;

import main.java.base.BaseElement;
import main.java.base.ElementService;
import main.java.content.enemy.base.Enemy;
import main.java.content.player.PlayerFist;
import main.java.content.player.PlayerShit;
import main.java.content.substance.trap.Bomb;

/**
 * @author 左边牙齿疼
 * @Description: 敌人元素服务
 * @date 2019/3/24
 */
public class EnemyElementService extends ElementService<Enemy> {

    @Override
    protected <S extends BaseElement> boolean intersectsHandle(Enemy myself, S other) {
        //被大便打到
        if (other instanceof PlayerShit) {
            //吃屎
            myself.eatShit();
            return true;
        }
        //被拳头打到
        if (other instanceof PlayerFist) {
            PlayerFist fist = (PlayerFist) other;
            int ay = fist.getAggressivity();//攻击力
            myself.beHurt(ay, fist.getDirection().left() ? -ay : ay);
            return false;
        }
        //被炸弹炸到
        if (other instanceof Bomb) {
            Bomb bomb = (Bomb) other;
            if (bomb.hasBlast()) {
                myself.beHurt(500, 0);
            }
            return false;
        }
        return super.intersectsHandle(myself, other);
    }
}
