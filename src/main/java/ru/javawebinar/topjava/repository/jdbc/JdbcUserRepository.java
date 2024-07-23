package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.validation.Validator;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class,new DefaultConversionService());

    private static final ResultSetExtractor<List<User>> RESULT_SET_EXTRACTOR = rs -> {
        Map<Integer, User> userMap = new LinkedHashMap<>();
        int rowNumber =0;
        while (rs.next()) {
            User user;
            String role = rs.getString("role");
            int id = rs.getInt("id");
            if (!userMap.containsKey(id)){
                user = ROW_MAPPER.mapRow(rs, rowNumber);
                user.setRoles(new HashSet<>());
                setRole(role, user);
                userMap.put(id,user);
            } else {
                userMap.compute(id,(integer, user1) -> setRole(role, user1));
            }
            rowNumber++;
        }
        return userMap.values().stream().toList();
    };

    private static User setRole(String role, User user) {
        if (role != null) {
            user.getRoles().add(Role.valueOf(role));
        }
        return user;
    }

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final Validator validator;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              Validator validator) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.validator = validator;
    }

    @Transactional
    @Override
    public User save(User user) {
        ValidationUtil.checkConstraints(user, validator);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        List<Role> roles = user.getRoles().stream().toList();
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                               UPDATE users SET name=:name, email=:email, password=:password, 
                               registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                               """, parameterSource) == 0
                   ||
                   jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.id()) == -1) {
            return null;
        }
        jdbcTemplate.batchUpdate("INSERT INTO user_role (user_id, role) VALUES (?,?)",
                                 new BatchPreparedStatementSetter() {
                                     @Override
                                     public void setValues(PreparedStatement ps, int i) throws SQLException {
                                         ps.setInt(1, user.id());
                                         ps.setString(2, roles.get(i).name());
                                     }

                                     @Override
                                     public int getBatchSize() {
                                         return roles.size();
                                     }
                                 });
        return user;
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_role ON users.id=user_role.user_id " +
                                              "WHERE users.id=?", RESULT_SET_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_role ON users.id=user_role.user_id " +
                                              "WHERE email=?", RESULT_SET_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFt JOIN user_role ON users.id = user_role.user_id ORDER BY " +
                                  "name, email", RESULT_SET_EXTRACTOR);
    }
}
