package com.github.serezhka.vkdump.util.converter;

import com.github.serezhka.vkdump.dao.entity.*;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.audio.AudioFull;
import com.vk.api.sdk.objects.base.Link;
import com.vk.api.sdk.objects.base.Sticker;
import com.vk.api.sdk.objects.docs.Doc;
import com.vk.api.sdk.objects.gifts.Layout;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.video.Video;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public class EntityConverter {

    /**
     * Message -> MessageEntity
     */
    public static MessageEntity messageToEntity(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageId(message.getId());
        messageEntity.setFromId(message.getFromId());
        Integer userId = message.getUserId();
        Integer chatId = message.getChatId();
        messageEntity.setDialogId(chatId != null ? 2000000000 + chatId : userId);
        messageEntity.setDate(message.getDate());
        messageEntity.setReadState(message.isReadState());
        messageEntity.setOut(message.isOut());
        messageEntity.setBody(message.getBody());

        if (message.getFwdMessages() != null && !message.getFwdMessages().isEmpty()) {
            List<MessageEntity> fwdMessages = message.getFwdMessages().stream().map(tmp -> {
                MessageEntity fwdMessage = EntityConverter.messageToEntity(tmp);
                fwdMessage.setParentMessage(messageEntity);
                fwdMessage.setFromId(tmp.getUserId());
                fwdMessage.setDialogId(null);
                return fwdMessage;
            }).collect(Collectors.toList());
            messageEntity.setFwdMessages(fwdMessages);
        }

        if (message.getAttachments() != null && !message.getAttachments().isEmpty()) {
            List<AttachmentEntity> attachments = message.getAttachments().stream().map(attachment -> {
                AttachmentEntity attachmentEntity = attachmentToEntity(attachment);
                attachmentEntity.setMessage(messageEntity);
                return attachmentEntity;
            }).collect(Collectors.toList());
            messageEntity.setAttachments(attachments);
        }

        return messageEntity;
    }

    /**
     * MessageAttachment -> AttachmentEntity
     */
    public static AttachmentEntity attachmentToEntity(MessageAttachment attachment) {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setType(attachment.getType().getValue());
        switch (attachment.getType()) {
            case PHOTO:
                attachmentEntity.setPhoto(photoToEntity(attachment.getPhoto()));
                break;
            case AUDIO:
                attachmentEntity.setAudio(audioToEntity(attachment.getAudio()));
                break;
            case VIDEO:
                attachmentEntity.setVideo(videoToEntity(attachment.getVideo()));
                break;
            case DOC:
                attachmentEntity.setDoc(docToEntity(attachment.getDoc()));
                break;
            case LINK:
                attachmentEntity.setLink(linkToEntity(attachment.getLink()));
                break;
            case MARKET:
                break;
            case MARKET_MARKET_ALBUM:
                break;
            case GIFT:
                attachmentEntity.setGift(giftToEntity(attachment.getGift()));
                break;
            case STICKER:
                attachmentEntity.setSticker(stickerToEntity(attachment.getSticker()));
                break;
            case WALL:
                break;
            case WALL_REPLY:
                break;
        }
        return attachmentEntity;
    }

    /**
     * Photo -> PhotoEntity
     */
    public static PhotoEntity photoToEntity(Photo photo) {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setPhotoId(photo.getId());
        photoEntity.setAlbumId(photo.getAlbumId());
        photoEntity.setOwnerId(photo.getOwnerId());
        photoEntity.setUserId(photo.getUserId());
        photoEntity.setText(photo.getText());
        photoEntity.setDate(photo.getDate());
        photoEntity.setPhoto75(photo.getPhoto75());
        photoEntity.setPhoto130(photo.getPhoto130());
        photoEntity.setPhoto604(photo.getPhoto604());
        photoEntity.setPhoto807(photo.getPhoto807());
        photoEntity.setPhoto1280(photo.getPhoto1280());
        photoEntity.setPhoto2560(photo.getPhoto2560());
        photoEntity.setWidth(photo.getWidth());
        photoEntity.setHeight(photo.getHeight());
        return photoEntity;
    }

    /**
     * Audio -> AudioEntity
     */
    public static AudioEntity audioToEntity(Audio audio) {
        AudioEntity audioEntity = new AudioEntity();
        audioEntity.setAudioId(audio.getId());
        audioEntity.setOwnerId(audio.getOwnerId());
        audioEntity.setArtist(audio.getArtist());
        audioEntity.setTitle(audio.getTitle());
        if (audio instanceof AudioFull) {
            audioEntity.setDuration(((AudioFull) audio).getDuration());
            audioEntity.setDate(((AudioFull) audio).getDate());
        }
        audioEntity.setUrl(audio.getUrl());
        return audioEntity;
    }

    /**
     * Video -> VideoEntity
     */
    public static VideoEntity videoToEntity(Video video) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setVideoId(video.getId());
        videoEntity.setOwnerId(video.getOwnerId());
        videoEntity.setTitle(video.getTitle());
        videoEntity.setDescription(video.getDescription());
        videoEntity.setDuration(video.getDuration());
        videoEntity.setPhoto130(video.getPhoto130());
        videoEntity.setPhoto320(video.getPhoto320());
        videoEntity.setPhoto800(video.getPhoto800());
        videoEntity.setDate(video.getDate());
        videoEntity.setAddingDate(video.getAddingDate());
        videoEntity.setViews(video.getViews());
        videoEntity.setComments(video.getComments());
        videoEntity.setPlayer(video.getPlayer());
        return videoEntity;
    }

    /**
     * Doc -> DocEntity
     */
    public static DocEntity docToEntity(Doc doc) {
        DocEntity docEntity = new DocEntity();
        docEntity.setDocId(doc.getId());
        docEntity.setOwnerId(doc.getOwnerId());
        docEntity.setTitle(doc.getTitle());
        docEntity.setSize(doc.getSize());
        docEntity.setExt(doc.getExt());
        docEntity.setUrl(doc.getUrl());
        docEntity.setDate(doc.getDate());
        docEntity.setType(doc.getType());
        return docEntity;
    }

    /**
     * Link -> LinkEntity
     */
    public static LinkEntity linkToEntity(Link link) {
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setUrl(link.getUrl());
        linkEntity.setTitle(link.getTitle());
        linkEntity.setCaption(link.getCaption());
        linkEntity.setDescription(link.getDescription());
        return linkEntity;
    }

    /**
     * Sticker -> StickerEntity
     */
    public static StickerEntity stickerToEntity(Sticker sticker) {
        StickerEntity stickerEntity = new StickerEntity();
        stickerEntity.setStickerId(sticker.getId());
        stickerEntity.setProductId(sticker.getProductId());
        stickerEntity.setPhoto64(sticker.getPhoto64());
        stickerEntity.setPhoto128(sticker.getPhoto128());
        stickerEntity.setPhoto256(sticker.getPhoto256());
        stickerEntity.setPhoto352(sticker.getPhoto352());
        stickerEntity.setWidth(sticker.getWidth());
        stickerEntity.setHeight(sticker.getHeight());
        return stickerEntity;
    }

    /**
     * Gift -> GiftEntity
     */
    public static GiftEntity giftToEntity(Layout gift) {
        GiftEntity giftEntity = new GiftEntity();
        giftEntity.setGiftId(gift.getId());
        giftEntity.setThumb48(gift.getThumb48());
        giftEntity.setThumb96(gift.getThumb96());
        giftEntity.setThumb256(gift.getThumb256());
        return giftEntity;
    }

    /**
     * User -> UserEntity
     */
    public static UserEntity userToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        if (user instanceof UserFull) {
            userEntity.setPhoto200Orig(((UserFull) user).getPhoto200Orig());
        }
        userEntity.setScreenName(user.getScreenName());
        userEntity.setSex(user.getSex() == null ? 0 : user.getSex().getValue());
        return userEntity;
    }
}
