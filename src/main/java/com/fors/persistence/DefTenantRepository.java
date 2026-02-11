package com.fors.persistence;

import com.fors.persistence.entity.DefTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefTenantRepository extends JpaRepository<DefTenant, String> {
}
