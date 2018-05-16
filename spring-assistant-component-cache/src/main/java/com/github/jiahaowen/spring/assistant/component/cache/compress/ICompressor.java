package com.github.jiahaowen.spring.assistant.component.cache.compress;

import java.io.ByteArrayInputStream;

/**
 *
 * @author jiahaowen
 */
public interface ICompressor {

    /**
     * 
     * 压缩
     * @param bais
     * @return
     * @throws Exception
     */
    byte[] compress(ByteArrayInputStream bais) throws Exception;

    /**
     * 
     * 解压
     * @param bais
     * @return
     * @throws Exception
     */
    byte[] decompress(ByteArrayInputStream bais) throws Exception;
}
