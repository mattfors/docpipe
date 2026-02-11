package com.fors.persistence.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "data_doc_segment")
@IdClass(DataDocSegment.DataDocSegmentId.class)
public class DataDocSegment {

    @Id
    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    @Id
    @Column(name = "data_doc_hdr_id", nullable = false, length = 100)
    private String dataDocHdrId;

    @Id
    @Column(name = "data_doc_segment_id", nullable = false, length = 100)
    private String dataDocSegmentId;

    @Column(name = "def_doc_id", nullable = false, length = 100)
    private String defDocId;

    @Column(name = "def_doc_segment_id", nullable = false, length = 100)
    private String defDocSegmentId;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "segment_data", nullable = false)
    private byte[] segmentData;

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

    public String getDataDocSegmentId() {
        return dataDocSegmentId;
    }

    public void setDataDocSegmentId(String dataDocSegmentId) {
        this.dataDocSegmentId = dataDocSegmentId;
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

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public byte[] getSegmentData() {
        return segmentData;
    }

    public void setSegmentData(byte[] segmentData) {
        this.segmentData = segmentData;
    }

    public static class DataDocSegmentId implements Serializable {
        private String tenantId;
        private String dataDocHdrId;
        private String dataDocSegmentId;

        public DataDocSegmentId() {}

        public DataDocSegmentId(String tenantId, String dataDocHdrId, String dataDocSegmentId) {
            this.tenantId = tenantId;
            this.dataDocHdrId = dataDocHdrId;
            this.dataDocSegmentId = dataDocSegmentId;
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

        public String getDataDocSegmentId() {
            return dataDocSegmentId;
        }

        public void setDataDocSegmentId(String dataDocSegmentId) {
            this.dataDocSegmentId = dataDocSegmentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataDocSegmentId that = (DataDocSegmentId) o;
            return Objects.equals(tenantId, that.tenantId) &&
                   Objects.equals(dataDocHdrId, that.dataDocHdrId) &&
                   Objects.equals(dataDocSegmentId, that.dataDocSegmentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, dataDocHdrId, dataDocSegmentId);
        }
    }
}
