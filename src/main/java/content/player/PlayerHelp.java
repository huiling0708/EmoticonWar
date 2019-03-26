package main.java.content.player;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.base.BaseElement;
import main.java.base.IElement;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
@IElement("helpStart.gif")
public class PlayerHelp extends BaseElement {

    public PlayerHelp(PlayerCandy playerCandy) {
        super(0, playerCandy.getY());
        this.playerCandy = playerCandy;
    }

    private PlayerCandy playerCandy;//糖果
    private Image helpReadyImage = CommonUtils.getImage("helpReady.gif");
    private Image helpEndImage = CommonUtils.getImage("helpEnd.gif");

    private int count = 0;//大于0表示吃到了糖果
    private int stepCount = 0;//大于0表示玩家踩过了

    @Override
    public void action() {
        //开始阶段
        if (count == 1) {
            playerCandy.remove = true;//糖果可以移除
        } else if (this.count == 0 && this.x < playerCandy.getX()) {
            this.x += 7;//向糖果靠近
            return;
        }
        //准备阶段
        this.count++;
        if (this.stepCount > 0) {
            //结束阶段
            this.stepCount++;
        }
    }

    //是否可以踩
    private boolean canStep() {
        return stepCount == 0 && this.count > 15;
    }

    @Override
    public boolean encounterPlayer(Player player) {
        if (this.canStep() && this.aboveIntersects(player)) {
            this.stepCount++;//踩到了 自身变大
            this.width = 36;
            this.height = 36;
            this.y -= 24;
            player.setOnTheGround(true);//设置玩家在地面上
            player.setySpeed(60);//增加一个向上的加速度
            Audio.StepHelpGirl.play();
            Audio.PlayerFly.play();
        }
        return super.encounterPlayer(player);
    }

    @Override
    public boolean remove(Player player) {
        //踩过之后或者始终没有踩
        return stepCount > 200 || count > 300;
    }

    @Override
    protected Image getImage() {
        if (stepCount > 0) {
            return helpEndImage;
        }
        if (count > 15) {
            return helpReadyImage;
        }
        return super.getImage();
    }
}
