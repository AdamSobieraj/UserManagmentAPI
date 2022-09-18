package com.example.usermanager.contenttemplates.functions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private ProcessSearchCriteria processSearchCriteria;
    private ProcessSortCriteria processSortCriteria;
}
