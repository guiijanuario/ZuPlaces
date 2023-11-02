package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetGeolocationDto {
    private String ip_address;
    private String city;
    private int city_geoname_id;
    private String region;
    private String region_iso_code;
    private int region_geoname_id;
    private String postal_code;
    private String country;
    private String country_code;
    private int country_geoname_id;
    private boolean country_is_eu;
    private String continent;
    private String continent_code;
    private int continent_geoname_id;
    private double longitude;
    private double latitude;
    private Security security;
    private Timezone timezone;
    private Flag flag;
    private Currency currency;
    private Connection connection;

    public String getIp_address() {
        return ip_address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public static class Security {
        private boolean is_vpn;

    }

    public static class Timezone {
        private String name;
        private String abbreviation;
        private int gmt_offset;
        private String current_time;
        private boolean is_dst;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class Flag {
        private String emoji;
        private String unicode;
        private String png;
        private String svg;

    }

    public static class Currency {
        private String currency_name;
        private String currency_code;

    }

    public static class Connection {
        private int autonomous_system_number;
        private String autonomous_system_organization;
        private String connection_type;
        private String isp_name;
        private String organization_name;

    }
}
