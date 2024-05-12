package aiss.videoMiner.repository;

import aiss.videoMiner.model.Caption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptionRepository extends JpaRepository<Caption,String> {
    Page<Caption> findByLanguage(String language, Pageable pageable);
}
