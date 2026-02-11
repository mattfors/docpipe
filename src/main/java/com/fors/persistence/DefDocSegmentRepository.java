package com.fors.persistence;

import com.fors.persistence.entity.DefDocSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefDocSegmentRepository extends JpaRepository<DefDocSegment, DefDocSegment.DefDocSegmentId> {
    
    List<DefDocSegment> findByTenantIdAndDefDocId(String tenantId, String defDocId);
}
