package main.java.auxiliary.generator;

import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.ElementBean;
import main.java.content.substance.Ground;
import main.java.content.substance.GroundGroup;
import main.java.content.substance.trap.Penknife;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 左边牙齿疼
 * @Description: 地面类型
 * @date 2019/3/23
 */
public enum GroundType {
    //直线 Y 坐标没有变化
    STRAIGHT_LINE(5, 35, GroundLocation.UNCHANGED),
    //小平台
    PLATFORM(3, 18, GroundLocation.UP) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            if (count == 0) {
                //绘制一个 3-9 高度的平台
                return initY - (CommonUtils.nextInt(3, 9) * Constant.ELEMENT_SIZE);
            } else if (count == distance - 1) {
                return initY;
            }
            return lastY;
        }
    },
    //向上台阶
    UPWARD_STEPS(3, 8, GroundLocation.UP) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return lastY - Constant.ELEMENT_SIZE;
        }
    },
    //向下台阶
    DOWN_STEPS(2, 8, GroundLocation.DOWN) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return lastY + Constant.ELEMENT_SIZE;
        }
    },
    //直接死亡坑
    DEATH_PIT(3, 15, GroundLocation.UNCHANGED) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return initY + Constant.FRAME_HEIGHT;
        }
    },
    //陷阱坑
    TRAP_PIT(5, 12, GroundLocation.DOWN) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            if (count == 0) {
                //绘制一个 2-6 深度的陷阱
                return lastY + (CommonUtils.nextInt(2, 6) * Constant.ELEMENT_SIZE);
            } else if (count == distance - 1) {
                return initY;
            }
            ElementBean.Substance.getService().add(new Penknife(lastX - 8, lastY - 18));
            return lastY;
        }
    },
    //长陷阱坑
    LONG_TRAP_PIT(6, 18, GroundLocation.DOWN) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            if (count == 0) {
                //绘制一个 3-8 深度的陷阱
                return lastY + (CommonUtils.nextInt(3, 8) * Constant.ELEMENT_SIZE);
            } else if (count == distance - 1) {
                return initY;
            }
            ElementBean.Substance.getService().add(new Penknife(lastX - 8, lastY - 18));
            return lastY;
        }
    },
    //一坑一线
    ONE_PIT_ON_LINE(10, 20, GroundLocation.UNCHANGED) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return count % 2 == 0 ? initY : initY + Constant.FRAME_HEIGHT;
        }
    },
    //余数3
    REMAINDER_OF_3(15, 35, GroundLocation.UNCHANGED) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return count % 3 == 0 ? initY : initY + Constant.FRAME_HEIGHT;
        }
    },
    //乱七八糟
    AT_SIXES_AND_SEVENS(10, 20, GroundLocation.UNCHANGED) {
        @Override
        public int handleY(int count, int distance, int lastX, int lastY, int initY) {
            return CommonUtils.nextInt(Constant.GROUND_LEVEL_BASE_LINE - 150, Constant.GROUND_LEVEL_BASE_LINE + 150);
        }
    };

    GroundType(int minDistance, int maxDistance, GroundLocation location) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.location = location;
    }

    private int minDistance;//最小水平距离 表示最小的组合个数
    private int maxDistance;//最大水平距离 表示最大的组合个数
    private GroundLocation location;//地面朝向位置


    /**
     * 生成
     *
     * @param initX 初始X 坐标
     * @param initY 初始Y 坐标
     * @return
     */
    public GroundGroup build(int initX, int initY) {
        //绘制距离
        int distance = CommonUtils.nextInt(minDistance, maxDistance);
        int count = 0;//已绘制个数

        List<Ground> groundList = new ArrayList<>();
        int lastY = initY;
        while (count < distance) {
            //处理y 轴坐标
            lastY = handleY(count, distance, initX, lastY, initY);
            Ground ground = new Ground(initX, lastY);
            //x 坐标平移元素大小
            initX += ground.getWidth();
            groundList.add(ground);
            count++;
        }
        return new GroundGroup(initX, lastY, this, groundList);
    }

    /**
     * 处理 y 坐标
     *
     * @param lastY
     */
    protected int handleY(int count, int distance, int lastX, int lastY, int initY) {
        return initY;
    }

    public GroundLocation getLocation() {
        return location;
    }
}
