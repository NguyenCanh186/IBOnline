package com.vmg.ibo.customer.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCustomer {
    @NotNull(message = "Tên doanh nghiệp bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 100, message = "Tên doanh nghiệp không được vượt quá 100 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Tên doanh nghiệp không được để trống!", groups = {Insert.class, Update.class})
    private String businessName;
    @NotNull(message = "Mã số thuế bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 20, message = "Mã số thuế không được vượt quá 20 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Mã số thuế không được để trống!", groups = {Insert.class, Update.class})
    private String codeTax;
    @NotNull(message = "Số giấy phép kinh doanh bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 20, message = "Số giấy phép kinh doanh không được vượt quá 20 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số giấy phép kinh doanh không được để trống!", groups = {Insert.class, Update.class})
    private String codeReg;
    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự!", groups = {Insert.class, Update.class})
    private String address;
    @NotNull(message = "Tên người liên hệ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Tên người liên hệ không được vượt quá 50 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Tên người liên hệ không được để trống!", groups = {Insert.class, Update.class})
    private String contactName;
    @Size(max = 255, message = "Ngành nghề kinh doanh chính không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    private String title;
    @NotNull(message = "Số điện thoại bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 11, message = "Số điện thoại không được vượt quá 11 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số điện thoại không được để trống!", groups = {Insert.class, Update.class})
    private String phone;
    @NotNull(message = "Ngành nghề kinh doanh chính bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 255, message = "Ngành nghề kinh doanh chính không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số điện thoại không được để trống!", groups = {Insert.class, Update.class})
    private String mainBusiness;
    @NotNull (message = "Quy mô vốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long capitalSize;
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
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
