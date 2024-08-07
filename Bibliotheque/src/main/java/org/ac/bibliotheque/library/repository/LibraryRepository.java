package org.ac.bibliotheque.library.repository;

import org.ac.bibliotheque.library.domain.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
