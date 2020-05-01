package com.ygz.aspen.config;

import com.ygz.aspen.common.base.BusinessException;
import com.ygz.aspen.common.base.UnauthorizedException;
import com.ygz.aspen.vo.PublicResultConstant;
import com.ygz.aspen.vo.ResponseHelper;
import com.ygz.aspen.vo.ResponseModel;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller统一异常处理
 * @author : liugh
 * @date : 2018/05/08
 */
@ControllerAdvice
public class AllControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(AllControllerAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseModel<String> errorHandler(Exception ex) {
        ex.printStackTrace();
        logger.error("请求失败：{}", ex.getMessage());
        return ResponseHelper.validationFailure(PublicResultConstant.FAILED);
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseModel<String> handleUnauthorized() {
        return ResponseHelper.validationFailure(PublicResultConstant.USER_NO_PERMITION);
    }

    /**
     * 捕捉shiro的异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResponseModel<String> handleShiroException(ShiroException e) {
        return ResponseHelper.validationFailure(PublicResultConstant.USER_NO_PERMITION);
    }

    /**
     * 捕捉BusinessException自定义抛出的异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseModel handleBusinessException(BusinessException e) {
        if (e instanceof BusinessException) {
            logger.info("操作失败：" + e.getMessage());
            return ResponseHelper.validationFailure("操作失败：" + e.getMessage());
        }
        return ResponseHelper.validationFailure(PublicResultConstant.ERROR);
    }


}