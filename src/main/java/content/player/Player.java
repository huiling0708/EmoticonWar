package main.java.content.player;

import main.java.auxiliary.*;
import main.java.base.BaseGravityElement;
import main.java.base.IElement;
import main.java.content.common.Skill;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 玩家
 * @date 2019/3/23
 */
@IElement("stop.gif")
public class Player extends BaseGravityElement {

    private float xAcceleration = 1.2f;//水平加速度
    private final float yAcceleration = 24.0f;//垂直加速度
    protected final float xSpeedMax = 10.0f;//水平速度最大值
    private int carzyCount = 0;//仅用于播放玩家疯狂状态的音效

    protected PlayerState playerState;//玩家状态
    protected Skill<PlayerShit> shitSkill;//大便
    protected Skill<PlayerFist> fistSkill;//拳头
    protected Skill<PlayerCandy> candySkill;//糖果

    public Player() {
        super(100, 100);
        this.playerState = new PlayerState();
        this.shitSkill = new Skill<>(playerState.shit, Keys.SHIT).setAddBaseValue(0.1f);
        this.fistSkill = new Skill<>(Keys.FIST).setMaxEnergy(20f);
        this.candySkill = new Skill<>(playerState.candy, Keys.HELP);
    }

    /**
     * 被伤害
     */
    public void beHurt() {
        playerState.hurtState.toMax();//被伤害
        playerState.hp.subtract();//扣血
        Audio.PlayerBeHurt.play();
    }

    /**
     * 被伤害处理
     */
    private void beHurtHandle() {
        //被伤害状态下弹开
        this.y -= playerState.hurtState.getValue() * 2;
        if (direction.left()) {
            this.x += playerState.hurtState.getValue();
        } else {
            this.x -= playerState.hurtState.getValue();
            if (this.x < 0) {
                this.x = 0;
            }
        }
        playerState.hurtState.subtract();
    }

    @Override
    public void action() {
        if (playerState.hurtState.health()) {
            this.beHurtHandle();//被伤害状态下无法做出其它动作
            return;
        }
        this.shitSkill.action(() -> new PlayerShit(this));
        this.fistSkill.action(() -> new PlayerFist(this));
        this.candySkill.action(() -> new PlayerCandy(this));
        super.action();
    }

    @Override
    protected void xMove() {
        //无横向移动按键时 重置玩家的初速度并回复能量
        if (!Keys.LEFT.use() && !Keys.RIGHT.use()) {
            this.playerState.mp.add();
            this.xSpeed = 1.0f;//,重置玩家速度
            return;
        }
        //水平移动默认为匀加速运动
        //当按下疯狂键时，加速度为xSpeedV，反之为0.1
        //当按下疯狂键时，水平速度最大值为 xSpeedMax，反之为 xSpeedMax/3
        if (Keys.CRAZY.use() && playerState.mp.health()) {
            this.playerState.mp.subtract();//减少能量
            this.xSpeed += xAcceleration;
            if (this.xSpeed > xSpeedMax) {
                this.xSpeed = xSpeedMax;
            }
        } else {
            this.xSpeed += 0.1f;
            if (this.xSpeed > xSpeedMax / 3) {
                this.xSpeed = xSpeedMax / 3;
            }
            this.playerState.mp.add();//增加能量
        }

        //边距判定
        if (ElementBean.getBackground().sideJudge(this)) {
            //撞到墙面后往后退
            this.x += direction.left() ? 3 : -3;
            return;
        }
        //水平移动
        if (Keys.LEFT.use()) {
            if (this.x > 0) {
                this.x -= this.xSpeed;
            }
            this.direction = Direction.LEFT;//更改玩家方向
        } else if (Keys.RIGHT.use()) {
            //小于屏幕的一半时移动玩家，否则移动背景
            if (this.x <= Constant.FRAME_WIDTH / 2) {
                this.x += this.xSpeed;
            } else {
                //跟随玩家移动
                for (ElementBean bean : ElementBean.values()) {
                    bean.getService().movedByPlayer(this);
                }
                //奖励得分
                this.playerState.score.add((int) this.getxSpeed());
            }
            this.direction = Direction.RIGHT;//更改玩家方向
        }
        //当玩家速度达到最大值的1/2并且没有跳跃时播放
        if (this.xSpeed > xSpeedMax / 2 && !Keys.JUMP.use()) {
            carzyCount++;
            if (carzyCount > 10) {
                Audio.PlayerCrazy.play();
                carzyCount = -20;//为了保证玩家长按时能有间隔的发出音效，增加等待时间
            }
        } else {
            carzyCount = 0;
        }
    }

    @Override
    public void yMove() {
        if (Keys.JUMP.use()) {
            //在地面时,允许跳跃，设置初始向上加速度
            if (onTheGround()) {
                this.ySpeed = this.yAcceleration;
                Audio.PlayerJump.play();
            }
            //向上的加速度递减直到为零
            if (this.ySpeed > 0) {
                ySpeed -= 1;
            }
            //y轴移动当前垂直速度
            this.y -= ySpeed;
        } else {
            ySpeed = 0;
        }
    }

    @Override
    public void drawImage(Graphics g) {
        super.drawImage(g);
        this.playerState.drawImage(g);
    }

    @Override
    protected Image getImage() {
        return this.playerState.getImage(this);
    }

    /**
     * 游戏是否结束
     *
     * @return
     */
    public boolean gameOver() {
        if (this.y >= Constant.FRAME_HEIGHT) {
            //this.playerState.hp.setValue(0);//掉线悬崖直接死亡
            this.playerState.hp.subtract();
            this.y = -100;
            Audio.PlayerFall.play();
        }
        return !this.playerState.hp.health();
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
