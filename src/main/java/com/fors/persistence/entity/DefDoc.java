package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "def_doc")
@IdClass(DefDoc.DefDocId.class)
public class DefDoc {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Id
    @Column(name = "def_doc_id", length = 50)
    private String defDocId;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    public DefDoc() {
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDefDocId() {
        return defDocId;
    }

    public void setDefDocId(String defDocId) {
        this.defDocId = defDocId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class DefDocId implements Serializable {
        private String tenantId;
        private String defDocId;

        public DefDocId() {
        }

        public DefDocId(String tenantId, String defDocId) {
            this.tenantId = tenantId;
            this.defDocId = defDocId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefDocId that = (DefDocId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(defDocId, that.defDocId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, defDocId);
        }
    }
}
