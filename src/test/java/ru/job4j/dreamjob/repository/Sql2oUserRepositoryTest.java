package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.Properties;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class Sql2oUserRepositoryTest {

    static Sql2oUserRepository userRepository;

    @BeforeAll
    static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oVacancyRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        userRepository = new Sql2oUserRepository(sql2o);
    }

    @Test
    void whenSaveNewUserThenMustSave() {
        var user = new User(0, "test", UUID.randomUUID().toString(), "pss");
        var savedUser = userRepository.save(user);
        assertThat(savedUser).isNotEmpty();
        assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenSaveUserTwiceThenGetEmpty() {
        var user = new User(0, "test", UUID.randomUUID().toString(), "pss");
        userRepository.save(user);
        var savedUser = userRepository.save(user);
        assertThat(savedUser).isEmpty();
    }

    @Test
    void whenSaveThenGetByUsernameAndPassword() {
        var email = UUID.randomUUID().toString();
        var password = "pss";
        var user = new User(0, "test", email, password);
        userRepository.save(user);
        var savedUser = userRepository.findByEmailAndPassword(email, password);
        assertThat(savedUser).isNotEmpty();
        assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

}