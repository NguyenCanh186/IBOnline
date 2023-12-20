package com.vmg.ibo.core.model.customer;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BusinessCustomer {
    @NotNull(message = "Tên doanh nghiệp bắt buộc nhập!")
    private String businessName;
    @NotNull(message = "Mã số thuế bắt buộc nhập!")
    private String codeTax;
    @NotNull(message = "Số giấy phép kinh doanh bắt buộc nhập!")
    private String codeReg;
    private String address;
    @NotNull(message = "Tên người liên hệ bắt buộc nhập!")
    private String contactName;
    private String title;
    @NotNull(message = "Số điện thoại bắt buộc nhập!")
    private String phone;
    @NotNull(message = "Ngành nghề kinh doanh chính bắt buộc nhập!")
    private String mainBusiness;
    @NotNull (message = "Quy mô vốn bắt buộc nhập!")
    private Long capitalSize;
    private String description;
    @NotNull (message = "Doanh thu năm gần nhất bắt buộc nhập!")
    private Long mostRecentYearRevenue;
    @NotNull (message = "Lợi nhuận năm gần nhất bắt buộc nhập!")
    private Long mostRecentYearProfit;
    @NotNull (message = "Cơ cấu tài sản bắt buộc nhập!")
    private Long propertyStructure;
    @NotNull (message = "Cơ cấu nợ bắt buộc nhập!")
    private Long debtStructure;
    private List<MultipartFile> files;

    public BusinessCustomer() {
    }

    public BusinessCustomer(String businessName, String codeTax, String codeReg, String address, String contactName, String title, String phone, String mainBusiness, Long capitalSize, String description, Long mostRecentYearRevenue, Long mostRecentYearProfit, Long propertyStructure, Long debtStructure, List<MultipartFile> files) {
        this.businessName = businessName;
        this.codeTax = codeTax;
        this.codeReg = codeReg;
        this.address = address;
        this.contactName = contactName;
        this.title = title;
        this.phone = phone;
        this.mainBusiness = mainBusiness;
        this.capitalSize = capitalSize;
        this.description = description;
        this.mostRecentYearRevenue = mostRecentYearRevenue;
        this.mostRecentYearProfit = mostRecentYearProfit;
        this.propertyStructure = propertyStructure;
        this.debtStructure = debtStructure;
        this.files = files;
    }
}
