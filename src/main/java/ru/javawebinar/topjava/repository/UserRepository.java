package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param id
     * @param enabled
     * @return true if parameter has been set up
     */
    boolean enabled(int id, boolean enabled);
}