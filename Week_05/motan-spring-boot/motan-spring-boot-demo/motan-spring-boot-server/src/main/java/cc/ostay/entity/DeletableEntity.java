package cc.ostay.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * extends BaseEntity 扩展 delete 操作
 *
 */
@Data
@Accessors(chain = true)
public class DeletableEntity extends BaseEntity {

    /**
     * 是否删除
     */
    private Integer deleted;

}
