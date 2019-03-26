package main.java.content.player;

import main.java.auxiliary.Audio;
import main.java.base.BaseGravityElement;
import main.java.base.IElement;

/**
 * @author 左边牙齿疼
 * @Description: 玩家扔大便
 * @date 2019/3/23
 */
@IElement("shit.gif")
public class PlayerShit extends BaseGravityElement {

    public PlayerShit(Player player) {
        super(player.getX(), player.getY());
        this.direction = player.getDirection();
        float energy = player.shitSkill.getEnergy();//根据玩家蓄力时间来增加大便扔出的高度与距离
        xSpeed = 5 + (int) (energy * 3);
        ySpeed = 13 + (int) (energy * 5);
        Audio.ThrowShit.play();
    }

    private int count = 0;//移除计数

    @Override
    public void action() {
        //扔到地面以后开始计数
        if (this.onTheGround && this.ySpeed < 0) {
            this.count++;
            return;
        }
        //移动
        super.action();
        this.ySpeed--;
        if (xSpeed > 10) {
            xSpeed--;
        }
    }

    @Override
    public float getQuality() {
        return 40;
    }

    @Override
    public boolean remove(Player player) {
        return count > 50;
    }

    @Override
    public boolean encounterPlayer(Player player) {
        if (count > 5 && this.intersects(player)) {//已经落地 并与玩家相遇捡起来
            player.playerState.shit.add();//大便++
            Audio.PickShit.play();
            return true;
        }
        return false;
    }
}
