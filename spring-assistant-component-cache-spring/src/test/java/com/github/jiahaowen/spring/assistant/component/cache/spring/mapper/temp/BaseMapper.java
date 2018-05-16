package com.github.jiahaowen.spring.assistant.component.cache.spring.mapper.temp;

import com.github.jiahaowen.spring.assistant.component.cache.annotation.Cache;

/**
 * 通用Mapper加缓存的例子
 *
 * @author: jiahaowen
 */
public interface BaseMapper<T, PK> {

    @Cache(
        expire = 3600,
        expireExpression = "null == #retVal ? 600: 3600",
        key = "#target.getCacheName() +'-byid-' + #args[0]"
    )
    T getById(PK id);
}
