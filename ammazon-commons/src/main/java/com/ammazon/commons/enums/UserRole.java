package com.ammazon.commons.enums;

import java.util.Arrays;
import java.util.List;

/**
 * User role enumeration.
 * Represents different roles in the system.
 */
public enum UserRole {
    CUSTOMER("CUSTOMER", List.of("view_products", "create_order", "view_profile")),
    ADMIN("ADMIN", List.of("manage_products", "manage_orders", "manage_users")),
    VENDOR("VENDOR", List.of("manage_products", "view_orders")),
    SUPPORT("SUPPORT", List.of("view_orders", "view_users", "resolve_issues")),
    WAREHOUSE("WAREHOUSE", List.of("manage_inventory", "process_shipments"));

    private final String code;
    private final List<String> permissions;

    UserRole(String code, List<String> permissions) {
        this.code = code;
        this.permissions = permissions;
    }

    public String getCode() {
        return code;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public static UserRole fromCode(String code) {
        return Arrays.stream(values())
                .filter(role -> role.code.equals(code))
                .findFirst()
                .orElse(CUSTOMER);
    }
}