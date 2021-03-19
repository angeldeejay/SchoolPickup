package com.uniajc.schoolpickup.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockAuthorityUserSecurityContextFactory.class)
@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
public @interface WithMockAuthorityUser {

    String value() default "";
}
