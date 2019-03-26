package main.java.auxiliary.generator;


import main.java.auxiliary.ClassLoaderUtils;
import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.Constant;
import main.java.auxiliary.ElementBean;
import main.java.content.enemy.base.Enemy;
import main.java.content.enemy.base.IEnemy;
import main.java.content.player.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 左边牙齿疼
 * @Description: 敌人生成器
 * @date 2019/3/24
 */
public abstract class EnemyGenerator {

    private final static String ENEMY_PACKAGE = "main.java.content.enemy";//敌人包路径
    private static List<EnemyGeneratorProperty> enemyGeneratorProperties;//敌人生成属性


    /**
     * 生成敌人
     *
     * @param player
     */
    public static void buildEnemy(Player player) {
        int x = CommonUtils.nextInt(Constant.FRAME_WIDTH - 200, Constant.FRAME_WIDTH);//出现范围
        int y = player.getY() - 100;
        while (!buildEnemy(x, y, player)) ;
    }

    /**
     * 加载敌机类
     */
    private static void loadEnemyGeneratorProperty() {
        Set<Class<?>> classes = ClassLoaderUtils.loadClassByPackage(ENEMY_PACKAGE);
        classes.forEach(classType -> {
            if (!Enemy.class.isAssignableFrom(classType)) {
                return;//是否时是敌机子类
            }
            IEnemy ann = classType.getAnnotation(IEnemy.class);
            if (ann == null) {
                return;//无敌机注解
            }
            Constructor<? extends Enemy> constructor;
            try {
                constructor = (Constructor<? extends Enemy>) classType.getConstructor(int.class, int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.out.println("获取" + classType.getTypeName() + "构造器异常");
                return;
            }
            enemyGeneratorProperties.add(new EnemyGeneratorProperty(ann, classType.getSimpleName(), constructor));
        });
    }

    /**
     * 获取敌机生成属性
     *
     * @return
     */
    private static List<EnemyGeneratorProperty> getEnemyGeneratorProperties() {
        if (enemyGeneratorProperties == null) {
            synchronized (EnemyGenerator.class) {
                if (enemyGeneratorProperties == null) {
                    enemyGeneratorProperties = new ArrayList<>();
                    loadEnemyGeneratorProperty();
                }
            }
        }
        return enemyGeneratorProperties;
    }

    /**
     * 生成敌人
     *
     * @param player
     */
    private static boolean buildEnemy(int x, int y, Player player) {
        List<EnemyGeneratorProperty> propertyList = getEnemyGeneratorProperties();
        int i = CommonUtils.nextInt(0, propertyList.size());
        EnemyGeneratorProperty property = propertyList.get(i);
        //分数是否达到
        if (player.getPlayerState().getScore().getValue() < property.getScore()) {
            return false;
        }
        //概率
        int nextInt = CommonUtils.nextInt(1, 100);
        if (property.getProbability() < nextInt) {
            return false;
        }
        //出怪等级
        build(property, x, y, player.getPlayerState().getGameLevel().getValue());
        return true;
    }

    /**
     * 生成敌人
     *
     * @param x
     * @param y
     */
    private static void build(EnemyGeneratorProperty property, int x, int y, int grade) {
        int distance = CommonUtils.nextInt(20, 80);//间隔距离
        int count = CommonUtils.nextInt(1, property.getNumber() + grade);//个数
        createEnemy(property, count, distance, x, y);
    }

    /**
     * 创建敌人
     *
     * @param count
     * @param distance
     * @param x
     * @param y
     */
    private static void createEnemy(EnemyGeneratorProperty property, int count, int distance, int x, int y) {
        if (count == 0) {
            return;
        }
        Enemy enemy;
        try {
            enemy = property.getConstructor().newInstance(x, y);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取" + property.getClassName() + "实例异常");
        }
        ElementBean.Enemy.getService().add(enemy);
        //间隔距离
        x += distance;
        count--;
        createEnemy(property, count, distance, x, y);
    }
}
