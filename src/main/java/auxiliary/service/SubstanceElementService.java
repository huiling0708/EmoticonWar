package main.java.auxiliary.service;

import main.java.auxiliary.CommonUtils;
import main.java.base.BaseElement;
import main.java.base.ElementService;
import main.java.content.player.Player;
import main.java.content.substance.Snow;
import main.java.base.IHurtPlayer;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
public class SubstanceElementService extends ElementService {

    public SubstanceElementService() {
        //添加雪花
        CommonUtils.task(3 * 1000, () -> {
            this.add(new Snow());
        });
    }

    @Override
    protected boolean encounterPlayer(BaseElement element, Player player) {
        if (element instanceof IHurtPlayer) {//陷阱统一处理
            if (element.intersects(player)) {
                player.beHurt();
                return true;
            }
        }
        return super.encounterPlayer(element, player);
    }
}
