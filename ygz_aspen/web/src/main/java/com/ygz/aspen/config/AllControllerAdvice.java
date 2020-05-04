package com.ygz.aspen.config;

import com.ygz.aspen.common.base.BusinessException;
import com.ygz.aspen.common.base.UnauthorizedException;
import com.ygz.aspen.vo.ResponseModel;
import com.ygz.aspen.vo.ResultMsgEnum;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller统一异常处理
 * @author : ygz
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
        logger.error("请求失败：{}", ex);
        return new ResponseModel<>(ResultMsgEnum.FAILED);
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseModel handleUnauthorized() {
        return new ResponseModel(ResultMsgEnum.UNAUTHORIZED);
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
        return new ResponseModel(ResultMsgEnum.USER_NO_POWER);
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
        logger.info("操作失败：" + e.getMessage());
        return new ResponseModel(ResultMsgEnum.ERROR.getCode(), e.getMessage(), null);
    }


}