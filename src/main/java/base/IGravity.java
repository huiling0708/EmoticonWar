package main.java.base;

/**
 * @author 左边牙齿疼
 * @Description: 重力接口
 * @date 2019/3/23
 */
public interface IGravity {

    /**
     * 是否在地面上
     *
     * @return
     */
    boolean onTheGround();

    /**
     * 物体质量
     */
    float getQuality();

    /**
     * 获取 Y 坐标
     *
     * @return
     */
    int getY();

    /**
     * 设置 Y 坐标
     *
     * @param y
     */
    void setY(int y);
}
