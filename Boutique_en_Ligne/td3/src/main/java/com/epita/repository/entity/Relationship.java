package com.epita.repository.entity;

import static com.epita.repository.entity.Node.NodeType.PRODUCT;
import static com.epita.repository.entity.Node.NodeType.CUSTOMER;

import io.smallrye.common.constraint.NotNull;
import jakarta.ws.rs.BadRequestException;

public record Relationship(Node source, Node target, int amount) {

    public Relationship(final @NotNull Node source,
                        final @NotNull Node target,
                        final @NotNull int amount) {
        if (source.nodeType() != CUSTOMER || target.nodeType() != PRODUCT) {
            throw new BadRequestException("Invalid relationship: must be Customer-[PURCHASED]->Product");
        }
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public String findCypher() {
        return "FIXME";
    }

    public String createCypher() {
        return "FIXME";
    }

    public String updateCypher() {
        return "FIXME";
    }
}
