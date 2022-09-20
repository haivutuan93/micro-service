package com.eureka.zuul.exception;

import com.netflix.client.ClientException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Slf4j
@Component
public class CustomErrorFilter extends ZuulFilter {

    private static final String ERROR_STATUS_CODE_KEY = "error.status_code";
    public static final String DEFAULT_ERR_MSG = " System is busy , Please try again later ";
    @Override
    public String filterType() {
        return "post";
    }
    @Override
    public int filterOrder() {
        return 0;
    }
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.containsKey(ERROR_STATUS_CODE_KEY);
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = ctx.getRequest();
            int statusCode = (Integer) ctx.get(ERROR_STATUS_CODE_KEY);
            String message = (String) ctx.get("error.message");
            if (ctx.containsKey("error.exception")) {
                Throwable e = (Exception) ctx.get("error.exception");
                Throwable re = getOriginException(e);
                if(re instanceof ConnectException){
                    message = "Real Service Connection refused";
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }else if(re instanceof SocketTimeoutException){
                    message = "Real Service Timeout";
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }else if(re instanceof ClientException){
                    message = re.getMessage();
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }else{
                    log.warn("Error during filtering",e);
                }
            }
            if(StringUtils.isBlank(message))message = DEFAULT_ERR_MSG;
            request.setAttribute("javax.servlet.error.status_code", statusCode);
            request.setAttribute("javax.servlet.error.message", message);
//            WebUtils.responseOutJson(ctx.getResponse(), JsonUtils.toJson(new WrapperResponse<>(statusCode, message)));
        } catch (Exception e) {
            String error = "Error during filtering[ErrorFilter]";
            log.error(error,e);
//            WebUtils.responseOutJson(ctx.getResponse(), JsonUtils.toJson(new WrapperResponse<>(500, error)));
        }
        return null;
    }
    private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
}
