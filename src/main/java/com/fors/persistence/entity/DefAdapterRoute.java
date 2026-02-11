package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "def_adapter_route")
@IdClass(DefAdapterRoute.DefAdapterRouteId.class)
public class DefAdapterRoute {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Id
    @Column(name = "def_adapter_id", length = 50)
    private String defAdapterId;

    @Id
    @Column(name = "def_adapter_route_id", length = 50)
    private String defAdapterRouteId;

    @Column(name = "def_doc_id", length = 50, nullable = false)
    private String defDocId;

    @Column(name = "path", length = 500, nullable = false)
    private String path;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    public DefAdapterRoute() {
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

    public String getDefAdapterRouteId() {
        return defAdapterRouteId;
    }

    public void setDefAdapterRouteId(String defAdapterRouteId) {
        this.defAdapterRouteId = defAdapterRouteId;
    }

    public String getDefDocId() {
        return defDocId;
    }

    public void setDefDocId(String defDocId) {
        this.defDocId = defDocId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public static class DefAdapterRouteId implements Serializable {
        private String tenantId;
        private String defAdapterId;
        private String defAdapterRouteId;

        public DefAdapterRouteId() {
        }

        public DefAdapterRouteId(String tenantId, String defAdapterId, String defAdapterRouteId) {
            this.tenantId = tenantId;
            this.defAdapterId = defAdapterId;
            this.defAdapterRouteId = defAdapterRouteId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefAdapterRouteId that = (DefAdapterRouteId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(defAdapterId, that.defAdapterId) &&
                   Objects.equals(defAdapterRouteId, that.defAdapterRouteId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, defAdapterId, defAdapterRouteId);
        }
    }
}
