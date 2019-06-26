package pl.tscript3r.photogram.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.tscript3r.photogram.domains.Post;

import java.time.LocalDateTime;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Slice<Post> findByUserIdAndValidIsTrue(Long userId, Pageable pageable);

    Slice<Post> findAllByValidIsTrue(Pageable pageable);

    @Transactional
    void deleteByCreationDateBefore(LocalDateTime expiryDate);

    Integer countByCreationDateBefore(LocalDateTime expiryDate);

}
