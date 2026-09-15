package pl.coderslab.planespotter.service;


import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.response.IdentifiedPlaneResponse;
import java.util.List;

@Service
public class PlaneMatchingService {

    private final OpenSkyService service;
    private final GeoCalculationService geoService;

    public PlaneMatchingService(OpenSkyService service, GeoCalculationService geoService) {
        this.service = service;
        this.geoService = geoService;
    }

    public IdentifiedPlaneResponse findPlane(double la, double lo, double viewingBearing) {
        List<IdentifiedPlaneResponse> planes = service.getPlanes(la, lo);

        double diff1 = 360;

        IdentifiedPlaneResponse planeResponse = null;

        for (IdentifiedPlaneResponse plane : planes) {
            double planeLo = plane.getLongitude();
            double planeLa = plane.getLatitude();

            // planeBearing is the real angle from the viewer to the plane
            double planeBearing = geoService.calculateBearing(la, lo, planeLa, planeLo);

            double diff2 = geoService.calculateAngularDiff(viewingBearing, planeBearing);

            if (diff2 < diff1) {
                diff1 = diff2;
                planeResponse = plane;
            }
        }

        if (planeResponse == null) {
            return null;
        }

        return planeResponse;


    }
}
