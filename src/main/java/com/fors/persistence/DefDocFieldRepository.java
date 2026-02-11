package com.fors.persistence;

import com.fors.persistence.entity.DefDocField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefDocFieldRepository extends JpaRepository<DefDocField, DefDocField.DefDocFieldId> {
    
    List<DefDocField> findByTenantIdAndDefDocIdAndDefDocSegmentIdOrderByFieldOrder(
        String tenantId, String defDocId, String defDocSegmentId);
}
