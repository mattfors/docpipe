package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "def_doc_field")
@IdClass(DefDocField.DefDocFieldId.class)
public class DefDocField {

    @Id
    @Column(name = "tenant_id", length = 50)
    private String tenantId;

    @Id
    @Column(name = "def_doc_id", length = 50)
    private String defDocId;

    @Id
    @Column(name = "def_doc_segment_id", length = 50)
    private String defDocSegmentId;

    @Id
    @Column(name = "def_doc_field_id", length = 50)
    private String defDocFieldId;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "field_order", nullable = false)
    private Integer fieldOrder;

    @Column(name = "length", nullable = false)
    private Integer length;

    public DefDocField() {
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

    public String getDefDocFieldId() {
        return defDocFieldId;
    }

    public void setDefDocFieldId(String defDocFieldId) {
        this.defDocFieldId = defDocFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public static class DefDocFieldId implements Serializable {
        private String tenantId;
        private String defDocId;
        private String defDocSegmentId;
        private String defDocFieldId;

        public DefDocFieldId() {
        }

        public DefDocFieldId(String tenantId, String defDocId, String defDocSegmentId, String defDocFieldId) {
            this.tenantId = tenantId;
            this.defDocId = defDocId;
            this.defDocSegmentId = defDocSegmentId;
            this.defDocFieldId = defDocFieldId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefDocFieldId that = (DefDocFieldId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(defDocId, that.defDocId) &&
                   Objects.equals(defDocSegmentId, that.defDocSegmentId) &&
                   Objects.equals(defDocFieldId, that.defDocFieldId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, defDocId, defDocSegmentId, defDocFieldId);
        }
    }
}
