package main.java.content.substance.trap;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.base.BaseGravityElement;
import main.java.base.IElement;
import main.java.content.enemy.base.Enemy;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 炸弹
 * @date 2019/3/24
 */
@IElement("bomb.gif")
public class Bomb extends BaseGravityElement {

    public Bomb(Enemy enemy) {
        super(enemy.getX(), enemy.getY());
        xSpeed = CommonUtils.nextInt(5, 15);
        ySpeed = CommonUtils.nextInt(12, 20);
    }

    private Image skeletonImage = CommonUtils.getImage("skeleton.gif");//爆炸骷髅图片
    private boolean kick = false;//踢开开关，只能被玩家踢一次
    private int count = 0;//计数

    //闪光时间
    private boolean light() {
        return count >= 50 && count < 75;
    }

    //已经爆炸
    public boolean hasBlast() {
        return count >= 75;
    }

    //移除
    @Override
    public boolean remove(Player player) {
        return count > 95;
    }

    @Override
    public float getQuality() {
        return 40;
    }

    @Override
    public void action() {
        //扔到地面以后开始计数
        if (this.onTheGround && this.ySpeed < 0) {
            this.count++;
            if (count == 75) {
                Audio.BombBlast.play();
            }
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
    public void drawImage(Graphics g) {
        //闪光时刻
        if (this.light() && count % 2 == 0) {
            g.drawImage(this.skeletonImage, this.x, this.y, this.width, this.height, null);
        }
        //爆炸瞬间
        else if (this.hasBlast()) {
            int newX = this.x - (this.width * 2);
            for (int i = 0; i < 5; i++) {
                g.drawImage(this.skeletonImage, newX, this.y, this.width, this.height, null);
                newX += this.width;
            }
        } else {
            super.drawImage(g);
        }
    }

    @Override
    public Rectangle getRectangle() {
        if (this.hasBlast()) {
            return new Rectangle(this.x - (this.width * 2), this.y, this.width * 5, this.height);
        }
        return super.getRectangle();
    }

    @Override
    public boolean encounterPlayer(Player player) {
        if (!this.intersects(player)) {
            return super.encounterPlayer(player);
        }
        //未爆炸踢开，已经爆炸伤害玩家
        if (hasBlast()) {
            player.beHurt();
            return false;
        }
        if (!this.kick) {
            this.xSpeed = -this.xSpeed - player.getxSpeed() / 3;
            this.ySpeed = 15 + player.getxSpeed();
            this.kick = true;
            Audio.KickBombs.play();
        }
        return super.encounterPlayer(player);
    }
}
