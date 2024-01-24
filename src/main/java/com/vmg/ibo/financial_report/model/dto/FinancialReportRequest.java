package com.vmg.ibo.financial_report.model.dto;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FinancialReportRequest {
    private Long id;
    @NotNull(message = "Tiêu đề báo cáo tài chính bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String title;
    @NotNull (message = "Doanh thu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long revenue;
    @NotNull (message = "Lợi nhuận bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long profit;
    @NotNull (message = "Tổng tài sản bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long asset;
    @NotNull (message = "Tổng nợ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long debt;
    @NotNull(message = "Năm bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer year;
    private Integer quarter;
    private Long userId;
    private List<MultipartFile> files;
}
