package main.java.content.substance.trap;

import main.java.base.BaseElement;
import main.java.base.IElement;
import main.java.base.IHurtPlayer;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
@IElement(value = "penknife.gif", width = 36, height = 36)
public class Penknife extends BaseElement implements IHurtPlayer {
    public Penknife(int x, int y) {
        super(x, y);
    }

    @Override
    public void action() {

    }
}
