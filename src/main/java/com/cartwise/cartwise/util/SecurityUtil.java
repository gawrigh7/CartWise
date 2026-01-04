package com.cartwise.cartwise.util;


import com.cartwise.cartwise.model.UsersPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UsersPrinciple) {
            UsersPrinciple userPrincipal = (UsersPrinciple) authentication.getPrincipal();
            return userPrincipal.getUsersId();
        }

        throw new IllegalStateException("User is not authenticated");
    }
}
