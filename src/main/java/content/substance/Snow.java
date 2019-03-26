package main.java.content.substance;

import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.Direction;
import main.java.base.BaseElement;
import main.java.base.IElement;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
@IElement("snow.gif")
public class Snow extends BaseElement {
    public Snow() {
        this.leftImage = CommonUtils.getImage("snow.gif");
        int size = CommonUtils.nextInt(12, 32);
        this.width = size;
        this.height = size;
        this.y = -this.height;
        this.x = CommonUtils.nextInt(0, Constant.FRAME_WIDTH);
        this.xSpeed = size / 8;
    }

    @Override
    public void action() {
        this.y += this.xSpeed;
    }
}
