package main.java.content.substance;

import main.java.auxiliary.Constant;
import main.java.base.BaseElement;
import main.java.base.IElement;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 地面 从下往上绘制多张礼物盒图片拼凑而成
 * @date 2019/3/23
 */
@IElement("groud.gif")
public class Ground extends BaseElement{

    private int thickness = 15;

    public Ground(int x, int y) {
        super(x, y);
    }

    @Override
    public void drawImage(Graphics g) {
        int i = Constant.FRAME_HEIGHT;//从屏幕最下面开始画
        while (i >= this.y) {
            g.drawImage(this.leftImage, this.x, i, this.width, this.height, null);
            i -= 10;//让盒子叠在一起
        }
    }

    @Override
    public <E extends BaseElement> boolean aboveIntersects(E element) {
        Rectangle myself = new Rectangle(this.x + 3, y, this.height - 8, Constant.FRAME_HEIGHT - thickness);
        Rectangle be = element.getRectangle();
        return be.getY() < myself.getY() && myself.intersects(be);
    }

    @Override
    public Rectangle getRectangle() {
        //地面矩形范围 减去地面厚度 ，延申至地下
        return new Rectangle(this.x + 3, y + thickness, this.height - 8, Constant.FRAME_HEIGHT);
    }

    /**
     * 跟随玩家移动
     *
     * @param player
     */
    public void movedByPlayer(Player player) {
        this.x -= player.getxSpeed();
    }
}
