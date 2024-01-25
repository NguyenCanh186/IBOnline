package com.vmg.ibo.deal.service;

import com.vmg.ibo.deal.model.dto.DealDTO;
import com.vmg.ibo.deal.model.dto.DealDetail;
import com.vmg.ibo.deal.model.dto.DealResponse;
import com.vmg.ibo.deal.model.dto.DealFilter;
import com.vmg.ibo.deal.model.entity.Deal;
import org.springframework.data.domain.Page;

public interface IDealService {
    Deal save(Deal deal);

    Page<DealResponse> getAllDeals(DealFilter filter);

    DealDTO updateStatus(Long id, DealDTO dealDTO);

    DealDetail findById(Long id);
}
