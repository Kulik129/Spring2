package com.example.repository;

import com.example.model.Reader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {
    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new Reader("Dmitrii")
        ));
    }

    public Reader getReaderById(long id) {
        return readers.stream().filter(it -> Objects.equals(it.getId(),id))
                .findFirst().orElse(null);
    }

    public void addReader(Reader reader) {
        reader.setId(readers.size() + 1);
        readers.add(reader);
    }

    public void deleteReader(long id) {
        Reader reader = getReaderById(id);
        readers.remove(reader);
    }

    public List<Reader> getReaders() {
        return readers;
    }
}
