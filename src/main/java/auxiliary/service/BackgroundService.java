package main.java.auxiliary.service;

import main.java.auxiliary.Constant;
import main.java.auxiliary.generator.GroundGenerator;
import main.java.base.BaseElement;
import main.java.base.BaseGravityElement;
import main.java.base.BaseService;
import main.java.content.player.Player;
import main.java.content.substance.GroundGroup;

import java.awt.*;

/**
 * @author 左边牙齿疼
 * @Description: 游戏背景服务
 * @date 2019/3/23
 */
public class BackgroundService extends BaseService<GroundGroup> {

    /**
     * 初始化背景
     */
    @Override
    public void init() {
        //添加背景缓存
        this.addGroundCache(0, 0, Constant.GROUND_LEVEL_BASE_LINE);//从地面基线开始画起
    }

    /**
     * 添加地图缓存
     *
     * @param i     当前个数
     * @param lastX 上一块地面的X坐标
     * @param lastY 上一块地面的Y坐标
     */
    private void addGroundCache(int i, int lastX, int lastY) {
        //让缓存中始终保持固定个地面组数
        if (i < Constant.GROUND_CACHE_COUNT) {
            GroundGroup groundGroup = GroundGenerator.buildGroundGroup(lastX, lastY);
            this.add(groundGroup);
            this.addGroundCache(i + 1, groundGroup.getEndX(), groundGroup.getEndY());
        }
    }

    @Override
    public void movedByPlayer(Player player) {
        this.getElementList().forEach(i -> {
            //如果地图已经离开屏幕左侧，则移除
            if (i.getEndX() < 0) {
                this.remove(i);
            }
            i.movedByPlayer(player);
        });
        //补齐缓存地图
        int size = this.getElementList().size();
        GroundGroup groundGroup = this.getElementList().get(size - 1);
        this.addGroundCache(size, groundGroup.getEndX(), groundGroup.getEndY());
    }

    @Override
    public void drawImage(Graphics g) {
        this.getElementList().forEach(i -> i.drawImage(g));
    }

    /**
     * 边距判定
     *
     * @param element
     * @return
     */
    public boolean sideJudge(BaseElement element) {
        for (GroundGroup group : this.getElementList()) {
            if (group.sideJudge(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 地面判定
     *
     * @param element
     */
    public void groundJudge(BaseGravityElement element) {
        for (GroundGroup i : this.getElementList()) {
            if (i.groundJudge(element)) {
                //设置元素站住地面上
                element.setOnTheGround(true);
                return;
            }
        }
        element.setOnTheGround(false);
    }
}
