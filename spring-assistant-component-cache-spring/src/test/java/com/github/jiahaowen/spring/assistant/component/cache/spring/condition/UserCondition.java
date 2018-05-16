package com.github.jiahaowen.spring.assistant.component.cache.spring.condition;

import com.github.jiahaowen.spring.assistant.component.cache.spring.entity.UserDO;
import org.springframework.data.domain.Pageable;

/**
 * 查询条件
 *
 * @author jiahaowen
 */
public class UserCondition extends UserDO {

    private static final long serialVersionUID = -5111314038991538777L;

    private Pageable pageable;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
