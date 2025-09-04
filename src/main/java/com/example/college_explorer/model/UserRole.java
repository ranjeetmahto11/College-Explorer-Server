package com.example.college_explorer.model;

public enum UserRole {
    ADMIN;

    private String roleName;

    UserRole() {
        this.roleName = this.name();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        // Allow setting custom role name (not typical for enums)
        this.roleName = roleName;
    }
}
