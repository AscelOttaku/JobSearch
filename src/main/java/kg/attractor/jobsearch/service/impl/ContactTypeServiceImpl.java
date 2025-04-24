package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ContactTypeDto;
import kg.attractor.jobsearch.dto.mapper.impl.ContactTypeMapper;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.repository.ContactTypeRepository;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    @Override
    public ContactTypeDto findContactByType(String contactType) {
        return contactTypeRepository.findContactByType(contactType)
                .map(contactTypeMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("contact type not found"));
    }

    @Override
    public boolean isContactTypeExist(String contactType) {
        return contactTypeRepository.findContactByType(contactType).isPresent();
    }

    @Override
    public Long createContactType(ContactTypeDto contactTypeDto) {
        return contactTypeRepository.save(contactTypeMapper.mapToEntity(contactTypeDto))
                .getId();
    }
}
