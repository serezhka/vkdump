package com.github.serezhka.vkdump.util.converter;

import com.github.serezhka.vkdump.dao.entity.*;
import com.github.serezhka.vkdump.dto.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class EntityConverter {

    /**
     * MessageDTO -> MessageEntity
     */
    public static MessageEntity messageDtoToEntity(MessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageId(messageDTO.getId());
        messageEntity.setFromId(messageDTO.getFromId());
        Long userId = messageDTO.getUserId();
        Long chatId = messageDTO.getChatId();
        messageEntity.setDialogId(chatId != null ? 2000000000 + chatId : userId); // FIXME
        messageEntity.setDate(messageDTO.getDate());
        messageEntity.setReadState(messageDTO.getReadState());
        messageEntity.setOut(messageDTO.getOut());
        messageEntity.setBody(messageDTO.getBody());

        if (messageDTO.getFwdMessages() != null && !messageDTO.getFwdMessages().isEmpty()) {
            List<MessageEntity> fwdMessages = messageDTO.getFwdMessages().stream().map(message -> {
                MessageEntity fwdMessage = EntityConverter.messageDtoToEntity(message);
                fwdMessage.setParentMessage(messageEntity);
                fwdMessage.setDialogId(null);
                return fwdMessage;
            }).collect(Collectors.toList());
            messageEntity.setFwdMessages(fwdMessages);
        }

        if (messageDTO.getAttachments() != null && !messageDTO.getAttachments().isEmpty()) {
            List<AttachmentEntity> attachments = messageDTO.getAttachments().stream().map(attachment -> {
                AttachmentEntity attachmentEntity = attachmentDtoToEntity(attachment);
                attachmentEntity.setMessage(messageEntity);
                return attachmentEntity;
            }).collect(Collectors.toList());
            messageEntity.setAttachments(attachments);
        }

        return messageEntity;
    }

    /**
     * MessageEntity -> MessageDTO
     */
    public static MessageDTO messageEntityToDto(MessageEntity messageEntity) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(messageEntity.getMessageId());
        messageDTO.setFromId(messageEntity.getFromId());
        Long dialogId = messageEntity.getDialogId();
        messageDTO.setUserId(dialogId != null && dialogId < 2000000000 ? dialogId : null); // FIXME
        messageDTO.setChatId(dialogId != null && dialogId > 2000000000 ? dialogId - 2000000000 : null); // FIXME
        messageDTO.setDate(messageEntity.getDate());
        messageDTO.setReadState(messageEntity.getReadState());
        messageDTO.setOut(messageEntity.getOut());
        messageDTO.setBody(messageEntity.getBody());

        if (messageEntity.getFwdMessages() != null && !messageEntity.getFwdMessages().isEmpty()) {
            messageDTO.setFwdMessages(messageEntity.getFwdMessages().stream()
                    .map(EntityConverter::messageEntityToDto)
                    .collect(Collectors.toList()));
        }

        if (messageEntity.getAttachments() != null && !messageEntity.getAttachments().isEmpty()) {
            messageDTO.setAttachments(messageEntity.getAttachments().stream()
                    .map(EntityConverter::attachmentEntityToDto)
                    .collect(Collectors.toList()));
        }

        return messageDTO;
    }

    /**
     * AttachmentDTO -> AttachmentEntity
     */
    public static AttachmentEntity attachmentDtoToEntity(AttachmentDTO attachmentDTO) {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setType(attachmentDTO.getType());
        switch (attachmentDTO.getType()) {
            case "photo":
                attachmentEntity.setPhoto(photoDtoToEntity(attachmentDTO.getPhoto()));
                break;
            case "video":
                attachmentEntity.setVideo(videoDtoToEntity(attachmentDTO.getVideo()));
                break;
            case "audio":
                attachmentEntity.setAudio(audioDtoToEntity(attachmentDTO.getAudio()));
                break;
            case "doc":
                attachmentEntity.setDoc(docDtoToEntity(attachmentDTO.getDoc()));
                break;
            case "sticker":
                attachmentEntity.setSticker(stickerDtoToEntity(attachmentDTO.getSticker()));
                break;
            case "link":
                attachmentEntity.setLink(linkDtoToEntity(attachmentDTO.getLink()));
                break;
            case "gift":
                attachmentEntity.setGift(giftDtoToEntity(attachmentDTO.getGift()));
                break;
        }
        return attachmentEntity;
    }

    /**
     * AttachmentEntity -> AttachmentDTO
     */
    public static AttachmentDTO attachmentEntityToDto(AttachmentEntity attachmentEntity) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setType(attachmentEntity.getType());
        switch (attachmentEntity.getType()) {
            case "photo":
                attachmentDTO.setPhoto(photoEntityToDto(attachmentEntity.getPhoto()));
                break;
            case "video":
                attachmentDTO.setVideo(videoEntityToDto(attachmentEntity.getVideo()));
                break;
            case "audio":
                attachmentDTO.setAudio(audioEntityToDto(attachmentEntity.getAudio()));
                break;
            case "doc":
                attachmentDTO.setDoc(docEntityToDto(attachmentEntity.getDoc()));
                break;
            case "sticker":
                attachmentDTO.setSticker(stickerEntityToDto(attachmentEntity.getSticker()));
                break;
            case "link":
                attachmentDTO.setLink(linkEntityToDto(attachmentEntity.getLink()));
                break;
            case "gift":
                attachmentDTO.setGift(giftEntityToDto(attachmentEntity.getGift()));
                break;
        }
        return attachmentDTO;
    }

    /**
     * PhotoDTO -> PhotoEntity
     */
    public static PhotoEntity photoDtoToEntity(PhotoDTO photoDTO) {
        PhotoEntity photo = new PhotoEntity();
        photo.setPhotoId(photoDTO.getId());
        photo.setAlbumId(photoDTO.getAlbumId());
        photo.setOwnerId(photoDTO.getOwnerId());
        photo.setUserId(photoDTO.getUserId());
        photo.setText(photoDTO.getText());
        photo.setDate(photoDTO.getDate());
        photo.setPhoto75(photoDTO.getPhoto75());
        photo.setPhoto130(photoDTO.getPhoto130());
        photo.setPhoto604(photoDTO.getPhoto604());
        photo.setPhoto807(photoDTO.getPhoto807());
        photo.setPhoto1280(photoDTO.getPhoto1280());
        photo.setPhoto2560(photoDTO.getPhoto2560());
        photo.setWidth(photoDTO.getWidth());
        photo.setHeight(photoDTO.getHeight());
        return photo;
    }

    /**
     * PhotoEntity -> PhotoDTO
     */
    public static PhotoDTO photoEntityToDto(PhotoEntity photoEntity) {
        PhotoDTO photo = new PhotoDTO();
        photo.setId(photoEntity.getPhotoId());
        photo.setAlbumId(photoEntity.getAlbumId());
        photo.setOwnerId(photoEntity.getOwnerId());
        photo.setUserId(photoEntity.getUserId());
        photo.setText(photoEntity.getText());
        photo.setDate(photoEntity.getDate());
        photo.setPhoto75(photoEntity.getPhoto75());
        photo.setPhoto130(photoEntity.getPhoto130());
        photo.setPhoto604(photoEntity.getPhoto604());
        photo.setPhoto807(photoEntity.getPhoto807());
        photo.setPhoto1280(photoEntity.getPhoto1280());
        photo.setPhoto2560(photoEntity.getPhoto2560());
        photo.setWidth(photoEntity.getWidth());
        photo.setHeight(photoEntity.getHeight());
        return photo;
    }

    /**
     * VideoDTO -> VideoEntity
     */
    public static VideoEntity videoDtoToEntity(VideoDTO videoDTO) {
        VideoEntity video = new VideoEntity();
        video.setVideoId(videoDTO.getId());
        video.setOwnerId(videoDTO.getOwnerId());
        video.setTitle(videoDTO.getTitle());
        video.setDescription(videoDTO.getDescription());
        video.setDuration(videoDTO.getDuration());
        video.setPhoto130(videoDTO.getPhoto130());
        video.setPhoto320(videoDTO.getPhoto320());
        video.setPhoto640(videoDTO.getPhoto640());
        video.setPhoto800(videoDTO.getPhoto800());
        video.setDate(videoDTO.getDate());
        video.setAddingDate(videoDTO.getAddingDate());
        video.setViews(videoDTO.getViews());
        video.setComments(videoDTO.getComments());
        video.setPlayer(videoDTO.getPlayer());
        return video;
    }

    /**
     * VideoEntity -> VideoDTO
     */
    public static VideoDTO videoEntityToDto(VideoEntity videoEntity) {
        VideoDTO video = new VideoDTO();
        video.setId(videoEntity.getVideoId());
        video.setOwnerId(videoEntity.getOwnerId());
        video.setTitle(videoEntity.getTitle());
        video.setDescription(videoEntity.getDescription());
        video.setDuration(videoEntity.getDuration());
        video.setPhoto130(videoEntity.getPhoto130());
        video.setPhoto320(videoEntity.getPhoto320());
        video.setPhoto640(videoEntity.getPhoto640());
        video.setPhoto800(videoEntity.getPhoto800());
        video.setDate(videoEntity.getDate());
        video.setAddingDate(videoEntity.getAddingDate());
        video.setViews(videoEntity.getViews());
        video.setComments(videoEntity.getComments());
        video.setPlayer(videoEntity.getPlayer());
        return video;
    }

    /**
     * AudioDTO -> AudioEntity
     */
    public static AudioEntity audioDtoToEntity(AudioDTO audioDTO) {
        AudioEntity audio = new AudioEntity();
        audio.setAudioId(audioDTO.getId());
        audio.setOwnerId(audioDTO.getOwnerId());
        audio.setArtist(audioDTO.getArtist());
        audio.setTitle(audioDTO.getTitle());
        audio.setDuration(audioDTO.getDuration());
        audio.setDate(audioDTO.getDate());
        audio.setUrl(audioDTO.getUrl());
        return audio;
    }

    /**
     * AudioEntity -> AudioDTO
     */
    public static AudioDTO audioEntityToDto(AudioEntity audioEntity) {
        AudioDTO audio = new AudioDTO();
        audio.setId(audioEntity.getAudioId());
        audio.setOwnerId(audioEntity.getOwnerId());
        audio.setArtist(audioEntity.getArtist());
        audio.setTitle(audioEntity.getTitle());
        audio.setDuration(audioEntity.getDuration());
        audio.setDate(audioEntity.getDate());
        audio.setUrl(audioEntity.getUrl());
        return audio;
    }

    /**
     * DocDTO -> DocEntity
     */
    public static DocEntity docDtoToEntity(DocDTO docDTO) {
        DocEntity docEntity = new DocEntity();
        docEntity.setDocId(docDTO.getId());
        docEntity.setOwnerId(docDTO.getOwnerId());
        docEntity.setTitle(docDTO.getTitle());
        docEntity.setSize(docDTO.getSize());
        docEntity.setExt(docDTO.getExt());
        docEntity.setUrl(docDTO.getUrl());
        docEntity.setDate(docDTO.getDate());
        docEntity.setType(docDTO.getType());
        return docEntity;
    }

    /**
     * DocEntity -> DocDTO
     */
    public static DocDTO docEntityToDto(DocEntity docEntity) {
        DocDTO doc = new DocDTO();
        doc.setId(docEntity.getDocId());
        doc.setOwnerId(docEntity.getOwnerId());
        doc.setTitle(docEntity.getTitle());
        doc.setSize(docEntity.getSize());
        doc.setExt(docEntity.getExt());
        doc.setUrl(docEntity.getUrl());
        doc.setDate(docEntity.getDate());
        doc.setType(docEntity.getType());
        return doc;
    }

    /**
     * StickerDTO -> StickerEntity
     */
    public static StickerEntity stickerDtoToEntity(StickerDTO stickerDTO) {
        StickerEntity sticker = new StickerEntity();
        sticker.setStickerId(stickerDTO.getId());
        sticker.setProductId(stickerDTO.getProductId());
        sticker.setPhoto64(stickerDTO.getPhoto64());
        sticker.setPhoto128(stickerDTO.getPhoto128());
        sticker.setPhoto256(stickerDTO.getPhoto256());
        sticker.setPhoto352(stickerDTO.getPhoto352());
        sticker.setWidth(stickerDTO.getWidth());
        sticker.setHeight(stickerDTO.getHeight());
        return sticker;
    }

    /**
     * StickerEntity -> StickerDTO
     */
    public static StickerDTO stickerEntityToDto(StickerEntity stickerEntity) {
        StickerDTO sticker = new StickerDTO();
        sticker.setId(stickerEntity.getStickerId());
        sticker.setProductId(stickerEntity.getProductId());
        sticker.setPhoto64(stickerEntity.getPhoto64());
        sticker.setPhoto128(stickerEntity.getPhoto128());
        sticker.setPhoto256(stickerEntity.getPhoto256());
        sticker.setPhoto352(stickerEntity.getPhoto352());
        sticker.setWidth(stickerEntity.getWidth());
        sticker.setHeight(stickerEntity.getHeight());
        return sticker;
    }

    /**
     * LinkDTO -> LinkEntity
     */
    public static LinkEntity linkDtoToEntity(LinkDTO linkDTO) {
        LinkEntity link = new LinkEntity();
        link.setUrl(linkDTO.getUrl());
        link.setTitle(linkDTO.getTitle());
        link.setCaption(linkDTO.getCaption());
        link.setDescription(linkDTO.getDescription());
        return link;
    }

    /**
     * LinkEntity -> LinkDTO
     */
    public static LinkDTO linkEntityToDto(LinkEntity linkEntity) {
        LinkDTO link = new LinkDTO();
        link.setUrl(linkEntity.getUrl());
        link.setTitle(linkEntity.getTitle());
        link.setCaption(linkEntity.getCaption());
        link.setDescription(linkEntity.getDescription());
        return link;
    }

    /**
     * GiftDTO -> GiftEntity
     */
    public static GiftEntity giftDtoToEntity(GiftDTO giftDTO) {
        GiftEntity gift = new GiftEntity();
        gift.setGiftId(giftDTO.getId());
        gift.setThumb48(giftDTO.getThumb48());
        gift.setThumb96(giftDTO.getThumb96());
        gift.setThumb256(giftDTO.getThumb256());
        return gift;
    }

    /**
     * GiftEntity -> GiftDTO
     */
    public static GiftDTO giftEntityToDto(GiftEntity giftEntity) {
        GiftDTO gift = new GiftDTO();
        gift.setId(giftEntity.getGiftId());
        gift.setThumb48(giftEntity.getThumb48());
        gift.setThumb96(giftEntity.getThumb96());
        gift.setThumb256(giftEntity.getThumb256());
        return gift;
    }

    /**
     * UserDTO -> UserEntity
     */
    public static UserEntity userDtoToEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUserId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoto200Orig(userDTO.getPhoto200Orig());
        user.setScreenName(userDTO.getScreenName());
        user.setSex(userDTO.getSex());
        return user;
    }

    /**
     * UserEntity -> UserDTO
     */
    public static UserDTO userEntityToDto(UserEntity userEntity) {
        UserDTO user = new UserDTO();
        user.setId(userEntity.getUserId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPhoto200Orig(userEntity.getPhoto200Orig());
        user.setScreenName(userEntity.getScreenName());
        user.setSex(userEntity.getSex());
        return user;
    }
}
