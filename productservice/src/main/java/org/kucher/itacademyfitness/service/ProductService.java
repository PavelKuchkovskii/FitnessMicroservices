package org.kucher.itacademyfitness.service;

import org.kucher.itacademyfitness.audit.AuditService;
import org.kucher.itacademyfitness.audit.dto.AuditDTO;
import org.kucher.itacademyfitness.audit.dto.enums.EEssenceType;
import org.kucher.itacademyfitness.config.exceptions.api.AlreadyChangedException;
import org.kucher.itacademyfitness.config.exceptions.api.NotFoundException;
import org.kucher.itacademyfitness.dao.api.IProductDao;
import org.kucher.itacademyfitness.dao.entity.Product;
import org.kucher.itacademyfitness.dao.entity.builders.ProductBuilder;
import org.kucher.itacademyfitness.security.entity.UserToJwt;
import org.kucher.itacademyfitness.service.api.IProductService;
import org.kucher.itacademyfitness.service.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService implements IProductService {

    private final IProductDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;

    public ProductService(IProductDao dao, ModelMapper mapper, AuditService auditService) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO dto) {
        dto.setUuid(UUID.randomUUID());
        dto.setDtCreate(LocalDateTime.now());
        dto.setDtUpdate(dto.getDtCreate());

        if(validate(dto)) {

            Product product = mapToEntity(dto);
            dao.save(product);

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            audit.setUser(user);
            audit.setText("Create Product");
            audit.setType(EEssenceType.PRODUCT);
            audit.setId(dto.getUuid().toString());

            auditService.createAudit(audit);
        }

        return dto;
    }

    @Override
    public ProductDTO read(UUID uuid) {
        Optional<Product> read = dao.findById(uuid);

        if(read.isPresent()) {
            return read.map(this::mapToDTO).orElse(null);
        }
        else {
            throw new NotFoundException();
        }

    }

    @Override
    public Page<ProductDTO> get(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<Product> products = dao.findAll(pageable);

        return new PageImpl<> (products.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Override
    @Transactional
    public ProductDTO update(UUID uuid, LocalDateTime dtUpdate, ProductDTO dto) {

        ProductDTO read = this.read(uuid);

        if(read.getDtUpdate().isEqual(dtUpdate)) {
            read.setDtUpdate(LocalDateTime.now());
            read.setTitle(dto.getTitle());
            read.setWeight(dto.getWeight());
            read.setCalories(dto.getCalories());
            read.setFats(dto.getFats());
            read.setCarbohydrates(dto.getCarbohydrates());
            read.setProteins(dto.getProteins());

            if(validate(read)) {
                Product product = mapToEntity(read);
                dao.save(product);

                //Create audit
                AuditDTO audit = new AuditDTO();
                UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                audit.setUser(user);
                audit.setText("Update Product");
                audit.setType(EEssenceType.PRODUCT);
                audit.setId(dto.getUuid().toString());

                auditService.createAudit(audit);
            }

            return read;
        }
        else {
            throw new AlreadyChangedException();
        }
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate) {

    }

    @Override
    public boolean validate(ProductDTO dto) {
        if(dto.getUuid() == null) {
            throw new IllegalArgumentException("Uuid cannot be null");
        }
        else if(dto.getDtCreate() == null){
            throw new IllegalArgumentException("Date create cannot be null");
        }
        else if(dto.getDtUpdate()== null){
            throw new IllegalArgumentException("Date update cannot be null");
        }

        return true;
    }

    @Override
    public ProductDTO mapToDTO(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public Product mapToEntity(ProductDTO dto) {
        return ProductBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setTitle(dto.getTitle())
                .setWeight(dto.getWeight())
                .setCalories(dto.getCalories())
                .setFats(dto.getFats())
                .setCarbohydrates(dto.getCarbohydrates())
                .setProteins(dto.getProteins())
                .build();
    }


}
