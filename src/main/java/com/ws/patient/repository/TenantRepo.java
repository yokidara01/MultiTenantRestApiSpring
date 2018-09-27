package com.ws.patient.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.patient.model.Tenant;



@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long>{

}
