package com.example.serice;

import com.example.model.Reader;
import com.example.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public void deleteById(long id) {
        readerRepository.deleteReader(id);
    }

    public Reader getReaderById(long id) {
        return readerRepository.getReaderById(id);
    }

    public List<Reader> addReader(Reader reader) {
        readerRepository.addReader(reader);
        return readerRepository.getReaders();
    }

    public List<Reader> all() {
        return readerRepository.getReaders();
    }
}
