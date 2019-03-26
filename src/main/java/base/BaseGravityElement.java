package main.java.base;


/**
 * @author 左边牙齿疼
 * @Description: 受重力影响的基础元素
 * @date 2019/3/23
 */
public abstract class BaseGravityElement extends BaseElement implements IGravity {

    protected boolean onTheGround;//是否在地面上

    public BaseGravityElement(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean onTheGround() {
        return this.onTheGround;
    }

    public void setOnTheGround(boolean onTheGround) {
        this.onTheGround = onTheGround;
    }

    @Override
    public float getQuality() {
        return 100;
    }
}
