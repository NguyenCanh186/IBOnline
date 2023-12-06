package com.vmg.ibo.core.service.permission;

import com.vmg.ibo.core.model.dto.PermissionDTO;
import com.vmg.ibo.core.model.dto.filter.PermissionFilter;
import com.vmg.ibo.core.model.entity.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> getRecursivePermissionsByCurrentUser(Long parentId);

    List<Permission> getPermissions(Long parentId, PermissionFilter permissionFilter);

    Permission findById(Long id);

    Permission create(PermissionDTO permissionDTO);

    Permission update(Long id, PermissionDTO permissionDTO);

    List<Permission> deleteByIds(PermissionDTO permissionDTO);
}
