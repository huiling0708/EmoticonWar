package main.java.auxiliary;

import main.java.content.player.Player;
import main.java.content.substance.Prop;

/**
 * @author 左边牙齿疼
 * @Description: 道具类型
 * @date 2019/3/24
 */
public enum PropType {
    BEER("beer.gif") {
        @Override
        public void effect(Player player) {
            //啤酒加能量上限
            player.getPlayerState().getMp().addMaxValue(50);
            player.getPlayerState().getMp().toMax();
            player.getPlayerState().getScore().add(2000);
        }
    },
    COFFEE("coffee.gif") {
        @Override
        public void effect(Player player) {
            player.getPlayerState().getHp().add();
            player.getPlayerState().getShit().addMaxValue(10);
            player.getPlayerState().getShit().add(10);
            player.getPlayerState().getScore().add(500);
        }
    },
    RICE("rice.gif") {
        @Override
        public void effect(Player player) {
            player.getPlayerState().getHp().add();
            player.getPlayerState().getCandy().add();
            player.getPlayerState().getShit().add(20);
            player.getPlayerState().getMp().toMax();
            player.getPlayerState().getScore().add(1500);
        }
    },
    CANDY("candy.gif") {
        @Override
        public void effect(Player player) {
            player.getPlayerState().getCandy().addMaxValue(1);
            player.getPlayerState().getCandy().add();
            player.getPlayerState().getScore().add(1500);
        }
    };

    PropType(String iamgeName) {
        this.iamgeName = iamgeName;
    }

    public static void createProp(int x, int y) {
        PropType[] values = PropType.values();
        int i = CommonUtils.nextInt(0, values.length + 4);
        if (i >= values.length) {
            return;
        }
        ElementBean.Substance.getService().add(
                new Prop(x, y, values[i]));
    }

    private String iamgeName;

    public abstract void effect(Player player);

    public String getIamgeName() {
        return iamgeName;
    }
}
