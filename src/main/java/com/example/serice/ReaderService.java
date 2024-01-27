package com.example.serice;

import com.example.model.Reader;
import com.example.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public void deleteById(long id) {
        readerRepository.deleteById(id);
    }

    public Optional<Reader> getReaderById(long id) {
        return readerRepository.findById(id);
    }

    public List<Reader> addReader(Reader reader) {
        readerRepository.save(reader);
        return readerRepository.findAll();
    }

    public List<Reader> all() {
        return readerRepository.findAll();
    }
}
