package com.vmg.ibo.core.service.role;

import com.vmg.ibo.core.model.dto.RoleDTO;
import com.vmg.ibo.core.model.dto.filter.RoleFilter;
import com.vmg.ibo.core.model.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {
    Page<Role> findAllRoles(RoleFilter roleFilter);

    List<Role> findAll();

    RoleDTO findById(Long id);

    Role create(RoleDTO roleDTO);

    Role update(Long id, RoleDTO roleDTO);

    List<Role> changeStatusByIds(RoleDTO roleDTO);

    List<Role> deleteByIds(RoleDTO roleDTO);
}
