/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniajc.schoolpickup.security;

import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.util.Mocker;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

class WithMockAuthorityUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthorityUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthorityUser withUser) {
        Authentication authentication = null;
        switch (withUser.value()) {
            case "admin":
                authentication = getAdminAuthentication();
                break;
            case "parent":
                authentication = getParentAuthentication();
                break;
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    private Authentication getAdminAuthentication() {
        User user;
        user = Mocker.getUser(999999L);
        user.setEmail("admin@company.com");
        user.setParent(null);
        CustomUserDetails principal = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        token.setDetails(principal);
        return token;
    }

    private Authentication getParentAuthentication() {
        User user;
        user = Mocker.getUser(999998L);
        user.setEmail("parent@company.com");
        user.setParent(Mocker.getParent(user.getId()));
        CustomUserDetails principal = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        token.setDetails(principal);
        return token;
    }
}
