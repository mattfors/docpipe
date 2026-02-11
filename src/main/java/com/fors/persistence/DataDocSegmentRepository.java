package com.fors.persistence;

import com.fors.persistence.entity.DataDocSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDocSegmentRepository extends JpaRepository<DataDocSegment, DataDocSegment.DataDocSegmentId> {
    
    List<DataDocSegment> findByTenantIdAndDataDocHdrIdOrderBySeqNo(String tenantId, String dataDocHdrId);
    
    List<DataDocSegment> findByTenantIdAndDataDocHdrIdAndDefDocSegmentIdOrderBySeqNo(
            String tenantId, String dataDocHdrId, String defDocSegmentId);
}
