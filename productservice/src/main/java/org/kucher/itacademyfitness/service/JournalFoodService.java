package org.kucher.itacademyfitness.service;

import org.kucher.itacademyfitness.audit.AuditService;
import org.kucher.itacademyfitness.audit.dto.AuditDTO;
import org.kucher.itacademyfitness.audit.dto.enums.EEssenceType;
import org.kucher.itacademyfitness.config.exceptions.api.AlreadyChangedException;
import org.kucher.itacademyfitness.config.exceptions.api.NotFoundException;
import org.kucher.itacademyfitness.dao.api.IJournalFoodDao;
import org.kucher.itacademyfitness.dao.entity.JournalFood;
import org.kucher.itacademyfitness.dao.entity.builders.JournalFoodBuilder;
import org.kucher.itacademyfitness.security.entity.UserToJwt;
import org.kucher.itacademyfitness.service.api.IJournalFoodService;
import org.kucher.itacademyfitness.service.api.IProductService;
import org.kucher.itacademyfitness.service.api.IProfileService;
import org.kucher.itacademyfitness.service.api.IRecipeService;
import org.kucher.itacademyfitness.service.dto.JournalFoodDTO;
import org.kucher.itacademyfitness.service.dto.ProductDTO;
import org.kucher.itacademyfitness.service.dto.ProfileDTO;
import org.kucher.itacademyfitness.service.dto.RecipeDTO;
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
public class JournalFoodService implements IJournalFoodService {

    private final IJournalFoodDao dao;
    private final IProfileService profileService;
    private final IProductService productService;
    private final IRecipeService recipeService;
    private final AuditService auditService;
    private final ModelMapper mapper;

    public JournalFoodService(IJournalFoodDao dao, IProfileService profileService, IProductService productService, IRecipeService recipeService, AuditService auditService, ModelMapper mapper) {
        this.dao = dao;
        this.profileService = profileService;
        this.productService = productService;
        this.recipeService = recipeService;
        this.auditService = auditService;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public JournalFoodDTO create(JournalFoodDTO dto) {
        if(dto.getProduct() != null) {
            ProductDTO productDTO = productService.read(dto.getProduct().getUuid());
            dto.setProduct(productService.mapToEntity(productDTO));
        }
        else if(dto.getRecipe() != null) {
            RecipeDTO recipeDTO = recipeService.read(dto.getRecipe().getUuid());
            dto.setRecipe(recipeService.mapToEntity(recipeDTO));
        }

        dto.setUuid(UUID.randomUUID());
        dto.setDtCreate(LocalDateTime.now());
        dto.setDtUpdate(dto.getDtCreate());

        if(validate(dto)) {

            JournalFood journalFood = mapToEntity(dto);
            dao.save(journalFood);

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            audit.setUser(user);
            audit.setText("Create Journal Food");
            audit.setType(EEssenceType.JOURNAL_FOOD);
            audit.setId(dto.getUuid().toString());

            auditService.createAudit(audit);
        }

        return dto;
    }

    @Override
    @Transactional
    public JournalFoodDTO create(JournalFoodDTO dto, UUID profileUuid) {
        ProfileDTO profile = profileService.read(profileUuid);

        dto.setProfile(profileService.mapToEntity(profile));

        return create(dto);
    }

    @Override
    public Page<JournalFoodDTO> get(int page, int itemsPerPage, UUID profileUuid) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<JournalFood> journalFoods = dao.findAllByProfile_Uuid(profileUuid, pageable);

        return new PageImpl<> (journalFoods.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, journalFoods.getTotalElements());
    }

    @Override
    public JournalFoodDTO read(UUID uuid) {
        Optional<JournalFood> read = dao.findById(uuid);

        if(read.isPresent()) {
            return read.map(this::mapToDTO).orElse(null);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    @Deprecated
    public Page<JournalFoodDTO> get(int page, int itemsPerPage) {
        //SO BAD
        return null;
    }

    @Override
    @Transactional
    public JournalFoodDTO update(UUID uuid, LocalDateTime dtUpdate, JournalFoodDTO dto) {
        JournalFoodDTO read = this.read(uuid);

        if(read.getDtUpdate().isEqual(dtUpdate)) {
            read.setDtUpdate(LocalDateTime.now());
            read.setDtSupply(dto.getDtSupply());
            read.setRecipe(dto.getRecipe());
            read.setProduct(dto.getProduct());
            read.setWeight(dto.getWeight());

            if(validate(read)) {
                JournalFood journalFood = mapToEntity(read);
                dao.save(journalFood);

                //Create audit
                AuditDTO audit = new AuditDTO();
                UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                audit.setUser(user);
                audit.setText("Update Journal Food");
                audit.setType(EEssenceType.JOURNAL_FOOD);
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
    public boolean validate(JournalFoodDTO dto) {
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
    public JournalFoodDTO mapToDTO(JournalFood journalFood) {
        return mapper.map(journalFood, JournalFoodDTO.class);
    }

    @Override
    public JournalFood mapToEntity(JournalFoodDTO dto) {
        return JournalFoodBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setDtSupply(dto.getDtSupply())
                .setRecipe(dto.getRecipe())
                .setProduct(dto.getProduct())
                .setWeight(dto.getWeight())
                .setProfile(dto.getProfile())
                .build();
    }
}
