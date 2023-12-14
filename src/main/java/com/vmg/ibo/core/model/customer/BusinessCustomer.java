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
}
