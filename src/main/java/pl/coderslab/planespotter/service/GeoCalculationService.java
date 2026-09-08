package pl.coderslab.planespotter.service;


import org.springframework.stereotype.Service;

@Service
public class GeoCalculationService {

    public double calculateBearing(double userLa, double userLo, double planeLa, double planeLo){

        double lo1 = Math.toRadians(userLo);
        double lo2 = Math.toRadians(planeLo);

        double la1 = Math.toRadians(userLa);
        double la2 = Math.toRadians(planeLa);

        double loDiff = lo2 - lo1;

        double bearing = Math.atan2(Math.sin(loDiff) * Math.cos(la2),
                Math.cos(la1) * Math.sin(la2) - Math.sin(la1) * Math.cos(la2) * Math.cos(loDiff));

        return (Math.toDegrees(bearing) + 360) % 360;
    }

    public double calculateAngularDiff(double firstB, double secondB){

        double diff = Math.abs(firstB - secondB);

        return Math.min(diff, 360 - diff);
    }
}
