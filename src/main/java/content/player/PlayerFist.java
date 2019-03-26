package main.java.content.player;

import main.java.auxiliary.Audio;
import main.java.base.BaseElement;
import main.java.base.IElement;

/**
 * @author 左边牙齿疼
 * @Description: 拳头
 * @date 2019/3/23
 */
@IElement(
        leftImage = "leftFist.gif",
        rightImage = "rightFist.gif")
public class PlayerFist extends BaseElement {

    private Player player;//拳头的y坐标始终要与玩家一致
    private int count;//计数

    public PlayerFist(Player player) {
        super(player.getX(), player.getY());
        this.direction = player.getDirection();
        float energy = player.fistSkill.getEnergy();
        this.xSpeed = energy > 5 ? 5 : energy;//出拳距离
        this.player = player;
        Audio.PlayerFist.play();
    }

    @Override
    public void action() {
        this.y = player.getY();
        if (count == 0) {
            this.x = player.getX();//出拳时与玩家平行
        } else if (count < 10) { //出拳
            this.x += direction.right() ? xSpeed + player.getxSpeed() : -xSpeed - player.getxSpeed();
        } else { //收拳
            this.x += direction.right() ? -xSpeed - player.getxSpeed() : xSpeed + player.getxSpeed();
        }
        count++;
    }

    @Override
    public boolean remove(Player player) {
        if (count > 18) {
            return true;
        }
        //与玩家方向相反时消除
        if (!direction.equals(player.getDirection())) {
            return true;
        }
        //如果面朝右边，则拳头小于玩家坐标时移除
        if (direction.right()) {
            return this.x < player.getX() - player.getxSpeed() - 1;
        } else {
            return this.x > player.getX() + player.getxSpeed() + 1;
        }
    }

    public int getAggressivity() {
        return this.player.playerState.aggressivity.getValue();
    }
}
