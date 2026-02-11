package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "def_adapter")
@IdClass(DefAdapter.DefAdapterId.class)
public class DefAdapter {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Id
    @Column(name = "def_adapter_id", length = 50)
    private String defAdapterId;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    public DefAdapter() {
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDefAdapterId() {
        return defAdapterId;
    }

    public void setDefAdapterId(String defAdapterId) {
        this.defAdapterId = defAdapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public static class DefAdapterId implements Serializable {
        private String tenantId;
        private String defAdapterId;

        public DefAdapterId() {
        }

        public DefAdapterId(String tenantId, String defAdapterId) {
            this.tenantId = tenantId;
            this.defAdapterId = defAdapterId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefAdapterId that = (DefAdapterId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(defAdapterId, that.defAdapterId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, defAdapterId);
        }
    }
}
