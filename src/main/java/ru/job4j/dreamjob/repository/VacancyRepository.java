package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

/**
 * Репозиторий, описывающий CRUD операции для вакансии
 * @see Vacancy
 */
public interface VacancyRepository {

    /**
     * Выполняет сохранение вакансии
     *
     * @param vacancy сохраняемая вакансия
     * @return сохраненная вакансия с новым id
     */
    Vacancy save(Vacancy vacancy);

    /**
     * Удаляет вакансию по идентификатору
     *
     * @param id идентификатор вакансии
     * @return true если удаление было произведено и false в противном случае
     */
    boolean deleteById(int id);

    /**
     * Обновляет вакансию. Идентификатор берется из vacancy
     *
     * @param vacancy обновляемая вакансия
     * @return true если обновление было произведено и false в противном случае
     */
    boolean update(Vacancy vacancy);

    /**
     * Возвращает ваканси. по id
     *
     * @param id идентификатор вакансии
     * @return optional с ваканcией и false в противном случае
     */
    Optional<Vacancy> findById(int id);

    /**
     * Возвращает список всех вакансий
     *
     * @return список всех вакансий
     */
    Collection<Vacancy> findAll();

}
