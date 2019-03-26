package main.java.auxiliary;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/24
 */
public enum Audio {
    BGM("bgm.wav"),
    GameOver("gameover.wav"),
    PlayerJump("jump2.wav"),
    PlayerCandy("playerCandy.wav"),
    HelpComing("helpComing2.wav"),
    PlayerFist("fist.wav"),
    PlayerFall("playerFall.wav"),//掉下悬崖
    FistHurt("fistHurt.wav"),
    PlayerCrazy("playerCrazy3.wav"),
    PlayerBeHurt("beHurt.wav"),
    StepHelpGirl("stepHelpGirl.wav"),//
    PlayerFly("playerFly.wav"),//玩家踩妹子飞
    AddProp("addProp.wav"),//加道具
    EnemyVomit("vomit.wav"),//敌人呕吐
    EnemyDeath("enemyDeath.wav"),//敌人呕吐
    SinisterSmile("sinisterSmile.wav"),//奸笑
    BombBlast("bombBlast.wav"),//炸弹爆炸
    KickBombs("kickBombs2.wav"),//踢炸弹
    EnemyThrowLightning("throwLightning.wav"),//敌人扔闪电
    EnemyThrowBomb("throwBomb.wav"),//敌人扔炸弹
    EnemyPlayFootball("playFootball.wav"),//敌人扔炸弹
    PickShit("pickShit.wav"),//捡到大便
    ThrowShit("shit2.wav");

    Audio(String name) {
        this.name = name;
    }

    private String name;

    public void play() {
        AudioStream as = null;
        try {
            InputStream resourceAsStream = Audio.class.getClassLoader().getResourceAsStream("main/resources/audio/" + name);
            as = new AudioStream(resourceAsStream);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
