package main.java.content.player;

import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.Keys;
import main.java.base.IDraw;
import main.java.content.common.Attribute;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 玩家状态
 * @date 2019/3/24
 */
public class PlayerState implements IDraw {

    protected Attribute hp = new Attribute(8, 10);//血量
    protected Attribute mp = new Attribute(200, 250);//能量
    protected Attribute shit = new Attribute(100, 100);//大便
    protected Attribute candy = new Attribute(10, 20);//糖果
    protected Attribute aggressivity = new Attribute(50);//攻击力
    protected Attribute hurtState = new Attribute(0, 15);//伤害状态
    protected Attribute score = new Attribute(0);//分数
    protected Attribute gameLevel = new Attribute(0);//游戏等级

    //玩家状态图片
    private Image hpImage = CommonUtils.getImage("hp.gif");
    private Image mpImage = CommonUtils.getImage("mpIcon.png");
    private Image shitImage = CommonUtils.getImage("shit.gif");
    private Image candyImage = CommonUtils.getImage("candy.gif");

    //玩家动态图片
    private Image stopImage = CommonUtils.getImage("stop.gif");//站立
    private Image jumpImage = CommonUtils.getImage("jump.gif");//跳
    private Image rightImage = CommonUtils.getImage("right.gif");//右移
    private Image rightMaxImage = CommonUtils.getImage("rightMax.gif");//右移最大值
    private Image rightJumpImage = CommonUtils.getImage("rightJump.gif");//右跳
    private Image leftImage = CommonUtils.getImage("left.gif");//左移
    private Image leftMxImage = CommonUtils.getImage("leftMax.gif");//左移最大值
    private Image leftJumpImage = CommonUtils.getImage("leftJump.gif");//左跳
    private Image shitEnergyImage = CommonUtils.getImage("shitEnergy.gif");//大便蓄力能量表情
    private Image hurtImage = CommonUtils.getImage("hurt.gif");//受到伤害
    private Image gameOver = CommonUtils.getImage("gameover.png");//gameover

    public Image getImage(Player player) {
        if (hurtState.health()) {
            return hurtImage;
        }
        if (Keys.LEFT.use()) {
            return Keys.JUMP.use() ? leftJumpImage : player.getxSpeed() == player.xSpeedMax ? leftMxImage : leftImage;
        }
        if (Keys.RIGHT.use()) {
            return Keys.JUMP.use() ? rightJumpImage : player.getxSpeed() == player.xSpeedMax ? rightMaxImage : rightImage;
        }
        if (Keys.SHIT.use()) {
            return shitEnergyImage;
        }
        if (Keys.JUMP.use()) {
            return jumpImage;
        }
        return stopImage;
    }


    @Override
    public void drawImage(Graphics g) {
        g.drawImage(mpImage, 2, 2, 24, 24, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("幼圆", Font.BOLD, 24));
        g.drawString("×" + mp.getValue(), 24, 24);
        g.drawImage(shitImage, 100, 2, 24, 24, null);
        g.drawString("×" + shit.getValue(), 120, 24);
        g.drawImage(candyImage, 195, 2, 24, 24, null);
        g.drawString("×" + candy.getValue(), 220, 24);

        g.drawString("SCORE:" + score.getValue(), 300, 24);
        g.drawString("LEVEL " + gameLevel.getValue(), 944, 24);

        int i = 0;
        while (i < hp.getValue()) {
            g.drawImage(hpImage, i * 24, 24, 24, 24, null);
            i++;
        }
        if (!hp.health()) {
            g.drawImage(gameOver, Constant.FRAME_WIDTH / 2 - 200, 200, 400, 71, null);
        }
    }

    public Attribute getHp() {
        return hp;
    }

    public Attribute getMp() {
        return mp;
    }

    public Attribute getShit() {
        return shit;
    }

    public Attribute getCandy() {
        return candy;
    }

    public Attribute getAggressivity() {
        return aggressivity;
    }

    public Attribute getHurtState() {
        return hurtState;
    }

    public Attribute getScore() {
        return score;
    }

    public Attribute getGameLevel() {
        return gameLevel;
    }
}
