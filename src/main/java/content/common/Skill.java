package main.java.content.common;

import main.java.auxiliary.ElementBean;
import main.java.auxiliary.Keys;
import main.java.base.BaseElement;

/**
 * @author 左边牙齿疼
 * @Description:
 * @date 2019/3/23
 */
public class Skill<T extends BaseElement> {

    private Keys[] keys;//按键 多个按键都使用时，才可以使用
    private Attribute attribute;//需要扣减的属性
    private float energy;//能量 蓄力时间，当蓄力时间大于0 时才会释放
    private float addBaseValue;//能量递增值
    private Float maxEnergy;//最大能量 最大蓄力时间 当达到该值时会自动释放

    public Skill(Keys... keys) {
        this(null, keys);
    }

    public Skill(Attribute attribute, Keys... keys) {
        this.attribute = attribute;
        this.keys = keys;
        this.maxEnergy = null;
        this.addBaseValue = 1;
    }

    public Skill setMaxEnergy(Float maxEnergy) {
        this.maxEnergy = maxEnergy;
        return this;
    }

    public Skill setAddBaseValue(float addBaseValue) {
        this.addBaseValue = addBaseValue;
        return this;
    }

    public void action(ISkill<T> skill) {
        //是否按了某键 并且 该技能所消耗的属性值健康
        if (this.keysJudge() && this.attributeHealth() && lessThanMax()) {
            energy += addBaseValue;
            return;
        }
        //否则当能量值大于0时,技能被释放
        if (energy > 0) {
            //创建实例并添加
            T instance = skill.instance();
            ElementBean.Player.getService().add(instance);
            this.energy = 0;//蓄力重置
            if (this.attribute != null) {
                attribute.subtract();//减少属性
            }
        }
    }

    /**
     * 小于能量最大值
     * @return
     */
    private boolean lessThanMax() {
        if (maxEnergy == null) {
            return true;
        }
        return energy < maxEnergy;
    }

    /**
     * 属性是否健康
     *
     * @return
     */
    private boolean attributeHealth() {
        if (attribute == null) {
            return true;
        }
        return attribute.health();
    }

    /**
     * 是否按了某个键
     *
     * @return
     */
    private boolean keysJudge() {
        for (Keys key : keys) {
            if (!key.use()) {
                return false;
            }
        }
        return true;
    }

    public float getEnergy() {
        return energy;
    }
}
