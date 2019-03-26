package main.java.content.common;

/**
 * @author 左边牙齿疼
 * @Description: 属性
 * @date 2019/3/23
 */
public class Attribute {

    private int value;//初始值
    private int maxValue;//最大值
    private int minValue;//最小值
    private int addBaseValue;//增加基数 每次增加量
    private int subtractBaseValue;//减少基数 每次减少量

    public Attribute(int value) {
        this(value, Integer.MAX_VALUE);
    }

    public Attribute(int value, int maxValue) {
        this.value = value;
        this.maxValue = maxValue;
        this.minValue = 0;
        this.addBaseValue = 1;
        this.subtractBaseValue = 1;
    }

    /**
     * 属性是否健康 当属性值大于最小值时表示健康
     *
     * @return
     */
    public boolean health() {
        return value > minValue;
    }

    /**
     * 按基数增加
     */
    public void add() {
        this.add(this.addBaseValue);
    }

    /**
     * 按指定值增加
     *
     * @param addvalue
     */
    public void add(int addvalue) {
        //大于最大值时等于最大值
        int i = this.value + addvalue;
        this.value = i < this.maxValue ? i : this.maxValue;
    }

    /**
     * 设置为属性最大值
     */
    public void toMax() {
        this.value = this.maxValue;
    }

    /**
     * 按基数增加
     */
    public void subtract() {
        this.subtract(this.subtractBaseValue);
    }

    /**
     * 按指定值增加
     *
     * @param subtractValue
     */
    public void subtract(int subtractValue) {
        //小于最小值时等于最小值
        int i = this.value - subtractValue;
        this.value = i < minValue ? minValue : i;
    }

    public void addMaxValue(int maxValue) {
        this.maxValue += maxValue;
    }

    public Attribute setValue(int value) {
        this.value = value;
        return this;
    }

    public Attribute setMaxValue(int maxValue) {
        return this;
    }

    public Attribute setMinValue(int minValue) {
        this.minValue = minValue;
        return this;
    }

    public Attribute setAddBaseValue(int addBaseValue) {
        this.addBaseValue = addBaseValue;
        return this;
    }

    public Attribute setSubtractBaseValue(int subtractBaseValue) {
        this.subtractBaseValue = subtractBaseValue;
        return this;
    }

    /**
     * 增加基数与减小基数相同时
     *
     * @param baseValue
     * @return
     */
    public Attribute setSameBaseValue(int baseValue) {
        this.addBaseValue = baseValue;
        this.subtractBaseValue = baseValue;
        return this;
    }

    public int getValue() {
        return value;
    }
}
