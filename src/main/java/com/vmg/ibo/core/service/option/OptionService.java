package com.vmg.ibo.core.service.option;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.dto.OptionDTO;
import com.vmg.ibo.core.model.dto.filter.OptionFilter;
import com.vmg.ibo.core.model.entity.Option;
import com.vmg.ibo.core.repository.IOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionService extends BaseService implements IOptionService {
    @Autowired
    private IOptionRepository optionRepository;

    @Override
    public Page<Option> findAllOptions(OptionFilter optionFilter) {
        String key = optionFilter.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(optionFilter, sort);
        return optionRepository.findAllOptions(key, pageable);
    }

    @Override
    public Option findById(Long id) {
        Option option = optionRepository.findById(id).orElse(null);
        if (option == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "option.error.notFound");
        }
        return option;
    }

    @Override
    public Option create(OptionDTO optionDTO) {
        Option option = mapToEntity(optionDTO);
        option.setCreatedBy(getCurrentUser().getUsername());
        option.setCreatedByUserId(getCurrentUser().getId());
        option.setCreatedAt(new Date());
        return optionRepository.save(option);
    }

    @Override
    public Option update(Long id, OptionDTO optionDTO) {
        Option option = mapToEntity(optionDTO);
        option.setId(id);
        Option oldOption = findById(option.getId());
        if (oldOption == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "option.error.notFound");
        }
        oldOption.setKey(option.getKey());
        oldOption.setValue(option.getValue());
        oldOption.setGroup(option.getGroup());
        oldOption.setDescription(option.getDescription());
        oldOption.setUpdatedBy(getCurrentUser().getUpdatedBy());
        oldOption.setUpdatedByUserId(getCurrentUser().getId());
        oldOption.setUpdatedAt(new Date());
        return optionRepository.save(oldOption);
    }

    @Override
    @Transactional
    public List<Option> deleteByIds(OptionDTO optionDTO) {
        List<Long> ids = reformatIdsFromRequest(optionDTO);
        List<Option> oldOptions = optionRepository.findAllById(ids);
        if (oldOptions.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "option.error.notFound");
        }
        optionRepository.deleteAllByIdInBatch(ids);
        return oldOptions;
    }

    @Override
    public List<Option> findAllByGroup(String group) {
        return optionRepository.findAllByGroup(group);
    }

    @Override
    public List<Option> findAllByKey(String key) {
        return optionRepository.findAllByKey(key);
    }

    @Override
    public List<Option> findAllByValue(String value) {
        return optionRepository.findAllByValue(value);
    }

    private List<Long> reformatIdsFromRequest(OptionDTO optionDTO) {
        return Arrays.stream(optionDTO.getIds().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private Option mapToEntity(OptionDTO optionDTO) {
        Option option = new Option();
        option.setKey(optionDTO.getKey());
        option.setValue(optionDTO.getValue());
        option.setGroup(optionDTO.getGroup());
        option.setDescription(optionDTO.getDescription());
        return option;
    }
}
