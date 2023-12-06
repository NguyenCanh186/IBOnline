package com.vmg.ibo.core.service.permission;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.OptionConstant;
import com.vmg.ibo.core.model.dto.PermissionDTO;
import com.vmg.ibo.core.model.dto.filter.PermissionFilter;
import com.vmg.ibo.core.model.entity.Option;
import com.vmg.ibo.core.model.entity.Permission;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IOptionRepository;
import com.vmg.ibo.core.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionService extends BaseService implements IPermissionService {
    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private IOptionRepository optionRepository;


    @Override
    public List<Permission> getRecursivePermissionsByCurrentUser(Long parentId) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Permission> permissions = permissionRepository.findPermissionsByUserId(currentUser.getId());
        return getPermissions(permissions);
    }

    @Override
    public List<Permission> getPermissions(Long parentId, PermissionFilter permissionFilter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "order");
        List<Permission> permissions = new ArrayList<>();
        if (permissionFilter.getName() != null) {
            permissions = permissionRepository.findAllByNameContaining(permissionFilter.getName(), sort);
        } else {
            permissions = permissionRepository.findAll(sort);
        }
        return getPermissions(permissions);
    }

    @Override
    public Permission findById(Long id) {
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
        }
        return permission;
    }

    @Override
    @Transactional
    public Permission create(PermissionDTO permissionDTO) {
        updateParentHasChild(permissionDTO);
        Permission permission = mapToEntity(permissionDTO);
        permission.setCreatedBy(getCurrentUser().getUsername());
        permission.setCreatedByUserId(getCurrentUser().getId());
        permission.setCreatedAt(new Date());
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public Permission update(Long id, PermissionDTO permissionDTO) {
        updateParentHasChild(permissionDTO);
        Permission permission = mapToEntity(permissionDTO);
        Permission oldPermission = findById(permission.getId());
        if (oldPermission == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
        }
        permission.setCreatedAt(oldPermission.getCreatedAt());
        permission.setCreatedBy(oldPermission.getCreatedBy());
        permission.setCreatedByUserId(oldPermission.getCreatedByUserId());
        permission.setUpdatedBy(getCurrentUser().getUsername());
        permission.setUpdatedByUserId(getCurrentUser().getId());
        permission.setUpdatedAt(new Date());
        return permissionRepository.save(permission);
    }

    private void updateParentHasChild(PermissionDTO permissionDTO) {
        if (permissionDTO.getParentId() != null) {
            Permission parent = findById(permissionDTO.getParentId());
            if (parent == null) {
                throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
            }
            if (parent.getHasChild() == null || !parent.getHasChild()) {
                parent.setHasChild(true);
                permissionRepository.save(parent);
            }
        }
    }

    @Override
    @Transactional
    public List<Permission> deleteByIds(PermissionDTO permissionDTO) {
        List<Long> ids = Arrays.stream(permissionDTO.getIds().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Permission> oldPermissions = permissionRepository.findAllById(ids);
        if (oldPermissions.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "permission.error.notFound");
        }
        List<Permission> childPermissions = permissionRepository.findByParentIn(oldPermissions);

        // Update child permissions to have null parent_id
        for (Permission childPermission : childPermissions) {
            childPermission.setParent(null);
        }

        // Save the changes to child permissions
        permissionRepository.saveAll(childPermissions);

        permissionRepository.deleteAllByIdInBatch(ids);
        return oldPermissions;
    }


    private List<Permission> getPermissions(List<Permission> permissions) {
        List<Permission> permissionTree = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission.getParent() == null) {
                buildPermissionTree(permission, permissions);
                permissionTree.add(permission);
            }
        }
        return permissionTree;
    }

    private void buildPermissionTree(Permission parentPermission, List<Permission> userPermissions) {
        List<Permission> children = new ArrayList<>();
        for (Permission permission : userPermissions) {
            if (permission.getParent() != null && permission.getParent().equals(parentPermission)) {
                buildPermissionTree(permission, userPermissions);
                permission.setParentId(permission.getParent().getId());
                children.add(permission);
            }
        }
        parentPermission.setChildren(children);
    }

    private Permission mapToEntity(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setId(permissionDTO.getId());
        permission.setCode(permissionDTO.getCode());
        permission.setName(permissionDTO.getName());
        permission.setParent(permissionDTO.getParentId() != null ? findById(permissionDTO.getParentId()) : null);
        permission.setSlug(permissionDTO.getSlug());
        permission.setHidden(permissionDTO.getHidden());
        permission.setIcon(permissionDTO.getIcon());
        permission.setOrder(permissionDTO.getOrder());
        if (permissionDTO.getGroupId() != null) {
            permission.setGroupId(permissionDTO.getGroupId());
            List<Option> group = optionRepository.findAllByKeyAndGroup(permissionDTO.getGroupId().toString(), OptionConstant.PERMISSION_GROUP.getValue());
            if (!group.isEmpty()) {
                permission.setGroupName(group.get(0).getValue());
            }
        }
        permission.setComponentName(permissionDTO.getComponentName());
        permission.setStatus(permissionDTO.getStatus());
        permission.setDescription(permissionDTO.getDescription());
        permission.setHasChild(permissionDTO.getHasChild());
        return permission;
    }
}
