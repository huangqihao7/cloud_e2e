package com.qihao.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 13:58
 */

public class WithoutLoginHandler implements AuthenticationEntryPoint, InitializingBean {

    private static final Log logger = LogFactory
            .getLog(LoginUrlAuthenticationEntryPoint.class);

    private PortMapper portMapper = new PortMapperImpl();

    private PortResolver portResolver = new PortResolverImpl();

    private String loginFormUrl;

    private boolean forceHttps = false;

    private boolean useForward = false;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ObjectMapper objectMapper;

    public WithoutLoginHandler(String loginFormUrl) {
        Assert.notNull(loginFormUrl, "loginFormUrl cannot be null");
        this.loginFormUrl = loginFormUrl;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(
                StringUtils.hasText(loginFormUrl)
                        && UrlUtils.isValidRedirectUrl(loginFormUrl),
                "loginFormUrl must be specified and must be a valid redirect URL");
        if (useForward && UrlUtils.isAbsoluteUrl(loginFormUrl)) {
            throw new IllegalArgumentException(
                    "useForward must be false if using an absolute loginFormURL");
        }
        Assert.notNull(portMapper, "portMapper must be specified");
        Assert.notNull(portResolver, "portResolver must be specified");
    }

    protected String determineUrlToUseForThisRequest(HttpServletRequest request,
                                                     HttpServletResponse response, AuthenticationException exception) {
        return getLoginFormUrl();
    }

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        Map<String, Object> map = new HashMap<>();
        map.put("code", 100);
        map.put("msg","未登录");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }

    protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
                                                 HttpServletResponse response, AuthenticationException authException) {

        String loginForm = determineUrlToUseForThisRequest(request, response, authException);

        if (UrlUtils.isAbsoluteUrl(loginForm)) {
            return loginForm;
        }

        int serverPort = portResolver.getServerPort(request);
        String scheme = request.getScheme();

        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

        urlBuilder.setScheme(scheme);
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(serverPort);
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setPathInfo(loginForm);

        if (forceHttps && "http".equals(scheme)) {
            Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

            if (httpsPort != null) {
                // Overwrite scheme and port in the redirect URL
                urlBuilder.setScheme("https");
                urlBuilder.setPort(httpsPort.intValue());
            }
            else {
                logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port "
                        + serverPort);
            }
        }
        return urlBuilder.getUrl();
    }

    protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request)
            throws IOException, ServletException {

        int serverPort = portResolver.getServerPort(request);
        Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

        if (httpsPort != null) {
            RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
            urlBuilder.setScheme("https");
            urlBuilder.setServerName(request.getServerName());
            urlBuilder.setPort(httpsPort.intValue());
            urlBuilder.setContextPath(request.getContextPath());
            urlBuilder.setServletPath(request.getServletPath());
            urlBuilder.setPathInfo(request.getPathInfo());
            urlBuilder.setQuery(request.getQueryString());

            return urlBuilder.getUrl();
        }
        logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port "
                + serverPort);
        return null;
    }

    public void setForceHttps(boolean forceHttps) {
        this.forceHttps = forceHttps;
    }

    protected boolean isForceHttps() {
        return forceHttps;
    }

    public String getLoginFormUrl() {
        return loginFormUrl;
    }

    public void setPortMapper(PortMapper portMapper) {
        Assert.notNull(portMapper, "portMapper cannot be null");
        this.portMapper = portMapper;
    }

    protected PortMapper getPortMapper() {
        return portMapper;
    }

    public void setPortResolver(PortResolver portResolver) {
        Assert.notNull(portResolver, "portResolver cannot be null");
        this.portResolver = portResolver;
    }

    protected PortResolver getPortResolver() {
        return portResolver;
    }

    public void setUseForward(boolean useForward) {
        this.useForward = useForward;
    }

    protected boolean isUseForward() {
        return useForward;
    }
}
