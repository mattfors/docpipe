package com.fors.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "def_tenant")
public class DefTenant {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    public DefTenant() {
    }

    public DefTenant(String tenantId, String name) {
        this.tenantId = tenantId;
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
