package com.fors.service;

import com.fors.persistence.*;
import com.fors.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentDataService {

    private final DataDocHdrRepository dataDocHdrRepository;
    private final DataDocSegmentRepository dataDocSegmentRepository;
    private final DefDocRepository defDocRepository;
    private final DefDocSegmentRepository defDocSegmentRepository;
    private final DefDocFieldRepository defDocFieldRepository;

    public DocumentDataService(
            DataDocHdrRepository dataDocHdrRepository,
            DataDocSegmentRepository dataDocSegmentRepository,
            DefDocRepository defDocRepository,
            DefDocSegmentRepository defDocSegmentRepository,
            DefDocFieldRepository defDocFieldRepository) {
        this.dataDocHdrRepository = dataDocHdrRepository;
        this.dataDocSegmentRepository = dataDocSegmentRepository;
        this.defDocRepository = defDocRepository;
        this.defDocSegmentRepository = defDocSegmentRepository;
        this.defDocFieldRepository = defDocFieldRepository;
    }

    public List<DocumentInstanceNode> loadDocumentInstances(String tenantId) {
        List<DataDocHdr> headers = dataDocHdrRepository.findByTenantIdOrderByReceivedAtDesc(tenantId);
        
        return headers.stream()
                .map(hdr -> buildDocumentInstanceNode(tenantId, hdr))
                .collect(Collectors.toList());
    }

    private DocumentInstanceNode buildDocumentInstanceNode(String tenantId, DataDocHdr hdr) {
        DocumentInstanceNode node = new DocumentInstanceNode();
        node.setDataDocHdrId(hdr.getDataDocHdrId());
        node.setDefDocId(hdr.getDefDocId());
        node.setReceivedAt(hdr.getReceivedAt());
        
        // Get document name
        DefDoc defDoc = defDocRepository.findById(
                new DefDoc.DefDocId(tenantId, hdr.getDefDocId()))
                .orElse(null);
        if (defDoc != null) {
            node.setDocumentName(defDoc.getName());
        }
        
        // Load segments
        List<DataDocSegment> segments = dataDocSegmentRepository
                .findByTenantIdAndDataDocHdrIdOrderBySeqNo(tenantId, hdr.getDataDocHdrId());
        
        node.setSegments(segments.stream()
                .map(seg -> buildSegmentInstanceNode(tenantId, seg))
                .collect(Collectors.toList()));
        
        return node;
    }

    private SegmentInstanceNode buildSegmentInstanceNode(String tenantId, DataDocSegment segment) {
        SegmentInstanceNode node = new SegmentInstanceNode();
        node.setDataDocSegmentId(segment.getDataDocSegmentId());
        node.setDefDocSegmentId(segment.getDefDocSegmentId());
        node.setSeqNo(segment.getSeqNo());
        
        // Get segment name
        DefDocSegment defSegment = defDocSegmentRepository.findById(
                new DefDocSegment.DefDocSegmentId(
                        tenantId, segment.getDefDocId(), segment.getDefDocSegmentId()))
                .orElse(null);
        
        if (defSegment != null) {
            node.setSegmentName(defSegment.getName());
            
            // Unpack segment data
            List<DefDocField> fields = defDocFieldRepository
                    .findByTenantIdAndDefDocIdAndDefDocSegmentIdOrderByFieldOrder(
                            tenantId, segment.getDefDocId(), segment.getDefDocSegmentId());
            
            node.setFields(unpackSegmentData(segment.getSegmentData(), fields));
        }
        
        return node;
    }

    private List<FieldValueNode> unpackSegmentData(byte[] segmentData, List<DefDocField> fields) {
        List<FieldValueNode> fieldValues = new ArrayList<>();
        int offset = 0;
        
        for (DefDocField field : fields) {
            FieldValueNode fieldValue = new FieldValueNode();
            fieldValue.setFieldName(field.getName());
            fieldValue.setFieldOrder(field.getFieldOrder());
            fieldValue.setLength(field.getLength());
            
            // Extract field value from packed data
            if (offset + field.getLength() <= segmentData.length) {
                byte[] fieldBytes = Arrays.copyOfRange(segmentData, offset, offset + field.getLength());
                String value = new String(fieldBytes, StandardCharsets.UTF_8).trim();
                fieldValue.setValue(value);
                offset += field.getLength();
            } else {
                fieldValue.setValue("(data truncated)");
            }
            
            fieldValues.add(fieldValue);
        }
        
        return fieldValues;
    }

    public static class DocumentInstanceNode {
        private String dataDocHdrId;
        private String defDocId;
        private String documentName;
        private java.time.LocalDateTime receivedAt;
        private List<SegmentInstanceNode> segments;

        public String getDataDocHdrId() { return dataDocHdrId; }
        public void setDataDocHdrId(String dataDocHdrId) { this.dataDocHdrId = dataDocHdrId; }
        public String getDefDocId() { return defDocId; }
        public void setDefDocId(String defDocId) { this.defDocId = defDocId; }
        public String getDocumentName() { return documentName; }
        public void setDocumentName(String documentName) { this.documentName = documentName; }
        public java.time.LocalDateTime getReceivedAt() { return receivedAt; }
        public void setReceivedAt(java.time.LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
        public List<SegmentInstanceNode> getSegments() { return segments; }
        public void setSegments(List<SegmentInstanceNode> segments) { this.segments = segments; }
    }

    public static class SegmentInstanceNode {
        private String dataDocSegmentId;
        private String defDocSegmentId;
        private String segmentName;
        private Integer seqNo;
        private List<FieldValueNode> fields;

        public String getDataDocSegmentId() { return dataDocSegmentId; }
        public void setDataDocSegmentId(String dataDocSegmentId) { this.dataDocSegmentId = dataDocSegmentId; }
        public String getDefDocSegmentId() { return defDocSegmentId; }
        public void setDefDocSegmentId(String defDocSegmentId) { this.defDocSegmentId = defDocSegmentId; }
        public String getSegmentName() { return segmentName; }
        public void setSegmentName(String segmentName) { this.segmentName = segmentName; }
        public Integer getSeqNo() { return seqNo; }
        public void setSeqNo(Integer seqNo) { this.seqNo = seqNo; }
        public List<FieldValueNode> getFields() { return fields; }
        public void setFields(List<FieldValueNode> fields) { this.fields = fields; }
    }

    public static class FieldValueNode {
        private String fieldName;
        private Integer fieldOrder;
        private Integer length;
        private String value;

        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        public Integer getFieldOrder() { return fieldOrder; }
        public void setFieldOrder(Integer fieldOrder) { this.fieldOrder = fieldOrder; }
        public Integer getLength() { return length; }
        public void setLength(Integer length) { this.length = length; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}
