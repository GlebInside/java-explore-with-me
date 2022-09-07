package ru.practicum.admin.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    //    private EventShortDto events;//тут так же невозможно выбрать тип Dto. В схеме указан просто int без EventShortDto, описание походе на такое же поле в классе Compilation, поэтому я решил, что тут тоже должен быть такой же тип
    private Boolean pinned;
    private String title;
}
