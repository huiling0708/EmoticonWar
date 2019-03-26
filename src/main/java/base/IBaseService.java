package main.java.base;

import main.java.content.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
            */
    public interface IBaseService<T> extends IDraw {

        /**
         * 初始化清除列表元素
         */
        void init();

        /**
         * 添加
         *
         * @param element
         */
    void add(T element);

    /**
     * 移除
     *
     * @param element
     */
    void remove(T element);

    /**
     * 获取元素列表
     *
     * @return
     */
    CopyOnWriteArrayList<T> getElementList();

    /**
     * 跟随玩家移动
     */
    void movedByPlayer(Player player);
}
