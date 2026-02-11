package com.fors.service;

import com.fors.persistence.*;
import com.fors.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConfigurationTreeService {

    private final DefTenantRepository tenantRepository;
    private final DefAdapterRepository adapterRepository;
    private final DefAdapterRouteRepository routeRepository;
    private final DefDocRepository docRepository;
    private final DefDocSegmentRepository segmentRepository;
    private final DefDocFieldRepository fieldRepository;

    public ConfigurationTreeService(
            DefTenantRepository tenantRepository,
            DefAdapterRepository adapterRepository,
            DefAdapterRouteRepository routeRepository,
            DefDocRepository docRepository,
            DefDocSegmentRepository segmentRepository,
            DefDocFieldRepository fieldRepository) {
        this.tenantRepository = tenantRepository;
        this.adapterRepository = adapterRepository;
        this.routeRepository = routeRepository;
        this.docRepository = docRepository;
        this.segmentRepository = segmentRepository;
        this.fieldRepository = fieldRepository;
    }

    public List<TenantNode> loadConfigurationTree() {
        List<DefTenant> tenants = tenantRepository.findAll();
        
        return tenants.stream()
                .map(this::buildTenantNode)
                .collect(Collectors.toList());
    }

    private TenantNode buildTenantNode(DefTenant tenant) {
        TenantNode node = new TenantNode();
        node.setTenantId(tenant.getTenantId());
        node.setName(tenant.getName());
        
        // Load adapters
        List<DefAdapter> adapters = adapterRepository.findByTenantId(tenant.getTenantId());
        node.setAdapters(adapters.stream()
                .map(adapter -> buildAdapterNode(tenant.getTenantId(), adapter))
                .collect(Collectors.toList()));
        
        // Load documents
        List<DefDoc> docs = docRepository.findByTenantId(tenant.getTenantId());
        node.setDocuments(docs.stream()
                .map(doc -> buildDocumentNode(tenant.getTenantId(), doc))
                .collect(Collectors.toList()));
        
        return node;
    }

    private AdapterNode buildAdapterNode(String tenantId, DefAdapter adapter) {
        AdapterNode node = new AdapterNode();
        node.setAdapterId(adapter.getDefAdapterId());
        node.setName(adapter.getName());
        node.setEnabled(adapter.getEnabled());
        
        // Load routes
        List<DefAdapterRoute> routes = routeRepository.findByTenantIdAndDefAdapterId(
                tenantId, adapter.getDefAdapterId());
        node.setRoutes(routes.stream()
                .map(this::buildRouteNode)
                .collect(Collectors.toList()));
        
        return node;
    }

    private RouteNode buildRouteNode(DefAdapterRoute route) {
        RouteNode node = new RouteNode();
        node.setRouteId(route.getDefAdapterRouteId());
        node.setPath(route.getPath());
        node.setDefDocId(route.getDefDocId());
        node.setEnabled(route.getEnabled());
        return node;
    }

    private DocumentNode buildDocumentNode(String tenantId, DefDoc doc) {
        DocumentNode node = new DocumentNode();
        node.setDocId(doc.getDefDocId());
        node.setName(doc.getName());
        
        // Load segments
        List<DefDocSegment> segments = segmentRepository.findByTenantIdAndDefDocId(
                tenantId, doc.getDefDocId());
        
        // Build segment hierarchy
        Map<String, SegmentNode> segmentMap = new HashMap<>();
        for (DefDocSegment segment : segments) {
            SegmentNode segNode = new SegmentNode();
            segNode.setSegmentId(segment.getDefDocSegmentId());
            segNode.setName(segment.getName());
            segNode.setParentSegmentId(segment.getParentDefDocSegmentId());
            
            // Load fields
            List<DefDocField> fields = fieldRepository.findByTenantIdAndDefDocIdAndDefDocSegmentIdOrderByFieldOrder(
                    tenantId, doc.getDefDocId(), segment.getDefDocSegmentId());
            segNode.setFields(fields.stream()
                    .map(this::buildFieldNode)
                    .collect(Collectors.toList()));
            
            segmentMap.put(segment.getDefDocSegmentId(), segNode);
        }
        
        // Build hierarchy
        List<SegmentNode> rootSegments = new ArrayList<>();
        for (SegmentNode segNode : segmentMap.values()) {
            if (segNode.getParentSegmentId() == null) {
                rootSegments.add(segNode);
            } else {
                SegmentNode parent = segmentMap.get(segNode.getParentSegmentId());
                if (parent != null) {
                    parent.addChild(segNode);
                }
            }
        }
        
        node.setSegments(rootSegments);
        return node;
    }

    private FieldNode buildFieldNode(DefDocField field) {
        FieldNode node = new FieldNode();
        node.setFieldId(field.getDefDocFieldId());
        node.setName(field.getName());
        node.setFieldOrder(field.getFieldOrder());
        node.setLength(field.getLength());
        return node;
    }

    // Tree node classes
    public static class TenantNode {
        private String tenantId;
        private String name;
        private List<AdapterNode> adapters;
        private List<DocumentNode> documents;

        public String getTenantId() { return tenantId; }
        public void setTenantId(String tenantId) { this.tenantId = tenantId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<AdapterNode> getAdapters() { return adapters; }
        public void setAdapters(List<AdapterNode> adapters) { this.adapters = adapters; }
        public List<DocumentNode> getDocuments() { return documents; }
        public void setDocuments(List<DocumentNode> documents) { this.documents = documents; }
    }

    public static class AdapterNode {
        private String adapterId;
        private String name;
        private Boolean enabled;
        private List<RouteNode> routes;

        public String getAdapterId() { return adapterId; }
        public void setAdapterId(String adapterId) { this.adapterId = adapterId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Boolean getEnabled() { return enabled; }
        public void setEnabled(Boolean enabled) { this.enabled = enabled; }
        public List<RouteNode> getRoutes() { return routes; }
        public void setRoutes(List<RouteNode> routes) { this.routes = routes; }
    }

    public static class RouteNode {
        private String routeId;
        private String path;
        private String defDocId;
        private Boolean enabled;

        public String getRouteId() { return routeId; }
        public void setRouteId(String routeId) { this.routeId = routeId; }
        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
        public String getDefDocId() { return defDocId; }
        public void setDefDocId(String defDocId) { this.defDocId = defDocId; }
        public Boolean getEnabled() { return enabled; }
        public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    }

    public static class DocumentNode {
        private String docId;
        private String name;
        private List<SegmentNode> segments;

        public String getDocId() { return docId; }
        public void setDocId(String docId) { this.docId = docId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<SegmentNode> getSegments() { return segments; }
        public void setSegments(List<SegmentNode> segments) { this.segments = segments; }
    }

    public static class SegmentNode {
        private String segmentId;
        private String name;
        private String parentSegmentId;
        private List<FieldNode> fields;
        private List<SegmentNode> children = new ArrayList<>();

        public String getSegmentId() { return segmentId; }
        public void setSegmentId(String segmentId) { this.segmentId = segmentId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getParentSegmentId() { return parentSegmentId; }
        public void setParentSegmentId(String parentSegmentId) { this.parentSegmentId = parentSegmentId; }
        public List<FieldNode> getFields() { return fields; }
        public void setFields(List<FieldNode> fields) { this.fields = fields; }
        public List<SegmentNode> getChildren() { return children; }
        public void setChildren(List<SegmentNode> children) { this.children = children; }
        public void addChild(SegmentNode child) { this.children.add(child); }
    }

    public static class FieldNode {
        private String fieldId;
        private String name;
        private Integer fieldOrder;
        private Integer length;

        public String getFieldId() { return fieldId; }
        public void setFieldId(String fieldId) { this.fieldId = fieldId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getFieldOrder() { return fieldOrder; }
        public void setFieldOrder(Integer fieldOrder) { this.fieldOrder = fieldOrder; }
        public Integer getLength() { return length; }
        public void setLength(Integer length) { this.length = length; }
    }
}
