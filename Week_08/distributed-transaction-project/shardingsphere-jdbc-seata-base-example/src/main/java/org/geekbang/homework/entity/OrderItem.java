package org.geekbang.homework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderItem extends Model<OrderItem> {
    @TableId
    private Long orderItemId;
    private Long orderId;
    private Long userId;
    private String status;
}
