package guru.springframework.sdjpainheritence.domain.joined;

import jakarta.persistence.Entity;

@Entity
public class Guitar extends Instrument {

    private Integer numberOfString;

    public Integer getNumberOfString() {
        return numberOfString;
    }

    public void setNumberOfString(Integer numberOfString) {
        this.numberOfString = numberOfString;
    }
}
