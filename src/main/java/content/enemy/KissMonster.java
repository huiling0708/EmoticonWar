package main.java.content.enemy;

import main.java.base.IElement;
import main.java.content.enemy.base.Enemy;
import main.java.content.enemy.base.IEnemy;

/**
 * @author 左边牙齿疼
 * @Description: 亲嘴怪
 * @date 2019/3/24
 */
@IEnemy(probability = 80, number = 6, score = 0)
@IElement(
        leftImage = "kissMonsterLeft.gif",
        rightImage = "kissMonsterRight.gif")
public class KissMonster extends Enemy {

    public KissMonster(int x, int y) {
        super(x, y);
    }
}
