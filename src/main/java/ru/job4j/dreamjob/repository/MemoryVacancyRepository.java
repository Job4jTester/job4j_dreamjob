package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Стажер Java разработчик", LocalDateTime.now(), true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Младший Java разработчик", LocalDateTime.now(), true, 1, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Java разработчик", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Middle Java Developer", "Старший Java разработчик", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Ведущий Java разработчик", LocalDateTime.now(), true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer", "Главный Java разработчик", LocalDateTime.now(), true, 3, 0));
    }

    @Override
    public synchronized Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public synchronized boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public synchronized boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId()
            );
        }) != null;
    }

    @Override
    public synchronized Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public synchronized Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
