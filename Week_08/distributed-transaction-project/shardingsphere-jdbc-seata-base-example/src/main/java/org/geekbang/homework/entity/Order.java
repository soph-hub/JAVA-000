package org.geekbang.homework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Order extends Model<Order> {
    @TableId
    private Long orderId;
    private Long userId;
    private Long addressId;
    private String status;
}
