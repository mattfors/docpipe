package com.fors.persistence;

import com.fors.persistence.entity.DataDocHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDocHdrRepository extends JpaRepository<DataDocHdr, DataDocHdr.DataDocHdrId> {
    
    List<DataDocHdr> findByTenantIdOrderByReceivedAtDesc(String tenantId);
    
    List<DataDocHdr> findByTenantIdAndDefDocIdOrderByReceivedAtDesc(String tenantId, String defDocId);
}
