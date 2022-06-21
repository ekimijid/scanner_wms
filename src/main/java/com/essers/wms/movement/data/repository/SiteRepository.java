package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site,Long> {
    Site findSiteById(Long id);

}
