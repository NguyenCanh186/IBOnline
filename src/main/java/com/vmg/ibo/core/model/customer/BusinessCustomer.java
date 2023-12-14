package com.vmg.ibo.core.model.customer;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BusinessCustomer {
    private String businessName;
    @NotNull(message = "Tên doanh nghiệp bắt buộc nhập!")
    private String codeTax;
    @NotNull(message = "Số giấy phép kinh doanh bắt buộc nhập!")
    private String codeReg;
    private String address;
    private String contactName;
    private String title;
    private String phone;
    private String mainBusiness;
    private Long capitalSize;
    private String description;
    private Long mostRecentYearRevenue;
    private Long mostRecentYearProfit;
    private Long propertyStructure;
    private Long debtStructure;
    private List<MultipartFile> files;
}
