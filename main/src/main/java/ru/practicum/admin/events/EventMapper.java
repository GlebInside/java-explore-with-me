package ru.practicum.admin.events;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.events.dto.*;
import ru.practicum.admin.events.model.Event;
import ru.practicum.admin.users.UserMapper;
import ru.practicum.admin.users.model.User;
import ru.practicum.priv.events.dto.UpdateEventRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static Event createFromDto(NewEventDto dto, User initiator, Category category) {
        var model = new Event();
        model.setEventDate(dto.getEventDate());
        model.setAnnotation(dto.getAnnotation());
        model.setCategory(category);
        model.setDescription(dto.getDescription());
        model.setEventDate(dto.getEventDate());
        model.setInitiator(initiator);
        model.setPaid(dto.getPaid());
        if (dto.getLocation() != null) {
            model.setLat(dto.getLocation().getLat());
            model.setLon(dto.getLocation().getLon());
        }
        model.setParticipantLimit(dto.getParticipantLimit());
        model.setTitle(dto.getTitle());
        model.setRequestModeration(dto.getRequestModeration());
        return model;
    }

    public static EventFullDto toFullDto(Event model) {
        var dto = new EventFullDto();
        dto.setAnnotation(model.getAnnotation());
        dto.setCategory(CategoryMapper.fromModel(model.getCategory()));
        dto.setEventDate(model.getEventDate());
        dto.setConfirmedRequests(model.getConfirmedRequests());
        dto.setDescription(model.getDescription());
        dto.setCreatedOn(model.getCreatedOn());
        dto.setId(model.getId());
        dto.setInitiator(UserMapper.shortDtoFromModel(model.getInitiator()));
        dto.setLocation(new Location(model.getLat(), model.getLon()));
        dto.setPaid(model.getPaid());
        dto.setTitle(model.getTitle());
        dto.setViews(model.getViews());
        dto.setState(model.getState());
        dto.setPublishedOn(model.getPublishedOn());
        dto.setParticipantLimit(model.getParticipantLimit());
        dto.setRequestModeration(model.getRequestModeration());
        return dto;
    }

    public static EventShortDto toShortDto(Event model) {
        var dto = new EventShortDto();
        dto.setAnnotation(model.getAnnotation());
        dto.setCategory(CategoryMapper.fromModel(model.getCategory()));
        dto.setEventDate(model.getEventDate());
        dto.setConfirmedRequests(model.getConfirmedRequests());
        dto.setId(model.getId());
        dto.setInitiator(UserMapper.shortDtoFromModel(model.getInitiator()));
        dto.setPaid(model.getPaid());
        dto.setTitle(model.getTitle());
        dto.setViews(model.getViews());
        return dto;
    }

    public static void updateFromRequest(UpdateEventRequest dto, Event model, Category category) {
        if (dto.getPaid() != null) {
            model.setPaid(dto.getPaid());
        }
        if (category != null) {
            model.setCategory(category);
        }
        if (dto.getEventDate() != null) {
            model.setEventDate(dto.getEventDate());
        }
        if (dto.getAnnotation() != null) {
            model.setAnnotation(dto.getAnnotation());
        }
        if (dto.getDescription() != null) {
            model.setDescription(dto.getDescription());
        }
        if (dto.getTitle() != null) {
            model.setTitle(dto.getTitle());
        }
        if (dto.getParticipantLimit() != null) {
            model.setParticipantLimit(dto.getParticipantLimit());
        }
    }

    public static Event updateFromAdminRequest(Event model, AdminUpdateEventRequest dto, Category category) {
        if (dto.getPaid() != null) {
            model.setPaid(dto.getPaid());
        }
        if (category != null) {
            model.setCategory(category);
        }
        if (dto.getEventDate() != null) {
            model.setEventDate(dto.getEventDate());
        }
        if (dto.getAnnotation() != null) {
            model.setAnnotation(dto.getAnnotation());
        }
        if (dto.getDescription() != null) {
            model.setDescription(dto.getDescription());
        }
        if (dto.getTitle() != null) {
            model.setTitle(dto.getTitle());
        }
        if (dto.getParticipantLimit() != null) {
            model.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getLocation() != null) {
            model.setLon(dto.getLocation().getLon());
            model.setLat(dto.getLocation().getLat());
        }
        if (dto.getRequestModeration() != null) {
            model.setRequestModeration(dto.getRequestModeration());
        }
        return model;
    }
}
