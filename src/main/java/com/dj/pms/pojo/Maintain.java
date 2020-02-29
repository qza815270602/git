package com.dj.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("maintain")
public class Maintain {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 填写维修时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 前台接受字符串转成时间类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") /* 只有把这个对象转为Json才生效返回 */
    private Date maintainTime;

    /**
     * 维修单号
     */
    private String maintainId;

    /**
     * 维修项目
     */
    private Integer maintainProject;

    /**
     * 维修项目
     */
    private String projectShow;

    /**处理状态 1 已预约  2已审核 3维修完成
     *
     */
    private Integer status;

    /**处理状态 1 已预约  2已审核 3维修完成
     *
     */
    private String statusShow;

    /**
     * 是否删除 1正常 0删除
     */
    private Integer isDel;

}
