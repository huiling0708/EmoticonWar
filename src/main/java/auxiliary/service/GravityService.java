package main.java.auxiliary.service;

import main.java.base.BaseService;
import main.java.base.IGravity;

/**
 * @author 左边牙齿疼
        * @Description: 重力服务
        * @date 2019/3/23
        */
public class GravityService extends BaseService<IGravity> {
    private final static float g = 0.098f;//比例系数
    /**
     * 万有引力
     */
    public void universalGravitation() {
        this.getElementList().forEach(gravity -> {
            if (!gravity.onTheGround()) {
                float G = g * gravity.getQuality();
                gravity.setY((int) (gravity.getY() + G));
            }
        });
    }
}
