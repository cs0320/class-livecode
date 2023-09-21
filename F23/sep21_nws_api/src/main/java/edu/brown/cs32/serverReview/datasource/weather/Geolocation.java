package edu.brown.cs32.serverReview.datasource.weather;

public record Geolocation(double lat, double lon) {
    public String toOurServerParams() {
        return "lat="+lat+"&lon="+lon;
    }
}
