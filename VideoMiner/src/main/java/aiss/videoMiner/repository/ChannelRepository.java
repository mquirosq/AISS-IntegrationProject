package aiss.videoMiner.repository;

import aiss.videoMiner.model.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {

    Page<Channel> findByNameContaining(String name, Pageable pageable);

}
