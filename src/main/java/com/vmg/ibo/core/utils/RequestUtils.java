package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.constant.RequestConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RequestUtils {

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!CommonUtils.empty(ip)
                && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!CommonUtils.empty(ip)
                && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static HttpServletRequest get() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static boolean isAjax(HttpServletRequest request) {
        String contentTypeHeader = request.getHeader("Content-Type");
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");

        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }

    public static boolean isPjax(HttpServletRequest request) {
        String pjaxHeader = request.getHeader("X-PJAX");

        return pjaxHeader != null && pjaxHeader.contains("true");
    }

    public static String getContentType(HttpServletRequest request) {
        return request.getHeader("Content-Type");
    }

    public static String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * @param parameterMap
     * @param key
     * @return parameter value
     */
    public static String getValue(Map<String, String[]> parameterMap, String key) {
        String[] values = parameterMap.get(key);
        if (CommonUtils.notEmpty(values)) {
            return values[0];
        }
        return null;
    }

    /**
     * getEncodePath
     *
     * @param path
     * @param queryString
     * @return encoded path
     */
    public static String getEncodePath(String path, String queryString) {
        String url = path;
        try {
            if (CommonUtils.notEmpty(queryString)) {
                url += "?" + queryString;
            }
            url = URLEncoder.encode(url, RequestConstant.DEFAULT_CHARSET_NAME.getValue());
        } catch (UnsupportedEncodingException e) {
            url = RequestConstant.BLANK.getValue();
        }
        return url;
    }

    /**
     * @param request
     * @return accept
     */
    public static String getAccept(HttpServletRequest request) {
        return request.getHeader("Accept");
    }

    /**
     * @param cookies
     * @param name
     * @return cookie
     */
    public static Cookie getCookie(Cookie[] cookies, String name) {
        if (CommonUtils.notEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * @param contextPath
     * @param response
     * @param name
     * @param value
     * @param expiry
     * @param domain
     * @return cookie
     */
    public static Cookie addCookie(String contextPath, HttpServletResponse response, String name, String value, Integer expiry,
                                   String domain) {
        Cookie cookie = new Cookie(name, value);
        if (CommonUtils.notEmpty(expiry)) {
            cookie.setMaxAge(expiry);
        }
        if (CommonUtils.notEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(CommonUtils.empty(contextPath) ? RequestConstant.SEPARATOR.getValue() : contextPath);
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * @param contextPath
     * @param response
     * @param name
     * @param domain
     */
    public static void cancleCookie(String contextPath, HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(CommonUtils.empty(contextPath) ? RequestConstant.SEPARATOR.getValue() : contextPath);
        if (CommonUtils.notEmpty(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }
}
