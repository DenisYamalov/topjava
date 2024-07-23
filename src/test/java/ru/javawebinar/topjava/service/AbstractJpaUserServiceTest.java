package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:spring/testConfig.xml")
public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    private CacheManager cacheManager;
    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }
}
