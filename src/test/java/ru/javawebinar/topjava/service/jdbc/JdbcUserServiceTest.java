package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    Environment env;

    private boolean isJdbcProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        Arrays.stream(activeProfiles).forEach(System.out::println);
        return Arrays.stream(activeProfiles).anyMatch(s -> s.contains(JDBC.toLowerCase()));
    }

    @Override
    public void createWithException() throws Exception {
        Assume.assumeFalse(isJdbcProfile());
        super.createWithException();
    }
}