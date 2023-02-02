package com.akhianand.springrolejwt.custom;

import com.akhianand.springrolejwt.model.User;
import com.akhianand.springrolejwt.service.impl.CustomUserDetails;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

  public boolean isMember(Long id) {
        User user = ((CustomUserDetails) this.getPrincipal()).getUser();
        return user.getRoles().stream().map(role -> role.getId().longValue()) != null;
    }


    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}