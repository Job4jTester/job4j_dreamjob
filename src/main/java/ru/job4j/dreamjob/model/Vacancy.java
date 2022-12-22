package ru.job4j.dreamjob.model;

import java.util.Objects;

/**
 * Модель данных вакансии
 */
public class Vacancy {

    /**
     * Идентификатор
     */
    private int id;

    /**
     * Название
     */
    private String title;

    public Vacancy(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
