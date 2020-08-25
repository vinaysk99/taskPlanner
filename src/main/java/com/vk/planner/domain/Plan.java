package com.vk.planner.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vk.planner.serialization.JsonJodaDateTimeDeserializer;
import com.vk.planner.serialization.JsonJodaDateTimeSerializer;
import lombok.*;
import org.joda.time.DateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan {

    private Long id;

    private String type;

    private String description;

    private String location;

    private String organiserEmailId;

    private String organiserPhoneNumber;

    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaDateTimeDeserializer.class)
    private DateTime dateTime;

    private String status;
}
