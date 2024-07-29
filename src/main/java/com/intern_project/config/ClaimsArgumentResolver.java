package com.intern_project.config;

import com.intern_project.user.domain.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.slf4j.*;

@Slf4j
@Component
public class ClaimsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean supported = parameter.getParameterType().equals(UserInfo.class);
        log.debug("supportsParameter: " + supported);
        return supported;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.debug("resolveArgument called");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        UserInfo userInfo = (UserInfo) request.getAttribute("userinfo");

        if (userInfo == null) {
            throw new IllegalArgumentException("UserInfo not found in request attributes");
        }

        return userInfo;
    }
}
