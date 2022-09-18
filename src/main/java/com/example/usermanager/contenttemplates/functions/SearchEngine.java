package com.example.usermanager.contenttemplates.functions;


import com.example.usermanager.contenttemplates.entities.Employee;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchEngine {
    private final EntityManager entityManager;

    public List<Employee> findByCriteria(SearchCriteria searchCriteria) {

        List<Employee> itemsOnPage = buildListQuery(searchCriteria).getResultList();

        return itemsOnPage;
    }

    private TypedQuery<Employee> buildListQuery(SearchCriteria searchCriteria) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        final Root<Employee> processMetadataRoot = criteriaQuery.from(Employee.class);

        List<Predicate> criteriaList = prepareQueryConditions(searchCriteria, criteriaBuilder, criteriaQuery, processMetadataRoot);
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery);
    }

    private List<Predicate> prepareQueryConditions(SearchCriteria searchCriteria, CriteriaBuilder criteriaBuilder,
                                                   CriteriaQuery<?> criteriaQuery, Root<Employee> processMetadataRoot) {

        sortSqlCriteriaPrepare( searchCriteria.getProcessSortCriteria(), criteriaBuilder, criteriaQuery, processMetadataRoot);
        return searchSqlCriteriaPrepare(searchCriteria.getProcessSearchCriteria(), criteriaBuilder, processMetadataRoot);
    }

    public CriteriaBuilder sortSqlCriteriaPrepare(ProcessSortCriteria processSortCriteria, CriteriaBuilder criteriaBuilder,
                                                  CriteriaQuery<?> criteriaQuery, Root<Employee> processMetadataRoot) {

        String sortOrder = processSortCriteria.getSortOrder(); ;

        if (!(StringUtils.isBlank(sortOrder))) {
            for (Expression<?> ex : sortParameterCreation(processSortCriteria, processMetadataRoot)) {
                criteriaQuery.orderBy(Arrays.asList(sortOrder.equalsIgnoreCase("ASC") ? criteriaBuilder.asc(ex) : criteriaBuilder.desc(ex)));
            }
        }

        return criteriaBuilder;
    }

    private List<Expression<?>> sortParameterCreation(ProcessSortCriteria processSortCriteria, Root<Employee> processMetadataRoot) {

        List<Expression<?>> expressions = new ArrayList<>();

        return expressions;
    }

    private List<Predicate> searchSqlCriteriaPrepare(ProcessSearchCriteria processSearchCriteria, CriteriaBuilder criteriaBuilder,
                                                     Root<Employee> processMetadataRoot) {

        List<Predicate> searchCriteriaList = new ArrayList<>();

        if (!StringUtils.isBlank(processSearchCriteria.getName())) {
            Predicate predicateFilter = criteriaBuilder.like(
                    processMetadataRoot.get("name"), "%" + processSearchCriteria.getName() + "%");
            searchCriteriaList.add(predicateFilter);
        }

        if (!StringUtils.isBlank(processSearchCriteria.getSurname())) {
            Predicate predicateFilter = criteriaBuilder.like(
                    processMetadataRoot.get("surname"), "%" + processSearchCriteria.getSurname() + "%");
            searchCriteriaList.add(predicateFilter);
        }

        if (!StringUtils.isBlank(processSearchCriteria.getGrade())) {
            Predicate predicateFilter = criteriaBuilder.like(
                    processMetadataRoot.get("grade"), "%" + processSearchCriteria.getSurname() + "%");
            searchCriteriaList.add(predicateFilter);
        }

        if (!StringUtils.isBlank(processSearchCriteria.getSalary())) {
            Predicate predicateFilter = criteriaBuilder.like(
                    processMetadataRoot.get("salary"), "%" + processSearchCriteria.getSurname() + "%");
            searchCriteriaList.add(predicateFilter);
        }

        if (!StringUtils.isBlank(processSearchCriteria.getId())) {
            Predicate predicateFilter = criteriaBuilder.like(
                    processMetadataRoot.get("id"), "%" + processSearchCriteria.getSurname() + "%");
            searchCriteriaList.add(predicateFilter);
        }


        return searchCriteriaList;
    }

}
