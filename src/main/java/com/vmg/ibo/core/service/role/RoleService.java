package com.vmg.ibo.core.service.role;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.RoleConstant;
import com.vmg.ibo.core.model.dto.RoleDTO;
import com.vmg.ibo.core.model.dto.filter.RoleFilter;
import com.vmg.ibo.core.model.entity.Permission;
import com.vmg.ibo.core.model.entity.Role;
import com.vmg.ibo.core.repository.IPermissionRepository;
import com.vmg.ibo.core.repository.IRoleRepository;
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
public class RoleService extends BaseService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public Page<Role> findAllRoles(RoleFilter roleFilter) {
        String name = roleFilter.getName();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(roleFilter, sort);
        return roleRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAllRoleActive();
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "role.error.notFound");
        }
        return mapToDTO(role);
    }

    @Override
    @Transactional
    public Role create(RoleDTO roleDTO) {
        writeLog("CREATE_ROLE", "Thêm mới vai trò", roleDTO, 2, null, null, "SYSTEM", this.getClass());
        Role role = mapToEntity(roleDTO);
        updateRoleMerchantDefault(roleDTO);
        Set<Long> setPermissionIds = new HashSet<>(roleDTO.getPermissionIds());
        List<Long> permissionIds = new ArrayList<>(setPermissionIds);
        List<Permission> permissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission == null) {
                throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
            }
            permissions.add(permission);
            if (permission.getParent() != null) {
                if (!permissionIds.contains(permission.getParent().getId())) {
                    permissions.add(permission.getParent());
                }
            }
        }
        Set<Permission> setPermissions = new HashSet<>(permissions);
        role.setPermissions(setPermissions);
        role.setCreatedBy(getCurrentUser().getUsername());
        role.setCreatedByUserId(getCurrentUser().getId());
        role.setCreatedAt(new Date());
        role = roleRepository.save(role);
        return role;
    }

    @Override
    @Transactional
    public Role update(Long id, RoleDTO roleDTO) {
        writeLog("UPDATE_ROLE", "Cập nhật vai trò", roleDTO, 3, null, null, "SYSTEM", this.getClass());
        Role role = mapToEntity(roleDTO);
        Role oldRole = roleRepository.findById(role.getId()).orElse(null);
        if (oldRole == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "role.error.notFound");
        }
        updateRoleMerchantDefault(roleDTO);
        Set<Long> setPermissionIds = new HashSet<>(roleDTO.getPermissionIds());
        List<Long> permissionIds = new ArrayList<>(setPermissionIds);
        List<Permission> permissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            Permission permission = permissionRepository.findById(permissionId).orElse(null);
            if (permission == null) {
                throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
            }
            permissions.add(permission);
            if (permission.getParent() != null) {
                if (!permissionIds.contains(permission.getParent().getId())) {
                    permissions.add(permission.getParent());
                }
            }
        }
        Set<Permission> setPermissions = new HashSet<>(permissions);
        oldRole.setPermissions(setPermissions);
        oldRole.setCode(roleDTO.getCode());
        oldRole.setName(roleDTO.getName());
        oldRole.setStatus(roleDTO.getStatus());
        oldRole.setMerchantDefault(roleDTO.getMerchantDefault() ? RoleConstant.MERCHANT_DEFAULT.getValue() : RoleConstant.MERCHANT_NOT_DEFAULT.getValue());
        oldRole.setDescription(roleDTO.getDescription());
        oldRole.setUpdatedBy(getCurrentUser().getUsername());
        oldRole.setUpdatedByUserId(getCurrentUser().getId());
        oldRole.setUpdatedAt(new Date());
        return roleRepository.save(oldRole);
    }

    private void updateRoleMerchantDefault(RoleDTO roleDTO) {
        if (roleDTO.getMerchantDefault() != null && roleDTO.getMerchantDefault()) {
            Role currentDefault = roleRepository.findByMerchantDefault(RoleConstant.MERCHANT_DEFAULT.getValue());
            if (currentDefault != null) {
                currentDefault.setMerchantDefault(RoleConstant.MERCHANT_NOT_DEFAULT.getValue());
                roleRepository.save(currentDefault);
            }
        }
    }


    @Override
    @Transactional
    public List<Role> changeStatusByIds(RoleDTO roleDTO) {
        List<Long> ids = reformatIdsFromRequest(roleDTO);
        List<Role> oldRoles = roleRepository.findAllById(ids);
        if (oldRoles.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "role.error.notFound");
        }
        String originalValue = oldRoles.get(0).getStatus().equals(RoleConstant.ENABLE.getValue()) ? "ACTICE" : "DEACTIVE";
        oldRoles.forEach(user -> user.setStatus(user.getStatus().equals(RoleConstant.ENABLE.getValue()) ? RoleConstant.LOCKED.getValue() : RoleConstant.ENABLE.getValue()));
        List<Role> newRoles = roleRepository.saveAll(oldRoles);
        String changeValue = newRoles.get(0).getStatus().equals(RoleConstant.ENABLE.getValue()) ? "ACTICE" : "DEACTIVE";
        writeLog("UPDATE_ROLE STATUS", "Thay đổi trạng thái vai trò", roleDTO, 3, originalValue, changeValue, "SYSTEM", this.getClass());
        return newRoles;
    }


    @Override
    @Transactional
    public List<Role> deleteByIds(RoleDTO roleDTO) {
        List<Long> ids = reformatIdsFromRequest(roleDTO);
        List<Role> oldRoles = roleRepository.findAllById(ids);
        if (oldRoles.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "role.error.notFound");
        }
        roleRepository.deleteAllByIdInBatch(ids);
        writeLog("DELETE_ROLE", "Xóa vai trò", roleDTO, 4, oldRoles, null, "SYSTEM", this.getClass());
        return oldRoles;
    }

    private List<Long> reformatIdsFromRequest(RoleDTO roleDTO) {
        return Arrays.stream(roleDTO.getIds().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private Role mapToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setCode(roleDTO.getCode());
        role.setName(roleDTO.getName());
        role.setStatus(roleDTO.getStatus());
        if (roleDTO.getMerchantDefault() == null) {
            role.setMerchantDefault(RoleConstant.MERCHANT_NOT_DEFAULT.getValue());
        } else {
            role.setMerchantDefault(roleDTO.getMerchantDefault() ? RoleConstant.MERCHANT_DEFAULT.getValue() : RoleConstant.MERCHANT_NOT_DEFAULT.getValue());
        }
        role.setDescription(roleDTO.getDescription());
        return role;
    }

    private RoleDTO mapToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setCode(role.getCode());
        roleDTO.setName(role.getName());
        roleDTO.setStatus(role.getStatus());
        roleDTO.setMerchantDefault(role.getMerchantDefault().equals(RoleConstant.MERCHANT_DEFAULT.getValue()));
        roleDTO.setDescription(role.getDescription());
        List<Long> permissionIds = role.getPermissions().stream()
                .filter(permission -> permission.getChildren().isEmpty())
                .map(Permission::getId)
                .collect(Collectors.toList());
        roleDTO.setPermissionIds(permissionIds);
        return roleDTO;
    }
}
