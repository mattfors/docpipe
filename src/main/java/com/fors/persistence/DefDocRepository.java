package com.fors.persistence;

import com.fors.persistence.entity.DefDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefDocRepository extends JpaRepository<DefDoc, DefDoc.DefDocId> {
    
    List<DefDoc> findByTenantId(String tenantId);
}
