package com.github.serezhka.vkdump.dao.online;

import com.github.serezhka.vkdump.dao.online.entity.FaveEntity;
import com.github.serezhka.vkdump.util.converter.EntityConverter;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.fave.responses.GetPhotosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Repository
public class FaveRepository {

    private final UserActor tokenOwner;
    private final VkApiClient vkApiClient;

    @Autowired
    public FaveRepository(UserActor tokenOwner,
                          VkApiClient vkApiClient) {
        this.tokenOwner = tokenOwner;
        this.vkApiClient = vkApiClient;
    }

    public Page<FaveEntity> findByType(String type, Pageable pageable) throws ClientException, ApiException {
        switch (type) {
            case "photo":
                GetPhotosResponse response = vkApiClient.fave().getPhotos(tokenOwner).offset(pageable.getOffset()).count(pageable.getPageSize()).execute();
                List<FaveEntity> favedPhotos = response.getItems().stream()
                        .map(EntityConverter::photoToEntity)
                        .map(photo -> {
                            FaveEntity faveEntity = new FaveEntity();
                            faveEntity.setType("photo");
                            faveEntity.setPhoto(photo);
                            return faveEntity;
                        })
                        .collect(Collectors.toList());
                return new PageImpl<>(favedPhotos, pageable, response.getCount());
            default:
                return new PageImpl<>(Collections.emptyList());
        }
    }
}
