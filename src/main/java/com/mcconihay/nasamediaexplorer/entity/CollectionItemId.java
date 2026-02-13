package com.mcconihay.nasamediaexplorer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for the collection_items table.
 * Combines collection ID and item ID.
 */
@Embeddable
public class CollectionItemId implements Serializable {

    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "item_id")
    private Long itemId;

    public CollectionItemId() { }

    public CollectionItemId(Long collectionId, Long itemId) {
        this.collectionId = collectionId;
        this.itemId = itemId;
    }

    public Long getCollectionId() { return collectionId; }
    public void setCollectionId(Long collectionId) { this.collectionId = collectionId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionItemId)) return false;
        CollectionItemId that = (CollectionItemId) o;
        return Objects.equals(collectionId, that.collectionId)
                && Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, itemId);
    }
}