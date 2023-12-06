package com.vmg.ibo.core.service.option;

import com.vmg.ibo.core.model.dto.OptionDTO;
import com.vmg.ibo.core.model.dto.filter.OptionFilter;
import com.vmg.ibo.core.model.entity.Option;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOptionService {
    Page<Option> findAllOptions(OptionFilter userFilter);

    Option findById(Long id);

    Option create(OptionDTO userDTO);

    Option update(Long id, OptionDTO userDTO);

    List<Option> deleteByIds(OptionDTO userDTO);

    List<Option> findAllByGroup(String group);

    List<Option> findAllByKey(String key);

    List<Option> findAllByValue(String value);
}
