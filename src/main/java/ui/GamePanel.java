package main.java.ui;

import main.java.auxiliary.CommonUtils;
import main.java.base.IDraw;

import javax.swing.*;
import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 游戏面板
 * @date 2019/3/23
 */
public class GamePanel extends JPanel {

    private IDraw[] draws;//待绘制的元素
    private Image image;//缓冲
    private Image backgroundImage = CommonUtils.getImage("background.png");//背景图片

    public GamePanel(IDraw... draws) {
        this.draws = draws;
    }
    /**
     * 绘制缓冲
     */
    private void drawBufferedImage() {
        image = createImage(this.getWidth(), this.getHeight());
        Graphics g = image.getGraphics();
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        //绘制
        for (IDraw draw : this.draws) {
            draw.drawImage(g);
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBufferedImage();
        g.drawImage(image, 0, 0, this);
    }
}
