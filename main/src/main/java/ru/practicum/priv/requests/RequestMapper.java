package ru.practicum.priv.requests;

import ru.practicum.admin.categories.CategoryMapper;
import ru.practicum.admin.categories.model.Category;
import ru.practicum.admin.users.UserMapper;
import ru.practicum.admin.users.model.User;
import ru.practicum.priv.events.dto.UpdateEventRequest;
import ru.practicum.priv.requests.dto.ParticipationRequestDto;
import ru.practicum.priv.requests.model.Request;

//@AllArgsConstructor
public class RequestMapper {
//    private final CategoryService categoryService;
//    public static Request createFromDto(NewEventDto dto, User initiator, Category category) {
//        var model = new Request();
//        model.setEventDate(dto.getEventDate());
//        model.setAnnotation(dto.getAnnotation());
//        model.setCategory(category);
//        model.setDescription(dto.getDescription());
//        model.setEventDate(dto.getEventDate());
//        model.setInitiator(initiator);
////        model.setViews();
//        model.setPaid(dto.getPaid());
//        model.setLat(dto.getLocation().getLat());
//        model.setLon(dto.getLocation().getLon());
//        model.setParticipantLimit(dto.getParticipantLimit());
//        model.setTitle(dto.getTitle());
//        model.setRequestModeration(dto.getRequestModeration());
////        model.setConfirmedRequests(dto);
//        return model;
//    }

//        public static Event updateFromDto( dto, User model) {
//        if(dto.getName() != null) {
//            model.setName(dto.getName());
//        }
//            if(dto.getName() != null) {
//                model.setEmail(dto.getEmail());
//            }
//            return model;
//        }

    public static ParticipationRequestDto fromModel(Request model) {
        var dto = new ParticipationRequestDto();
        dto.setRequester(model.getRequester().getId());
        dto.setCreated(model.getCreated());
        dto.setEventId(model.getEvent().getId());
        dto.setStatus(model.getStatus());
        dto.setId(model.getId());
        return dto;
    }
}
