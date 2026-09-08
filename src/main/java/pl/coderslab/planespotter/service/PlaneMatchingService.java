package pl.coderslab.planespotter.service;


import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.response.OpenSkyPlaneResponse;
import pl.coderslab.planespotter.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class PlaneMatchingService {

    private final OpenSkyService service;
    private final GeoCalculationService geoService;

    public PlaneMatchingService(OpenSkyService service, GeoCalculationService geoService) {
        this.service = service;
        this.geoService = geoService;
    }

    public OpenSkyPlaneResponse findPlane(double la, double lo, double viewingBearing){
        List<OpenSkyPlaneResponse> planes  = service.getPlanes(la, lo);

        double diff1 = 360;

        OpenSkyPlaneResponse planeResponse = null;

        for (OpenSkyPlaneResponse plane : planes){
            double planeLo = plane.getLongitude();
            double planeLa = plane.getLatitude();

            double planeBearing = geoService.calculateBearing(la, lo, planeLa, planeLo);

            double diff2 = geoService.calculateAngularDiff(viewingBearing, planeBearing);

            if (diff2 < diff1){
                diff1 = diff2;
                planeResponse = plane;
            }
        }

        if (planeResponse == null || diff1 > 20 ){
            throw new ResourceNotFoundException("No plane found in this area and direction.");
        }

        return planeResponse;



    }
}
