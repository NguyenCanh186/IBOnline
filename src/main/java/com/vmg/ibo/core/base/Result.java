package com.vmg.ibo.core.base;

import com.vmg.ibo.core.config.exception.GlobalException;
import com.vmg.ibo.core.config.handler.ErrorHandler;
import com.vmg.ibo.core.constant.HttpStatusConstant;
import com.vmg.ibo.core.utils.CommonUtils;
import com.vmg.ibo.core.utils.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.Objects;

@Data
@Accessors(chain = true)
@SuppressWarnings("rawtypes")
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
    private String message;
    private String redirect;
    private T data;
    
    public static Result success(Object data) {
        return Result.result(HttpStatusConstant.SUCCESS.getValue(), "success", data);
    }
    
    public static Result success() {
        return Result.success(null);
    }

    public static Result success(String templateMessage, Object data) {
    	return Result.result(HttpStatusConstant.SUCCESS.getValue(), GlobalException.format(templateMessage), data);
    }
    
    public static Result successWithMessage(String templateMessage) {
    	return success(templateMessage, null);
    }
    
    public static Result error(Integer code, String templateMessage) {
        return Result.result(code, GlobalException.format(templateMessage), null);
    }
    
    public static Result error(Integer code, String templateMessage, String...args) {
        return Result.result(code, GlobalException.format(templateMessage, args), null);
    }
    
    public Result redirect(String redirect) {
    	this.redirect = redirect;
    	return this;
    }
    
    @SuppressWarnings("unchecked")
	public static Result result(Integer code, String message, Object data) {
        Result result = new Result();
        return result.setCode(code)
                .setMessage(message)
                .setData(data);
    }
    
    public static Result check(ErrorHandler errorHandler, String success) {
    	if (errorHandler.isPass()) {
            Result results = Result.success();
            if (CommonUtils.notEmpty(success))
                results.setMessage(GlobalException.format(success));
            return results;
        } else {
            return Result.error(HttpStatusConstant.PRECONDITION_FAILED.getValue(), GlobalException.format(errorHandler.getFirstErrorMessage()));
        }
    }

    public static Result check(BindingResult bindingResult) {
        return Result.error(HttpStatusConstant.VERIFICATION_FAILED.getValue(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    
    public static Result check(ErrorHandler errorHandler) {
        return check(errorHandler, null);
    }
    
    public ResponseEntity<Result> asOkResponseEntity() {
        return ResponseEntity
                .ok()
                .body(this);
    }
    
    public String toJson() {
    	return JsonUtils.toJson(this);
    }
}
