package main.java.auxiliary.generator;

import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.content.substance.GroundGroup;

/**
 * @author 左边牙齿疼
 * @Description: 地面生成器
 * @date 2019/3/23
 */
public class GroundGenerator {

    /**
     * 生成地面组
     *
     * @param initX
     * @param initY
     * @return
     */
    public static GroundGroup buildGroundGroup(int initX, int initY) {
        //当地面当前坐标与地面基线偏移较大时，恢复基准线的直线地面
        if (initY > Constant.GROUND_LEVEL_BASE_LINE + 100
                || initY < Constant.GROUND_LEVEL_BASE_LINE - 150) {
            return GroundType.STRAIGHT_LINE.build(initX, Constant.GROUND_LEVEL_BASE_LINE);
        }
        //根据挑选的类型生成地面
        GroundType type = chooseGroundType(initY);
        return type.build(initX, initY);
    }

    /**
     * 选择地面类型
     *
     * @param initY
     * @return
     */
    public static GroundType chooseGroundType(int initY) {
        int i = CommonUtils.nextInt(0, GroundType.values().length);
        GroundType type = GroundType.values()[i];
        if (Constant.GROUND_LEVEL_BASE_LINE == initY) {
            return type;
        }
        //如果当前坐标大于基准线，则不能继续往下
        //如果当前坐标小于基准线，则不能继续往上
        if ((initY > Constant.GROUND_LEVEL_BASE_LINE
                && GroundLocation.DOWN.equals(type.getLocation()))
                || (initY < Constant.GROUND_LEVEL_BASE_LINE
                && GroundLocation.UP.equals(type.getLocation()))) {
            return chooseGroundType(initY);
        }
        return type;
    }
}
