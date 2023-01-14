package com.profitsoft.profitsofttask9.service.implementations;

import com.profitsoft.profitsofttask9.dao.AuthorRepo;
import com.profitsoft.profitsofttask9.dto.AuthorInfoDto;
import com.profitsoft.profitsofttask9.service.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    @Override
    public List<AuthorInfoDto> getAllAuthors() {
        return authorRepo.findAll().stream().map(AuthorInfoDto::of).collect(Collectors.toList());
    }
}
