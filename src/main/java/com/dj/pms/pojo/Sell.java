package com.dj.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sell")
public class Sell {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String sellName;

    /**
     * 是否删除 1正常 0删除
     */
    private Integer isDel;

    /**
     * 头像
     */
    private String img;

    /**
     *价格
     */
    private Double sellPrice;

    /**
     * 颜色
     */
    private String colour;

    /**
     * 维修项目
     */
    private Integer maintainProject;


}
