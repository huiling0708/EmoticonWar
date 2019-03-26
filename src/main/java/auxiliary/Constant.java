package main.java.auxiliary;

/**
 * @author 左边牙齿疼
 * @Description: 常量
 * @date 2019/3/23
 */
public abstract class Constant {

    public static boolean TIMER_STOP_ON_OFF = false;//定时器结束总开关，打开时停止所有定时器的刷新

    public final static int ELEMENT_SIZE = 24;//素材原始尺寸
    public final static int FRAME_WIDTH = ELEMENT_SIZE * 45;//主窗体宽
    public final static int FRAME_HEIGHT = ELEMENT_SIZE * 25;//主窗体高
    public final static String RESOURCES_PATH = "src/main/resources/image/";//资源加载路径头

    public final static int GROUND_CACHE_COUNT = 10;//地面组缓存个数
    public final static int GROUND_LEVEL_BASE_LINE = 350;//地面基础线


}
