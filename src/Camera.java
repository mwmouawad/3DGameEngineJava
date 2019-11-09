import java.awt.*;
import java.security.cert.PolicyNode;

public class Camera {

    private Point3D cameraCoordinates;

    public Camera(Point3D cameraCoordinates){

        this.cameraCoordinates = cameraCoordinates;


    }

    /**
     * Assuming the camera is at (0,0,0) and z goes into the screen.
     * And canvas is at distance 1 from camera.
     * @param inputPoint
     */
    public static Point2D projectIntoCameraView(Point3D inputPoint){

        double projPointx = inputPoint.x / inputPoint.z;
        double projPointy = inputPoint.y / inputPoint.z;

        return new Point2D(projPointx*15 , projPointy*15);

    }

    public Point3D getCameraCoordinates() {
        return cameraCoordinates;
    }

    public void translate(double x, double y, double z){

        Point3D oldCameraCoordinates = this.cameraCoordinates;

        Point3D newCameraCoordinates = new Point3D(
                oldCameraCoordinates.x + x,
                oldCameraCoordinates.y + y,
                oldCameraCoordinates.z + z );

        this.cameraCoordinates = newCameraCoordinates;

        return;

    }

    public void setCameraCoordinates(double x, double y, double z){


        Point3D newCameraCoordinates = new Point3D(
                 x,
                 y,
                 z );

        this.cameraCoordinates = newCameraCoordinates;

        return;

    }
}


class CameraScreen extends Camera{

    Plane screenPlane;

    public CameraScreen(Point3D cameraCoordinates, Plane plane){

        super(cameraCoordinates);
        this.screenPlane = plane;

    }

    /**
     * Returns the projection of P consisting of the intersection of the line passing by P and CameraPoint
     * with the Camera Plane.
     * @param P Using real world coordinates.
     * @param cameraScreen
     * @return
     */
    public static Point3D perspectiveProjectionOntoCameraScreen(Point3D P, CameraScreen cameraScreen, boolean reverse){

        System.out.println("Perspective projection input: " + P.x + " " + P.y + " " + P.z);

        Point3D cameraPoint = cameraScreen.getCameraCoordinates();
        Plane screenPlane = cameraScreen.getScreenPlane();

        Vector3D directionVectorOfLine = Vector3D.getDirectorVectorBetweenTwoPoints(P, cameraPoint);
        Point3D intersectionPoint = Plane.intersectionOfLineWithPlane(directionVectorOfLine, P, screenPlane );

        if(reverse){
            intersectionPoint = new Point3D(-intersectionPoint.x , -intersectionPoint.y, -intersectionPoint.z);
        }

        System.out.println("Perspective projection output: " + intersectionPoint.x + " " + intersectionPoint.y + " " + intersectionPoint.z);

        return intersectionPoint;

    }

    public void setPlaneConstant(double newConstant){
        this.screenPlane.setConstant(newConstant);
    }


    /**
     * Translate the group composed of the camera and plane together.
     */
    public void translate(double x, double y, double z){



    }

    public Plane getScreenPlane() {
        return screenPlane;
    }
}