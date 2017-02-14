package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.online.FaveRepository;
import com.github.serezhka.vkdump.dao.online.entity.FaveEntity;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Service
public class FaveService {

    private final FaveRepository faveRepository;

    @Autowired
    public FaveService(FaveRepository faveRepository) {
        this.faveRepository = faveRepository;
    }

    public Page<FaveEntity> getFaves(String type, Pageable pageable) throws ClientException, ApiException {
        return faveRepository.findByType(type, pageable);
    }
}
