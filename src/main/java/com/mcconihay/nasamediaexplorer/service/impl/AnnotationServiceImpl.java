package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.AnnotationRepository;
import com.mcconihay.nasamediaexplorer.service.AnnotationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Default implementation of {@link AnnotationService}.
 *
 * Handles persistence and retrieval of annotations that users
 * attach to NASA media items.
 */
@Service
@Transactional
public class AnnotationServiceImpl implements AnnotationService {

    private final AnnotationRepository annotationRepository;

    /**
     * Constructs the service with required repository dependency.
     *
     * @param annotationRepository repository for annotation persistence
     */
    public AnnotationServiceImpl(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
    }

    /**
     * Saves a new annotation.
     */
    @Override
    public AnnotationEntity saveAnnotation(AnnotationEntity annotation) {
        return annotationRepository.save(annotation);
    }

    /**
     * Retrieves annotations associated with a specific item.
     */
    @Override
    public List<AnnotationEntity> findByItemId(Long itemId) {
        return annotationRepository.findByItemItemId(itemId);
    }

    /**
     * Deletes an annotation if it exists.
     */
    @Override
    public void deleteAnnotation(Long id) {
        AnnotationEntity existing = annotationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annotation not found: " + id));
        annotationRepository.delete(existing);
    }
}