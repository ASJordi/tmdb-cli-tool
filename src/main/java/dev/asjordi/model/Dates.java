package dev.asjordi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dates {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maximum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate minimum;

}