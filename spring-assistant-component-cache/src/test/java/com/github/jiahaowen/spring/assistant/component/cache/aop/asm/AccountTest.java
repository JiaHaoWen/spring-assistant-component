package com.github.jiahaowen.spring.assistant.component.cache.aop.asm;

import java.io.IOException;

/**
 * @author: jiahaowen
 */
public class AccountTest {

    public static void main(String[] args) throws ClassFormatError, InstantiationException, IllegalAccessException, IOException {
        Account account=SecureAccountGenerator.generateSecureAccount();
        account.operation("test");
    }

}
