package pers.fancy.cloud.search.core.repository;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 高亮对象封装
 *
 * @author LiLiChai
 */
@Data
public class HighLight {
    private String preTag = "";
    private String postTag = "";
    private List<String> highLightList;

    public HighLight() {
        highLightList = new ArrayList<>();
    }

    public HighLight field(String fieldValue) {
        highLightList.add(fieldValue);
        return this;
    }

}
