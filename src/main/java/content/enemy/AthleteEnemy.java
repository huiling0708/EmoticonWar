package main.java.content.enemy;

import main.java.auxiliary.Audio;
import main.java.auxiliary.ElementBean;
import main.java.base.IElement;
import main.java.content.enemy.base.CountEnemy;
import main.java.content.enemy.base.IEnemy;
import main.java.content.substance.trap.Football;

/**
 * @author 左边牙齿疼
 * @Description: 踢足球的骚年
 * @date 2019/3/24
 */
@IEnemy(probability = 40, number = 1, score = 3000)
@IElement(leftImage = "athlete.gif", xSpeed = 0, rewardScore = 500)
public class AthleteEnemy extends CountEnemy {

    public AthleteEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected void doSomething() {
        ElementBean.Substance.getService().add(new Football(this));//添加一个足球
        Audio.EnemyPlayFootball.play();//播放踢足球音效
    }

    @Override
    protected int getCountMax() {
        return 50;
    }
}
