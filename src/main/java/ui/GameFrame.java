package main.java.ui;

import main.java.auxiliary.*;
import main.java.content.GameContent;
import main.java.content.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author 左边牙齿疼
 * @Description: 游戏主窗体
 * @date 2019/3/23
 */
public class GameFrame extends JFrame {

    public GameFrame() {
        this.setTitle("LET YOU EAT SHIT");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);//固定窗体
        //窗体居中
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = new Dimension(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;
        this.setBounds((int) (width - size.getWidth()) / 2,
                (int) (height - size.getHeight()) / 3, (int) size.getWidth(), (int) size.getHeight());

        //初始化游戏内容
        this.init();

    }

    private void init() {
        ElementBean.init();//初始化游戏服务
        //玩家
        Player player = new Player();
        //游戏内容
        GameContent gameContent = new GameContent(player);
        //画板
        GamePanel panel = new GamePanel(gameContent, player);
        //显示窗体
        this.add(panel);
        this.setVisible(true);

        //更新面板内容
        CommonUtils.task(5, () -> {
            panel.repaint();
            if (player.gameOver()) {
                Constant.TIMER_STOP_ON_OFF = true;
                Audio.GameOver.play();
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.dispose();
                System.exit(0);//此处可以替换为打开新的窗体等
            }
        });

        //玩家键盘监听
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Keys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Keys.remove(e.getKeyCode());
            }
        });
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
