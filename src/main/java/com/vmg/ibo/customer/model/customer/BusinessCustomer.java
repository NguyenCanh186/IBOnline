package com.vmg.ibo.customer.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCustomer {
    @NotNull(message = "Tên doanh nghiệp bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String businessName;
    @NotNull(message = "Mã số thuế bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String codeTax;
    @NotNull(message = "Số giấy phép kinh doanh bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String codeReg;
    private String address;
    @NotNull(message = "Tên người liên hệ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String contactName;
    private String title;
    @NotNull(message = "Số điện thoại bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String phone;
    @NotNull(message = "Ngành nghề kinh doanh chính bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String mainBusiness;
    @NotNull (message = "Quy mô vốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long capitalSize;
    private String description;
    @NotNull (message = "Doanh thu năm gần nhất bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long mostRecentYearRevenue;
    @NotNull (message = "Lợi nhuận năm gần nhất bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long mostRecentYearProfit;
    @NotNull (message = "Cơ cấu tài sản bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long propertyStructure;
    @NotNull (message = "Cơ cấu nợ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long debtStructure;
    private List<MultipartFile> files;
}
