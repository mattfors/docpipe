package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "data_doc_hdr")
@IdClass(DataDocHdr.DataDocHdrId.class)
public class DataDocHdr {

    @Id
    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    @Id
    @Column(name = "data_doc_hdr_id", nullable = false, length = 100)
    private String dataDocHdrId;

    @Column(name = "def_doc_id", nullable = false, length = 100)
    private String defDocId;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDataDocHdrId() {
        return dataDocHdrId;
    }

    public void setDataDocHdrId(String dataDocHdrId) {
        this.dataDocHdrId = dataDocHdrId;
    }

    public String getDefDocId() {
        return defDocId;
    }

    public void setDefDocId(String defDocId) {
        this.defDocId = defDocId;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public static class DataDocHdrId implements Serializable {
        private String tenantId;
        private String dataDocHdrId;

        public DataDocHdrId() {}

        public DataDocHdrId(String tenantId, String dataDocHdrId) {
            this.tenantId = tenantId;
            this.dataDocHdrId = dataDocHdrId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getDataDocHdrId() {
            return dataDocHdrId;
        }

        public void setDataDocHdrId(String dataDocHdrId) {
            this.dataDocHdrId = dataDocHdrId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataDocHdrId that = (DataDocHdrId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(dataDocHdrId, that.dataDocHdrId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, dataDocHdrId);
        }
    }
}
