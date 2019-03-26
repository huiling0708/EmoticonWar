package main.java.content.player;

import main.java.auxiliary.Audio;
import main.java.auxiliary.Constant;
import main.java.auxiliary.ElementBean;
import main.java.base.BaseElement;
import main.java.base.IElement;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
@IElement(value = "candy.gif", xSpeed = 0)
public class PlayerCandy extends BaseElement {

    public PlayerCandy(Player player) {
        super(player.getX() + Constant.ELEMENT_SIZE, player.getY());
        ElementBean.Substance.getService().add(new PlayerHelp(this));
        Audio.PlayerCandy.play();
        Audio.HelpComing.play();
    }

    protected boolean remove;

    @Override
    public boolean remove(Player player) {
        return this.remove;
    }
}
