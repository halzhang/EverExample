package com.halzhang.android.example.rxexample;

import java.util.List;

/**
 * Created by Hal on 15/4/26.
 */
public class WeatherData {


    public Coordinates coord;
    public Local sys;
    public List<Weather> weathers;
    public String base;
    public Main main;
    public Wind wind;
    public Rain rain;
    public Cloud clouds;
    public long id;
    public long dt;
    public String name;
    public int cod;

    public static class Coordinates {
        public double lat;
        public double lon;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Coordinates{");
            sb.append("lat=").append(lat);
            sb.append(", lon=").append(lon);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Local {
        public String country;
        public long sunrise;
        public long sunset;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Local{");
            sb.append("country='").append(country).append('\'');
            sb.append(", sunrise=").append(sunrise);
            sb.append(", sunset=").append(sunset);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Weather{");
            sb.append("id=").append(id);
            sb.append(", main='").append(main).append('\'');
            sb.append(", description='").append(description).append('\'');
            sb.append(", icon='").append(icon).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Main {
        public double temp;
        public double pressure;
        public double humidity;
        public double temp_min;
        public double temp_max;
        public double sea_level;
        public double grnd_level;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Main{");
            sb.append("temp=").append(temp);
            sb.append(", pressure=").append(pressure);
            sb.append(", humidity=").append(humidity);
            sb.append(", temp_min=").append(temp_min);
            sb.append(", temp_max=").append(temp_max);
            sb.append(", sea_level=").append(sea_level);
            sb.append(", grnd_level=").append(grnd_level);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Wind {
        public double speed;
        public double deg;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Wind{");
            sb.append("speed=").append(speed);
            sb.append(", deg=").append(deg);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Rain {
        public int threehourforecast;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Rain{");
            sb.append("threehourforecast=").append(threehourforecast);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Cloud {
        public int all;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Cloud{");
            sb.append("all=").append(all);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherData{");
        sb.append("coord=").append(coord);
        sb.append(", sys=").append(sys);
        sb.append(", weathers=").append(weathers);
        sb.append(", base='").append(base).append('\'');
        sb.append(", main=").append(main);
        sb.append(", wind=").append(wind);
        sb.append(", rain=").append(rain);
        sb.append(", clouds=").append(clouds);
        sb.append(", id=").append(id);
        sb.append(", dt=").append(dt);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cod=").append(cod);
        sb.append('}');
        return sb.toString();
    }
}
