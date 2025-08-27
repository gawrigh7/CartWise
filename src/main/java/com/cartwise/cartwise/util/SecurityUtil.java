package com.cartwise.cartwise.util;

import com.cartwise.cartwise.model.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    private SecurityUtil() {}

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrinciple up)) {
            throw new IllegalStateException("User is not authenticated");
        }
        return up.getId();
    }
}
