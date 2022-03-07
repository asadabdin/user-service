package org.company.user.service.homework.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class PageRequestDTO<T> {

    @NotNull
    private T criteria;

    @Min(0)
    @NotNull
    private Integer page;

    @Min(1)
    @NotNull
    private Integer pageSize;

}
