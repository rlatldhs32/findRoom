package sion.bestRoom.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private String code;
    private String name;
    private List<Double> location;

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDTO cityDTO = (CityDTO) o;

        if (code != null ? !code.equals(cityDTO.code) : cityDTO.code != null) return false;
        if (name != null ? !name.equals(cityDTO.name) : cityDTO.name != null) return false;
        return location != null ? location.equals(cityDTO.location) : cityDTO.location == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
