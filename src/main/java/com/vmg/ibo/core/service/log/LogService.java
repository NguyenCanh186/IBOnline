package com.vmg.ibo.core.service.log;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.dto.filter.LogFilter;
import com.vmg.ibo.core.model.entity.LogAction;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.ILogRepository;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.utils.RequestUtils;
import com.vmg.ibo.core.model.dto.LogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogService extends BaseService implements ILogService {
    @Autowired
    private ILogRepository logRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Page<LogDTO> findAllLog(LogFilter logFilter) {
        String query = logFilter.getQuery();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(logFilter, sort);
        return logRepository.findAllByFilter(query, pageable).map(this::mapToDTO);
    }

    @Override
    public LogAction save(LogAction logAction) {
        User user = getCurrentUser();
        logAction.setIpAddress(RequestUtils.getClientIP(request));
        logAction.setUserId(user.getId());
        logAction.setCreatedAt(new Date());
        return logRepository.save(logAction);
    }

    private LogDTO mapToDTO(LogAction logAction) {
        LogDTO logDTO = new LogDTO();
        logDTO.setId(logAction.getId());
        logDTO.setIpAddress(logAction.getIpAddress());
        logDTO.setCreatedAt(logAction.getCreatedAt());
        logDTO.setActionType(logAction.getActionType());
        logDTO.setActionDescription(logAction.getActionDescription());
        logDTO.setActionData(logAction.getActionData());
        logDTO.setImpactLevel(logAction.getImpactLevel());
        logDTO.setOriginalValue(logAction.getOriginalValue());
        logDTO.setChangedValue(logAction.getChangedValue());
        logDTO.setAffectTo(logAction.getAffectTo());
        userRepository.findById(logAction.getUserId()).ifPresent(user -> logDTO.setUsername(user.getUsername()));
        return logDTO;
    }
}
