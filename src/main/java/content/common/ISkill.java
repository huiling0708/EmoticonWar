package main.java.content.common;

import main.java.base.BaseElement;
import main.java.content.player.Player;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
public interface ISkill<T extends BaseElement> {
    T instance();
}
