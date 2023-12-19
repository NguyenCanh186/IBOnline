package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.PermissionDTO;
import com.vmg.ibo.core.model.dto.filter.PermissionFilter;
import com.vmg.ibo.core.service.permission.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/permissions")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('permission-list')")
    public Result<?> getPermissionTree(PermissionFilter permissionFilter) {
        return Result.success(permissionService.getPermissions(null, permissionFilter));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('permission-get')")
    public Result<?> getPermissionById(@PathVariable Long id) {
        return Result.success(permissionService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('permission-add')")
    public Result<?> createPermission(@Validated(Insert.class) @RequestBody PermissionDTO permissionDTO) {
        return Result.success(permissionService.create(permissionDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('permission-edit')")
    public Result<?> editPermission(@PathVariable Long id, @Validated(Update.class) @RequestBody PermissionDTO permissionDTO) {
        return Result.success(permissionService.update(id, permissionDTO));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('permission-delete')")
    public Result<?> deletePermission(@Validated(Delete.class) @RequestBody PermissionDTO permissionDTO) {
        return Result.success(permissionService.deleteByIds(permissionDTO));
    }
}
