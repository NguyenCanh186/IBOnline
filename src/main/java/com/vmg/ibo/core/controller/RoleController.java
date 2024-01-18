package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.RoleDTO;
import com.vmg.ibo.core.model.dto.filter.RoleFilter;
import com.vmg.ibo.core.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('role-list')")
    public Result<?> findAllRolesWithPaging(RoleFilter roleFilter) {
        return Result.success(roleService.findAllRoles(roleFilter));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('role-list')")
    public Result<?> findAllRoles() {
        return Result.success(roleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role-get')")
    public Result<?> getRoleById(@PathVariable Long id) {
        return Result.success(roleService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role-add')")
    public Result<?> createRole(@Validated(Insert.class) @RequestBody RoleDTO roleDTO) {
        return Result.success(roleService.create(roleDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role-edit')")
    public Result<?> editRole(@PathVariable Long id, @Validated(Update.class) @RequestBody RoleDTO roleDTO) {
        return Result.success(roleService.update(id, roleDTO));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('role-delete')")
    public Result<?> deleteRole(@Validated(Delete.class) @RequestBody RoleDTO roleDTO) {
        return Result.success(roleService.deleteByIds(roleDTO));
    }

    @PutMapping("/change-status")
    @PreAuthorize("hasAuthority('role-change-status')")
    public Result<?> changeStatusRole(@RequestBody RoleDTO roleDTO) {
        return Result.success(roleService.changeStatusByIds(roleDTO));
    }
}
