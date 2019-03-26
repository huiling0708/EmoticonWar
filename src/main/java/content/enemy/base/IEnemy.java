package main.java.content.enemy.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 左边牙齿疼
 * @Description: 敌人生成属性
 * @date 2019/3/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IEnemy {

    int probability();//概率

    int number();//人数

    int score();//分数

}
