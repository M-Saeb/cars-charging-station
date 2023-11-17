public class LocationAPI
{
    private float GPSLatitud_f;
    private float GPSLongitud_f;

    public float getGPSLatitud_f()
    {
        if(this.GPSLatitud_f == 0)
        {
            throw new IllegalArgumentException("GPS Location is Invalid...");
        }
        else
        {
            return GPSLatitud_f;
        }
    }

    public float getGPSLongitud_f()
    {
        if(this.GPSLongitud_f == 0)
        {
            throw new IllegalArgumentException("GPS Location is Invalid...");
        }
        else
        {
            return GPSLongitud_f;
        }
    }
}
