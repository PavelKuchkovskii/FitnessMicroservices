package org.kucher.itacademyfitness.dao.api;

import org.kucher.itacademyfitness.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductDao extends JpaRepository<Product, UUID> {
}
