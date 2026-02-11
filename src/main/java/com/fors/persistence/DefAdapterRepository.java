package com.fors.persistence;

import com.fors.persistence.entity.DefAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefAdapterRepository extends JpaRepository<DefAdapter, DefAdapter.DefAdapterId> {
    
    List<DefAdapter> findByTenantId(String tenantId);
}
