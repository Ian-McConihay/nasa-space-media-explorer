package com.mcconihay.nasamediaexplorer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a media item retrieved from a NASA data source.
 * <p>
 * Each item is uniquely identified by the combination of source API and NASA ID.
 * The entity stores metadata used for search, display, and user interaction.
 */
@Entity
@Table(
    name = "items",
    uniqueConstraints = @UniqueConstraint(
            name = "uq_item_source_nasa",
            columnNames = {"source_api", "nasa_id"}
    )
)
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_api", nullable = false, length = 40, columnDefinition = "varchar(40)")
    private NasaSourceApi sourceApi;

    @Column(name = "nasa_id", nullable = false, length = 120)
    private String nasaId;

    @Column(name = "title", length = 300)
    private String title;

    @Column(name = "media_type", length = 40)
    private String mediaType;

    @Lob
    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "asset_url", length = 500)
    private String assetUrl;

    @Column(name = "apod_date")
    private LocalDate apodDate;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CollectionItemEntity> collectionItems = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnnotationEntity> annotations = new HashSet<>();

    public ItemEntity() { }

    /**
     * Automatically sets the fetch timestamp when the entity is first persisted.
     */
    @PrePersist
    private void onCreate() {
        if (fetchedAt == null) {
            fetchedAt = LocalDateTime.now();
        }
    }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public NasaSourceApi getSourceApi() { return sourceApi; }
    public void setSourceApi(NasaSourceApi sourceApi) { this.sourceApi = sourceApi; }

    public String getNasaId() { return nasaId; }
    public void setNasaId(String nasaId) { this.nasaId = nasaId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getAssetUrl() { return assetUrl; }
    public void setAssetUrl(String assetUrl) { this.assetUrl = assetUrl; }

    public LocalDate getApodDate() { return apodDate; }
    public void setApodDate(LocalDate apodDate) { this.apodDate = apodDate; }

    public LocalDateTime getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(LocalDateTime fetchedAt) { this.fetchedAt = fetchedAt; }

    public Set<CollectionItemEntity> getCollectionItems() { return collectionItems; }
    public void setCollectionItems(Set<CollectionItemEntity> collectionItems) { this.collectionItems = collectionItems; }

    public Set<AnnotationEntity> getAnnotations() { return annotations; }
    public void setAnnotations(Set<AnnotationEntity> annotations) { this.annotations = annotations; }
}