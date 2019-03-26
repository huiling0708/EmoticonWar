package main.java.content;

import main.java.auxiliary.Audio;
import main.java.auxiliary.CommonUtils;
import main.java.auxiliary.ElementBean;
import main.java.auxiliary.generator.EnemyGenerator;
import main.java.base.ElementService;
import main.java.base.IDraw;
import main.java.content.player.Player;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 游戏内容
 * @date 2019/3/23
 */
public class GameContent implements IDraw {

    public GameContent(Player player) {

        //把玩家加入重力集合中
        ElementBean.getGravity().add(player);
        //刷新动作内容
        CommonUtils.task(25, () -> {
            player.action();//玩家动作
            this.wholeAction(player);//游戏整体动作
            ElementBean.getGravity().universalGravitation();//万有引力
        });
        //每单位1进行一次玩家的地板判定
        CommonUtils.task(1, () -> ElementBean.getBackground().groundJudge(player));
        //生成敌人
        CommonUtils.task(1000, () -> this.buildEnemy(player));
        //AudioPlayer循环播放文件 不能大于1048576个字节，详情见 AudioStream 中的 getData 方法
        //可以引入其它jar包用于播放背景音乐
        CommonUtils.task(30 * 1000, () -> {
            Audio.BGM.play();
            player.getPlayerState().getGameLevel().add();
        });
    }

    private int buildEnemyCount = 0;//生成敌人计数

    /**
     * 生成敌人 当敌人计数大于 10 - 游戏等级时，才生成一批敌人
     *
     * @param player
     */
    private void buildEnemy(Player player) {
        buildEnemyCount++;
        if (buildEnemyCount > 10 - player.getPlayerState().getGameLevel().getValue()) {
            EnemyGenerator.buildEnemy(player);
            buildEnemyCount = 0;
        }
    }

    /**
     * 整体动作
     */
    public void wholeAction(Player player) {
        //玩家
        ElementService playerService = (ElementService) ElementBean.Player.getService();
        playerService.action(player);
        //物质
        ElementService substanceService = (ElementService) ElementBean.Substance.getService();
        substanceService.action(player);
        //敌人
        ElementService enemyService = (ElementService) ElementBean.Enemy.getService();
        enemyService.action(player, playerService, substanceService);
    }

    @Override
    public void drawImage(Graphics g) {
        for (ElementBean bean : ElementBean.values()) {
            bean.getService().drawImage(g);
        }
    }
}
