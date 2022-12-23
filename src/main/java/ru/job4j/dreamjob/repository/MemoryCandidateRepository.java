package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> vacancies = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Intern Java Developer", "Стажер Java разработчик", LocalDateTime.now()));
        save(new Candidate(0, "Junior Java Developer", "Младший Java разработчик", LocalDateTime.now()));
        save(new Candidate(0, "Junior+ Java Developer", "Java разработчик", LocalDateTime.now()));
        save(new Candidate(0, "Middle Java Developer", "Старший Java разработчик", LocalDateTime.now()));
        save(new Candidate(0, "Middle+ Java Developer", "Ведущий Java разработчик", LocalDateTime.now()));
        save(new Candidate(0, "Senior Java Developer", "Главный Java разработчик", LocalDateTime.now()));
    }

    @Override
    public synchronized Candidate save(Candidate vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public synchronized boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public synchronized boolean update(Candidate vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Candidate(oldVacancy.getId(), vacancy.getName(), vacancy.getDescription(), vacancy.getCreationDate());
        }) != null;
    }

    @Override
    public synchronized Optional<Candidate> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public synchronized Collection<Candidate> findAll() {
        return vacancies.values();
    }

}
