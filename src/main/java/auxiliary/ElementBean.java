package main.java.auxiliary;

import main.java.base.IBaseService;
import main.java.auxiliary.service.*;

/**
 * @author 左边牙齿疼
 * @Description: 元素服务单例
 * @date 2019/3/23
 */
public enum ElementBean {

    Background(new BackgroundService()),//背景
    Gravity(new GravityService()),//重力
    Player(new PlayerElementService()),//玩家元素
    Enemy(new EnemyElementService()),//敌人元素
    Substance(new SubstanceElementService());//物质元素

    private ElementBean(IBaseService service) {
        this.service = service;
    }

    private IBaseService service;

    public IBaseService getService() {
        return service;
    }

    public static void init() {
        Constant.TIMER_STOP_ON_OFF = false;//初始化timer 开关
        for (ElementBean bean : ElementBean.values()) {
            bean.getService().init();
        }
    }

    /**
     * 获取地面
     *
     * @return
     */
    public static BackgroundService getBackground() {
        return (BackgroundService) ElementBean.Background.getService();
    }

    /**
     * 获取重力
     *
     * @return
     */
    public static GravityService getGravity() {
        return (GravityService) ElementBean.Gravity.getService();
    }
}
