package pers.fancy.cloud.search.core.repository;

import lombok.Data;

/**
 * 下钻聚合分析返回对象
 *
 * @author LiLiChai
 */
@Data
public class Down {

    private String level_1_key;
    private String level_2_key;
    private Object value;

    @Override
    public String toString() {
        return "Down{" +
                "level_1_key='" + level_1_key + '\'' +
                ", level_2_key='" + level_2_key + '\'' +
                ", value=" + value +
                '}';
    }
}
