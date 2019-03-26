package main.java.auxiliary.generator;

import main.java.content.enemy.base.Enemy;
import main.java.content.enemy.base.IEnemy;

import java.lang.reflect.Constructor;

/**
 * @author 左边牙齿疼
 * @Description: 敌人生成属性
 * @date 2019/3/24
 */
public class EnemyGeneratorProperty {

    private int probability;//概率
    private int number;//人数
    private int score;//分数
    private String className;//类名
    private Constructor<? extends Enemy> constructor;//类构造器

    public EnemyGeneratorProperty(IEnemy enemyAnn, String className, Constructor<? extends Enemy> constructor) {
        this.probability = enemyAnn.probability();
        this.number = enemyAnn.number();
        this.score = enemyAnn.score();
        this.constructor = constructor;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public int getProbability() {
        return probability;
    }

    public int getNumber() {
        return number;
    }

    public int getScore() {
        return score;
    }

    public Constructor<? extends Enemy> getConstructor() {
        return constructor;
    }
}
