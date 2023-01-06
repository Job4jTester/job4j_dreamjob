package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleVacancyService implements VacancyService {

    private final VacancyRepository vacancyRepository;

    private final FileService fileService;

    public SimpleVacancyService(VacancyRepository vacancyRepository, FileService fileService) {
        this.vacancyRepository = vacancyRepository;
        this.fileService = fileService;
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public boolean deleteById(int id) {
        var fileOptional = findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteOldFile(fileOptional.get());
        return vacancyRepository.deleteById(id);
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNotEmptyFile = image.getContent().length > 0;
        if (isNotEmptyFile) {
            /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем */
            deleteOldFile(vacancy);
            saveNewFile(vacancy, image);
        }
        return vacancyRepository.update(vacancy);
    }

    private void deleteOldFile(Vacancy vacancy) {
        fileService.deleteById(vacancy.getFileId());
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

}
