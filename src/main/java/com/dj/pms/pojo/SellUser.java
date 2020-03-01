package com.dj.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sell_user")
public class SellUser {

    /**
     * userid
     */
    private Integer userd;

    /**
     * sellid
     */
    private Integer sellId;

    /**
     * 是否删除 1正常 0删除
     */
    private Integer isDel;


}
