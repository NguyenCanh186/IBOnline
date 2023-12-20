package com.vmg.ibo.customer.service.userDetail;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.customer.model.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("newUserDetailService")
public class UserDetailService extends BaseService implements IUserDetailService {
    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Override
    public Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(userFilter, sort);
        return userDetailRepository.findAllUser(pageable, userFilter.getContactName(), userFilter.getIsCustomerPersonal(), userFilter.getStatus(), userFilter.getFromCapitalSize(), userFilter.getToCapitalSize());
    }

    @Override
    public UserDetailWithUserDTO findUserById(Long id) {
        return userDetailRepository.findUserById(id);
    }

    @Override
    public UserDetail create(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetail findByIdUser(Long id) {
        return userDetailRepository.findByIdUser(id);
    }
}
