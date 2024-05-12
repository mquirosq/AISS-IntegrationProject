package aiss.videoMiner.repository;

import aiss.videoMiner.model.Channel;
import aiss.videoMiner.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {

    Page<Comment> findByTextContaining(String text, Pageable pageable);

}
