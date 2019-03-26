package main.java.content.substance;

import main.java.auxiliary.*;
import main.java.base.BaseElement;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 道具
 * @date 2019/3/24
 */
public class Prop extends BaseElement {

    public Prop(int x, int y, PropType propType) {
        super();
        this.leftImage = CommonUtils.getImage(propType.getIamgeName());
        this.x = x;
        this.y = y;
        this.propType = propType;
        this.xSpeed = CommonUtils.nextInt(3, 10);
        this.ySpeed = this.xSpeed;
        verticalDirection = Direction.UP;
    }

    private PropType propType;//道具类型
    private Direction verticalDirection;

    @Override
    protected void xMove() {
        if (direction.left()) {
            if (this.x > 0) {
                this.x -= xSpeed;
            } else {
                this.direction = Direction.RIGHT;
                this.xSpeed++;
            }
        } else {
            if (this.x < Constant.FRAME_WIDTH) {
                this.x += xSpeed;
            } else {
                this.direction = Direction.LEFT;
                this.xSpeed++;
            }
        }
    }

    @Override
    protected void yMove() {
        if (Direction.UP.equals(verticalDirection)) {
            if (this.y > 0) {
                this.y -= ySpeed;
            } else {
                verticalDirection = Direction.DOWN;
                this.ySpeed++;
            }
        } else {
            if (this.y < Constant.FRAME_HEIGHT - 100) {
                this.y += ySpeed;
            } else {
                verticalDirection = Direction.UP;
                this.ySpeed++;
            }
        }
    }

    @Override
    public boolean encounterPlayer(Player player) {
        if (this.intersects(player)) {
            this.propType.effect(player);
            Audio.AddProp.play();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Player player) {
        return this.xSpeed > 18 || this.ySpeed > 18;
    }

    @Override
    public void drawImage(Graphics g) {
        super.drawImage(g);
        g.setColor(Color.WHITE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    @Override
    protected Image getImage() {
        return this.leftImage;
    }
}
