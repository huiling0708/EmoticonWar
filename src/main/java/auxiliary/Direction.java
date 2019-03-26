package main.java.auxiliary;

/**
 * @author 左边牙齿疼
 * @Description: 方向
 * @date 2019/3/23
 */
public enum Direction {
    LEFT {
        @Override
        public boolean left() {
            return true;
        }
    },
    RIGHT {
        @Override
        public boolean right() {
            return true;
        }
    },
    UP,
    DOWN;

    public boolean left() {
        return false;
    }

    public boolean right() {
        return false;
    }
}
