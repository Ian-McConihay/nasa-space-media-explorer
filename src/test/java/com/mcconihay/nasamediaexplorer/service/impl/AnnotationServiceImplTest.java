package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.AnnotationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnotationServiceImplTest {

    @Mock
    private AnnotationRepository annotationRepository;

    @InjectMocks
    private AnnotationServiceImpl annotationService;

    @Test
    void saveAnnotation_ShouldSaveAnnotation() {
        AnnotationEntity annotation = new AnnotationEntity();
        annotation.setNote("Interesting crater");

        when(annotationRepository.save(annotation)).thenReturn(annotation);

        AnnotationEntity result = annotationService.saveAnnotation(annotation);

        assertNotNull(result);
        assertEquals("Interesting crater", result.getNote());
        verify(annotationRepository).save(annotation);
    }

    @Test
    void findByItemId_ShouldReturnAnnotations() {
        when(annotationRepository.findByItemItemId(1L))
                .thenReturn(List.of(new AnnotationEntity(), new AnnotationEntity()));

        List<AnnotationEntity> result = annotationService.findByItemId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void deleteAnnotation_ShouldDelete_WhenAnnotationExists() {
        AnnotationEntity annotation = new AnnotationEntity();
        annotation.setAnnotationId(1L);

        when(annotationRepository.findById(1L)).thenReturn(Optional.of(annotation));

        annotationService.deleteAnnotation(1L);

        verify(annotationRepository).delete(annotation);
    }

    @Test
    void deleteAnnotation_ShouldThrow_WhenAnnotationMissing() {
        when(annotationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> annotationService.deleteAnnotation(1L));
        verify(annotationRepository, never()).delete(any());
    }
}