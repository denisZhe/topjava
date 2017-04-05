package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        List<User> users = Arrays.asList(
                new User(counter.incrementAndGet(), "Rulon", "aaaemail", "pass1", Role.ROLE_USER),
                new User(counter.incrementAndGet(), "Rulon", "bbbemail", "pass2", Role.ROLE_USER),
                new User(counter.incrementAndGet(), "Fidel", "cccemail", "pass3", Role.ROLE_USER),
                new User(counter.incrementAndGet(), "Alex", "sssemail", "pass4", Role.ROLE_USER)
        );
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getFiltered");
        List<User> users = repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
