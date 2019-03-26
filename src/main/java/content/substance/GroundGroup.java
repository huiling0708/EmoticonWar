package main.java.content.substance;

import main.java.auxiliary.generator.GroundType;
import main.java.base.BaseElement;
import main.java.base.BaseGravityElement;
import main.java.content.player.Player;

import java.awt.*;
import java.util.List;

/**
 * @author 左边牙齿疼
 * @Description: 地面组 由多个竖排构成的地面组合而成
 * @date 2019/3/23
 */
public class GroundGroup {

    private int endX, endY;//最后一块 地面绘制的x与y坐标
    private List<Ground> groundList;//地面组
    private GroundType groundType;//地面类型

    public GroundGroup(int endX, int endY, GroundType groundType, List<Ground> groundList) {
        this.endX = endX;
        this.endY = endY;
        this.groundList = groundList;
        this.groundType = groundType;
    }


    /**
     * 边距判定 是否碰触边距
     *
     * @param element
     * @return
     */
    public boolean sideJudge(BaseElement element) {
        for (Ground ground : groundList) {
            if (ground.intersects(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 地面判定 是否与地面接触
     *
     * @param element
     * @return
     */
    public boolean groundJudge(BaseGravityElement element) {
        for (Ground ground : groundList) {
            if (ground.aboveIntersects(element)) {
                //由于引力可能出现偏差值，在落地时为了保证所有元素距离地面相同，设定一个固定值
                element.setY(ground.getY() - 10);
                return true;
            }
        }
        return false;
    }

    public void drawImage(Graphics g) {
        this.groundList.forEach(ground -> ground.drawImage(g));
    }

    public void movedByPlayer(Player player) {
        groundList.forEach(ground -> ground.movedByPlayer(player));
        this.endX -= player.getxSpeed();
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
