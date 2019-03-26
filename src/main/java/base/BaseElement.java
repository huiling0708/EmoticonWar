package main.java.base;

import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.Direction;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 基础元素
 * @date 2019/3/23
 */
public abstract class BaseElement implements IDraw {

    protected int x, y;//坐标
    protected int width, height;//大小
    protected Image leftImage;//绘制图像 朝向为左
    protected Image rightImage;//扩展图片 朝向为右边

    protected Direction direction;//方向
    protected float xSpeed, ySpeed;//x轴与y轴的移动速度


    public BaseElement() {
        this.width = Constant.ELEMENT_SIZE;
        this.height = Constant.ELEMENT_SIZE;
        this.direction = Direction.LEFT;
    }

    public BaseElement(int x, int y) {
        IElement ann = this.getClass().getAnnotation(IElement.class);
        this.width = ann.width();
        this.height = ann.height();
        this.direction = ann.direction();
        this.x = x;
        this.y = y;
        //图片处理
        String imageName = ann.value().equals(IElement.NOTHING) ? ann.leftImage() : ann.value();
        this.leftImage = CommonUtils.getImage(imageName);
        this.rightImage = ann.rightImage().equals(IElement.NOTHING) ? leftImage : CommonUtils.getImage(ann.rightImage());
    }

    /**
     * 动作前判定 服务列表中调用 通过重写该方法，为元素的某个动作进行判断
     *
     * @return
     */
    public boolean beforeActionJudge() {
        return true;
    }

    /**
     * 遇到边距或墙面处理 结束之后是否移除
     *
     * @param
     */
    public void encounterSide() {
    }

    /**
     * 遇到玩家处理 结束之后是否移除
     *
     * @param player
     */
    public boolean encounterPlayer(Player player) {
        return false;
    }

    /**
     * 动作
     */
    public void action() {
        this.xMove();
        this.yMove();
    }

    /**
     * 水平移动
     */
    protected void xMove() {
        this.x += direction.right() ? xSpeed : -xSpeed;
    }

    /**
     * 垂直移动
     */
    protected void yMove() {
        this.y -= this.ySpeed;
    }

    /**
     * 获取自身矩形
     *
     * @return
     */
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    /**
     * 是否与指矩形相交
     *
     * @param element
     * @return
     */
    public <E extends BaseElement> boolean intersects(E element) {
        return this.getRectangle().intersects(element.getRectangle());
    }

    /**
     * 某元素是否在我的上方与我相交
     *
     * @param element
     * @return
     */
    public <E extends BaseElement> boolean aboveIntersects(E element) {
        Rectangle myself = this.getRectangle();
        Rectangle be = element.getRectangle();
        return be.getY() < myself.getY() && myself.intersects(be);
    }

    /**
     * 当元素满足自身离开条件时,从服务列表移除
     */
    public boolean remove(Player player) {
        return false;
    }


    @Override
    public void drawImage(Graphics g) {
        g.drawImage(this.getImage(), this.x, this.y, this.width, this.height, null);
    }

    protected Image getImage() {
        return direction.right() ? rightImage : leftImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getWidth() {
        return width;
    }

    public Direction getDirection() {
        return direction;
    }
}
