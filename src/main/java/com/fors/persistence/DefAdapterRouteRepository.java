package com.fors.persistence;

import com.fors.persistence.entity.DefAdapterRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefAdapterRouteRepository extends JpaRepository<DefAdapterRoute, DefAdapterRoute.DefAdapterRouteId> {
    
    List<DefAdapterRoute> findByTenantIdAndDefAdapterId(String tenantId, String defAdapterId);
}
