package com.mcconihay.nasamediaexplorer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Join entity linking collections and items.
 * Represents the inclusion of an item within a collection.
 */
@Entity
@Table(name = "collection_items")
public class CollectionItemEntity {

    @EmbeddedId
    private CollectionItemId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("collectionId")
    @JoinColumn(name = "collection_id", nullable = false)
    private CollectionEntity collection;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("itemId")
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    public CollectionItemEntity() { }

    /**
     * Sets the timestamp when the item is added to a collection.
     */
    @PrePersist
    private void onCreate() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();
        }
    }

    public CollectionItemId getId() { return id; }
    public void setId(CollectionItemId id) { this.id = id; }

    public CollectionEntity getCollection() { return collection; }
    public void setCollection(CollectionEntity collection) { this.collection = collection; }

    public ItemEntity getItem() { return item; }
    public void setItem(ItemEntity item) { this.item = item; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}