package com.nhnacademy.frontserver1.presentation.dto.request.book;

import java.util.List;

public record UpdateBookQuantityRequest (
        List<Long> bookIdList,
        List<Integer> quantityList
)
{

}

