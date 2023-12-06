package com.vmg.ibo.core.service.log;

import com.vmg.ibo.core.model.dto.LogDTO;
import com.vmg.ibo.core.model.dto.filter.LogFilter;
import com.vmg.ibo.core.model.entity.LogAction;
import org.springframework.data.domain.Page;

public interface ILogService {
    Page<LogDTO> findAllLog(LogFilter logFilter);
    LogAction save(LogAction logAction);
}
