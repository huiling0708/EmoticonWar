package main.java.base;

import main.java.auxiliary.Constant;
import main.java.auxiliary.ElementBean;
import main.java.content.player.Player;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
public abstract class ElementService<T extends BaseElement> extends BaseService<T> {

    /**
     * 自身动作处理
     *
     * @param player
     * @param services
     * @param <S>
     */
    public final <S extends BaseElement> void action(Player player, ElementService<S>... services) {
        this.getElementList().forEach(element -> {
            //根据元素自身判断其离开条件
            if (this.removeElement(element, player)) {
                this.remove(element);
                return;
            }
            //引力
            if (element instanceof IGravity) {
                BaseGravityElement gravityElement = (BaseGravityElement) element;
                ElementBean.getBackground().groundJudge(gravityElement);
            }
            //边距判定
            boolean sideJudge = ElementBean.getBackground().sideJudge(element);
            if (sideJudge) {
                element.encounterSide();
            }
            //前置操作
            if (!element.beforeActionJudge()) {
                return;
            }
            //与玩家相遇
            if (this.encounterPlayer(element, player)) {
                this.remove(element);
                return;
            }
            //与其它元素相交处理
            Rectangle myself = element.getRectangle();
            for (ElementService service : services) {
                //获取交互元素服务列表
                CopyOnWriteArrayList<S> elementList = service.getElementList();
                elementList.forEach(serviceElement -> {
                    //如果与矩形相交
                    if (myself.intersects(serviceElement.getRectangle())) {
                        //相交处理 处理结束之后是否把对方从对方的元素列表中移除
                        boolean removeOther = this.intersectsHandle(element, serviceElement);
                        if (removeOther) {
                            service.remove(serviceElement);
                        }
                    }
                });
            }
            //动作
            element.action();
        });
    }

    /**
     * 与玩家相遇之后
     *
     * @param element
     * @param player
     * @return
     */
    protected boolean encounterPlayer(T element, Player player) {
        return element.encounterPlayer(player);
    }

    /**
     * 相交处理 结束之后是否移除对方
     *
     * @param myself
     * @param other
     * @param <S>
     */
    protected <S extends BaseElement> boolean intersectsHandle(T myself, S other) {
        return false;
    }

    /**
     * 移除元素 当元素满足自身离开条件 或 当元素超过屏幕左边或下边时移除
     *
     * @param element
     * @return
     */
    protected boolean removeElement(T element, Player player) {
        return element.remove(player) || element.getY() > Constant.FRAME_HEIGHT
                || element.getX() < -50;
    }


    /**
     * 绘制
     *
     * @param g
     */
    @Override
    public final void drawImage(Graphics g) {
        this.getElementList().forEach(i -> i.drawImage(g));
    }

    /**
     * 跟随玩家移动
     *
     * @param player
     */
    public final void movedByPlayer(Player player) {
        this.getElementList().forEach(i -> i.setX((int) (i.getX() - player.getxSpeed())));
    }

    @Override
    public void add(T element) {
        super.add(element);
        if (element instanceof IGravity) {
            ElementBean.getGravity().add((IGravity) element);
        }
    }

    @Override
    public void remove(T element) {
        super.remove(element);
        if (element instanceof IGravity) {
            ElementBean.getGravity().remove((IGravity) element);
        }
    }
}
