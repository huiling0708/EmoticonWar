package main.java.content.enemy;

import main.java.base.IElement;
import main.java.content.enemy.base.Enemy;
import main.java.content.enemy.base.IEnemy;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
@IEnemy(probability = 70, number = 3, score = 0)
@IElement(leftImage = "enemyJumpLeft.gif", rightImage = "enemyJumpRight.gif",
        ySpeed = 60, rewardScore = 300)
public class JumpEnemy extends Enemy {

    public JumpEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected void yMove() {
        if (onTheGround()) {
            this.y -= this.ySpeed;
        }
    }

    @Override
    public float getQuality() {
        return 35;
    }
}
