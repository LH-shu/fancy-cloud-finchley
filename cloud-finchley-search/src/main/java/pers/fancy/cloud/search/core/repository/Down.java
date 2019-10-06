package pers.fancy.cloud.search.core.repository;

/**
 * description: 下钻聚合分析返回对象
 **/
public class Down {
    String level_1_key;
    String level_2_key;

    Object value;

    public String getLevel_1_key() {
        return level_1_key;
    }

    public void setLevel_1_key(String level_1_key) {
        this.level_1_key = level_1_key;
    }

    public String getLevel_2_key() {
        return level_2_key;
    }

    public void setLevel_2_key(String level_2_key) {
        this.level_2_key = level_2_key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Down{" +
                "level_1_key='" + level_1_key + '\'' +
                ", level_2_key='" + level_2_key + '\'' +
                ", value=" + value +
                '}';
    }
}
