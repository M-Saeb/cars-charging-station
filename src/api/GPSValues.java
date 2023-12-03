package api;

import annotations.Readonly;
import annotations.Mutable;

public class GPSValues {
    private float latitude;
    private float longitude;

    public GPSValues(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Readonly
    public float getLatitude() {
        return latitude;
    }

    @Readonly
    public float getLongitude() {
        return longitude;
    }

    @Mutable
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Mutable
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
