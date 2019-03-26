package main.java.base;

import main.java.auxiliary.Constant;
import main.java.auxiliary.Direction;

import java.lang.annotation.*;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IElement {
    String NOTHING = "NOTHING";

    String value() default NOTHING;//图片

    String leftImage() default NOTHING;//左边图片

    String rightImage() default NOTHING;//右边图片

    int width() default Constant.ELEMENT_SIZE;//宽度

    int height() default Constant.ELEMENT_SIZE;//高度

    Direction direction() default Direction.LEFT;//方向

    //以下属性只在敌机的子类读取
    int hp() default 50;//血量

    int defense() default 30;//防御力

    float xSpeed() default 3;//水平速度

    float ySpeed() default 0;//垂直速度

    int rewardScore() default 200;//防御力
}
