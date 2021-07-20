
package com.hongkun.aspect;

import com.hongkun.bean.common.LogAdminApi;

import com.hongkun.commons.Constants;
import com.hongkun.commons.cache.TokenCache;
import com.hongkun.service.apply.LogAdminApiService;
import com.hongkun.until.CommonUntil;
import com.hongkun.until.CookieUtil;
import com.hongkun.until.LoggerUtil;

import com.hongkun.model.vo.uc.UvEroleUserVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


@Slf4j
@Aspect
@Component
public class LogAopAction {

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private LogAdminApiService logAdminApiService;

    @Value("${spring.profiles.active}")
    private String environment;

    @Pointcut("execution(public * com.hongkun.controller..*(..))" +
            " && !execution(public * com.hongkun.controller.CheckDatabaseController.*(..))) " +
            " && !execution(public * com.hongkun.controller.FileUploadController.*(..))) ")
    private void pointCutMethod() {
    }

    //执行切点 之前
    @Before("pointCutMethod()")
    public void exBefore(JoinPoint pjp) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
    }


    /**
     * 记录操作日志
     * 使用上面定义的切入点
     *
     * @param joinPoint
     */
    @After("pointCutMethod()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        //userBrowseSourceHistoryService.saveBrowseUsersLog(request,joinPoint);

        Long start = System.currentTimeMillis();
        LogAdminApi logAdminApi = new LogAdminApi();
        logAdminApi.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        logAdminApi.setCreateTime(new Date());

        Cookie cookie = CookieUtil.get(request, Constants.COOKIE_KEY);
        if (cookie != null) {
            String tokenKey = cookie.getValue();
            UvEroleUserVO loginUser = tokenCache.getToken(tokenKey);
            if (loginUser != null) {
                logAdminApi.setUserId(loginUser.getUserId());
                logAdminApi.setUserName(loginUser.getAccountName());
            }
        } else if (!environment.equals("dev")) {
            //throw new GlobalException(ResultEnum.COOKIE_MISSING);
        }


        // 下面开始获取 ip，targetType，remark，action
        try {

            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();


            Map<String, String> map = getLogMark(joinPoint);
            logAdminApi.setLogPathUrl(url);
            logAdminApi.setLogAction(uri);

            if (!uri.contains("/api/hkdc-admin-api/fileUpload/uploadTsq.do")) {

                logAdminApi.setLogRemark(map.get(LoggerUtil.LOG_OPERATE));
                logAdminApi.setLogIp(LoggerUtil.getCliectIp(request));


                Object[] args = joinPoint.getArgs();
                String params = "";
                //获取请求参数集合并进行遍历拼接
                if (args.length > 0) {
                    if ("POST".equals(method)) {
                        params = CommonUntil.getPostParams(args);
                    } else if ("GET".equals(method)) {
                        params = queryString;
                    }
                }


                logAdminApi.setLogMethod(params);
                logAdminApi.setLogMethodName(method);

                /**
                 * 获取所有请求头信息
                 */
                Map<String, String> headMap = new HashMap<>();
                Enumeration er = request.getHeaderNames();//获取请求头的所有name值
                while (er.hasMoreElements()) {
                    String name = (String) er.nextElement();
                    String value = request.getHeader(name);
                    headMap.put(name, value);
                    //System.out.println(name+"="+value);
                }


                logAdminApiService.saveLogAdminApi(logAdminApi);

                log.info("【" + logAdminApi.getLogRemark() + "】【请求开始===地址】:" + url + ",类型:" + method);
                log.info("【" + logAdminApi.getLogRemark() + "】【请求开始===请求head】:" + headMap.toString());
                log.info("【" + logAdminApi.getLogRemark() + "】【" + logAdminApi.getLogAction() + "】请求开始===请求参数:" + params);
                log.info("【" + logAdminApi.getLogRemark() + "】【记录日志apiLog】" + logAdminApi);

            }


        } catch (Exception e) {
            log.error("插入日志异常", e);
        }
        Long end = System.currentTimeMillis();
        log.info("记录日志消耗时间:" + (end - start) + "ms");
    }


    private Map<String, String> getLogMark(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String, String> map = new HashMap<>(3);
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                ApiOperation logAnnotation = method.getAnnotation(ApiOperation.class);
                if (logAnnotation == null) {
                    break;
                }
                map.put(LoggerUtil.LOG_OPERATE, logAnnotation.value());

            }
        }
        return map;
    }

    private static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private static Map<String, Object> getFieldsName(JoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }


    private String getMobileType(HttpServletRequest request) {

        String mobileType = "";

        String User_Agent = request.getHeader("User-Agent");
        if (User_Agent.contains("Android") || User_Agent.contains("Linux")) {
            mobileType = "Android移动客户端";
            System.out.println("Android移动客户端");
            if (User_Agent.contains("MicroMessenger")) {
                mobileType = "Android移动客户端-微信";

            }
        } else if (User_Agent.contains("iPhone")) {

            mobileType = "iPhone移动客户端";

            System.out.println("iPhone移动客户端");
            if (User_Agent.contains("MicroMessenger")) {

                mobileType = "iPhone移动客户端-微信";


                System.out.println("微信");
            }
        } else if (User_Agent.contains("iPad")) {

            mobileType = "iPad客户端";

            System.out.println("iPad客户端");
            if (User_Agent.contains("MicroMessenger")) {
                mobileType = "iPad客户端-微信";

                System.out.println("微信");
            }
        } else if (User_Agent.contains("Windows")) {

            mobileType = "Windows";

            System.out.println("Windows");
        }
        return mobileType;

    }

}
