package aiss.videoMiner.repository;

import aiss.videoMiner.model.Channel;
import aiss.videoMiner.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Page<Video> findByNameContaining(String name, Pageable pageable);

}
