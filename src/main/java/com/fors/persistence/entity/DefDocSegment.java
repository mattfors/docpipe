package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "def_doc_segment")
@IdClass(DefDocSegment.DefDocSegmentId.class)
public class DefDocSegment {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Id
    @Column(name = "def_doc_id", length = 50)
    private String defDocId;

    @Id
    @Column(name = "def_doc_segment_id", length = 50)
    private String defDocSegmentId;

    @Column(name = "parent_def_doc_segment_id", length = 50)
    private String parentDefDocSegmentId;

    @Column(name = "name", length = 200)
    private String name;

    public DefDocSegment() {
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

    public String getDefDocSegmentId() {
        return defDocSegmentId;
    }

    public void setDefDocSegmentId(String defDocSegmentId) {
        this.defDocSegmentId = defDocSegmentId;
    }

    public String getParentDefDocSegmentId() {
        return parentDefDocSegmentId;
    }

    public void setParentDefDocSegmentId(String parentDefDocSegmentId) {
        this.parentDefDocSegmentId = parentDefDocSegmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class DefDocSegmentId implements Serializable {
        private String tenantId;
        private String defDocId;
        private String defDocSegmentId;

        public DefDocSegmentId() {
        }

        public DefDocSegmentId(String tenantId, String defDocId, String defDocSegmentId) {
            this.tenantId = tenantId;
            this.defDocId = defDocId;
            this.defDocSegmentId = defDocSegmentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefDocSegmentId that = (DefDocSegmentId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(defDocId, that.defDocId) &&
                   Objects.equals(defDocSegmentId, that.defDocSegmentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, defDocId, defDocSegmentId);
        }
    }
}
