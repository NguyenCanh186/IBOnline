package com.vmg.ibo.deal.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.dto.UserDetailDTO;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.customer.model.DataModel;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import com.vmg.ibo.deal.constant.DealConstant;
import com.vmg.ibo.deal.model.dto.*;
import com.vmg.ibo.deal.model.entity.Deal;
import com.vmg.ibo.deal.repository.IDealRepository;
import com.vmg.ibo.form.constant.FormConstant;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.repository.IFormRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class DealService extends BaseService implements IDealService {
    private final IDealRepository dealRepository;

    private final IFormRepository formRepository;

    private final IUserDetailRepository userDetailRepository;

    public DealService(IDealRepository dealRepository, IFormRepository formRepository, IUserDetailRepository userDetailRepository) {
        this.dealRepository = dealRepository;
        this.formRepository = formRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public Deal save(Deal deal) {
        deal.setCode(generateDealCode());
        deal.setCreatedAt(new Date());
        deal.setCreatedBy(getCurrentUser().getEmail());
        deal.setCreatedByUserId(getCurrentUser().getId());
        return dealRepository.save(deal);
    }

    @Override
    public Page<DealResponse> getAllDeals(DealFilter filter) {
        if (filter.getStatus() != null) {
            if (filter.getStatus() != Integer.parseInt(DealConstant.PROCESSING.getValue()) && filter.getStatus() != Integer.parseInt(DealConstant.SUCCESS.getValue()) && filter.getStatus() != Integer.parseInt(DealConstant.FAIL.getValue())) {
                throw new WebServiceException(HttpStatus.OK.value(), 409, "Trạng thái không hợp lệ");
            }
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(filter, sort);
        return dealRepository.getAllDeals(filter, pageable);
    }

    @Override
    @Transactional
    public DealDTO updateStatus(Long id, DealDTO dealDTO) {
        if (dealDTO.getStatus() == null) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Trạng thái không được để trống");
        }
        if (dealDTO.getStatus() != Integer.parseInt(DealConstant.SUCCESS.getValue()) && dealDTO.getStatus() != Integer.parseInt(DealConstant.FAIL.getValue())) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Trạng thái không hợp lệ");
        }
        Deal deal = dealRepository.findById(id).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy deal"));
        deal.setCoordinator(getCurrentUser());
        deal.setStatus(dealDTO.getStatus());
        deal.setUpdatedAt(new Date());
        deal.setUpdatedBy(getCurrentUser().getEmail());
        deal.setUpdatedByUserId(getCurrentUser().getId());
        dealRepository.save(deal);
        int status = Integer.parseInt(FormConstant.PROCESSING.getValue());
        if (deal.getStatus() == Integer.parseInt(DealConstant.SUCCESS.getValue())) {
            status = Integer.parseInt(FormConstant.SUCCESS.getValue());
        } else if (deal.getStatus() == Integer.parseInt(DealConstant.FAIL.getValue())) {
            status = Integer.parseInt(FormConstant.FAIL.getValue());
        }
        Form firstForm = formRepository.findById(deal.getFirst().getId()).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy nhu cầu phù hợp"));
        Form secondForm = formRepository.findById(deal.getSecond().getId()).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy nhu cầu phù hợp"));
        firstForm.setStatus(status);
        secondForm.setStatus(status);
        formRepository.save(firstForm);
        formRepository.save(secondForm);
        return mapToDTO(deal);
    }

    @Override
    public DealDetail findById(Long id) {
        Deal deal = dealRepository.findById(id).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy deal"));
        return DealDetail
                .builder()
                .id(deal.getId())
                .firstCustomerInfo(buildCustomerInfo(deal.getFirst(), deal.getConnectionDate()))
                .secondCustomerInfo(buildCustomerInfo(deal.getSecond(), deal.getConnectionDate()))
                .build();
    }

    private CustomerInfo buildCustomerInfo(Form form, Date connectionDate) {
        User user = form.getUser();
        UserDetailDTO additionalInfo = null;
        String email = null;
        String phone = null;
        if (user != null) {
            email = user.getEmail();
            phone = user.getPhone();
            UserDetail userDetail = userDetailRepository.findByIdUser(user.getId());
            if (userDetail == null) {
                throw new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy thông tin khách hàng");
            }
            additionalInfo = mapUserDetailToDTO(userDetail);
        }
        return CustomerInfo
                .builder()
                .email(email)
                .phone(phone)
                .userAdditionalInfo(additionalInfo)
                .form(mapFormToDTO(form))
                .connectionDate(connectionDate)
                .build();
    }

    private FormDTO mapFormToDTO(Form form) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(form, FormDTO.class);
    }

    private UserDetailDTO mapUserDetailToDTO(UserDetail userDetail) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDetail, UserDetailDTO.class);
    }

    private String generateDealCode() {
        List<String> codes = dealRepository.getAllCode();
        int maxNumber = codes.stream()
                .map(s -> Integer.parseInt(s.substring(5)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        return DataModel.DEAL_CODE + maxNumber;
    }

    private DealDTO mapToDTO(Deal deal) {
        return DealDTO
                .builder()
                .id(deal.getId())
                .status(deal.getStatus())
                .build();
    }
}
