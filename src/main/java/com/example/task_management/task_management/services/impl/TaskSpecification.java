package com.example.task_management.task_management.services.impl;

import com.example.task_management.task_management.dto.FilteredTaskDto;
import com.example.task_management.task_management.entity.TaskEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification implements Specification<TaskEntity> {

    private FilteredTaskDto filteredTaskDto;

    public TaskSpecification(FilteredTaskDto filteredTaskDto) {
        this.filteredTaskDto = filteredTaskDto;
    }

    @Override
    public Predicate toPredicate(Root<TaskEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filteredTaskDto.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), filteredTaskDto.getStatus()));
        }

        if (filteredTaskDto.getSearch() != null) {
            String searchPattern = "%" + filteredTaskDto.getSearch().toLowerCase() + "%";
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern);
            Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern);
            predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
