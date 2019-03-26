package main.java.content.enemy.base;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Direction;
import main.java.auxiliary.PropType;
import main.java.base.BaseGravityElement;
import main.java.base.IElement;
import main.java.content.common.Attribute;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 敌人基础
 * @date 2019/3/23
 */
public abstract class Enemy extends BaseGravityElement {

    protected int rewardScore;//奖励分数
    protected Attribute hp;//血量
    protected Attribute defense;//防御力
    protected Attribute spitState = new Attribute(0, 80);//吐饭状态
    protected Attribute haloState = new Attribute(0, 80);//晕眩状态
    protected Attribute deathState = new Attribute(0, 100);//死亡状态


    protected Image spitImage = CommonUtils.getImage("spit.gif");//吐饭
    protected Image haloImage = CommonUtils.getImage("halo.gif");//晕
    protected Image deathImage = CommonUtils.getImage("death.gif");//死

    public Enemy(int x, int y) {
        super(x, y);
        IElement ann = this.getClass().getAnnotation(IElement.class);
        hp = new Attribute(ann.hp());
        defense = new Attribute(ann.defense());
        rewardScore = ann.rewardScore();
        xSpeed = ann.xSpeed();
        ySpeed = ann.ySpeed();
    }

    /**
     * 吃大便
     */
    public void eatShit() {
        //如果无防御状态下吃到大便，则直接挂掉
        if (!this.defense.health()) {
            this.hp.setValue(0);
            this.deathState.toMax();//开始死亡状态
            Audio.EnemyDeath.play();
            return;
        }
        //吃大便防御力变0
        this.defense.setValue(0);
        this.spitState.toMax();//开启呕吐状态
        Audio.EnemyVomit.play();
    }

    /**
     * 被攻击
     *
     * @param aggressivity       攻击力
     * @param retreatingDistance 后退距离
     */
    public void beHurt(int aggressivity, int retreatingDistance) {
        //受到伤害=攻击力- 当前防御力
        int hurtValue = aggressivity - defense.getValue();
        if (hurtValue <= 0) {//未造成伤害
            return;
        }
        //击退距离 已区分左右
        this.x += retreatingDistance;
        this.hp.subtract(hurtValue);
        if (this.hp.health()) {
            this.haloState.toMax();//开启晕眩状态
            Audio.FistHurt.play();
        } else {
            this.deathState.toMax();//开启死亡状态
            Audio.EnemyDeath.play();
        }
    }

    @Override
    public boolean beforeActionJudge() {
        if (deathState.health()) {
            deathState.subtract();
            return false;
        }
        if (spitState.health()) {
            spitState.subtract();
            return false;
        }
        if (haloState.health()) {
            haloState.subtract();
            return false;
        }
        return true;
    }

    @Override
    public void encounterSide() {
        //撞墙之后反向移动
        if (this.direction.right()) {
            this.direction = Direction.LEFT;
            this.x -= 5;
        } else {
            this.direction = Direction.RIGHT;
            this.x += 5;
        }
    }

    @Override
    public boolean encounterPlayer(Player player) {
        if (this.intersects(player) && this.beforeActionJudge()) {
            player.beHurt();//伤害玩家
        }
        return super.encounterPlayer(player);
    }

    @Override
    public boolean remove(Player player) {
        if (!hp.health() && !deathState.health()) {
            //敌人死亡，增加分数
            player.getPlayerState().getScore().add(this.rewardScore);
            PropType.createProp(this.getX() + 100, this.getY() - 200);
            return true;
        }
        return false;
    }

    @Override
    protected Image getImage() {
        if (deathState.health()) {
            return this.deathImage;
        }
        if (spitState.health()) {
            return this.spitImage;
        }
        if (haloState.health()) {
            return this.haloImage;
        }
        return super.getImage();
    }


}
