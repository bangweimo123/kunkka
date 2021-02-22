package com.leshiguang.arch.kunkka.web.controller.application;

import com.leshiguang.arch.kunkka.web.domain.base.KunkkaPaging;
import org.springframework.data.domain.PageRequest;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 11:47
 * @Description
 */
public class ApiController {

    protected PageRequest parsePageRequest(KunkkaPaging paging) {
        return PageRequest.of(paging.getPageIndex() - 1, paging.getPageSize());
    }
}
