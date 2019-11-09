import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.LinkedList;

public class Main extends Application {

    boolean xForward, xBackward, yForward, yBackward;

    @Override
    public void start(Stage stage) {


        double deltaZ = 100;

        Point3D pointA = new Point3D(548.45f,585.96,0f + deltaZ);
        Point3D pointB = new Point3D(126.43f,768.27f,0f+ deltaZ);
        Point3D pointC = new Point3D(90.57f,626.68f,403.16f+ deltaZ);
        Point3D pointD = new Point3D(482.6f,444.36f,403.16f+ deltaZ);
        Point3D pointE = new Point3D(718.46f,951.52f,156.16f+ deltaZ);
        Point3D pointF = new Point3D(326.43f,1133.84,156.16f+ deltaZ);
        Point3D pointG = new Point3D(260.58f,992.24f,559.32f+ deltaZ);
        Point3D pointH = new Point3D(652.61f,809.92,559.32f+ deltaZ);

        Group root = new Group();

        final Scene scene = new Scene(root,1920, 1080);

        scene.setFill(null);

        final long startNanoTime = System.nanoTime();



        Cube cube = new Cube(pointA, pointB,pointC,pointD,pointE,pointF,pointG, pointH);

        double scaleFactor = 500;
        Plane cameraPlane = new Plane( new Point3D(0,0-scaleFactor,0), new Vector3D(0,3,0));

        CameraScreen cameraScreen = new CameraScreen(new Point3D(-5,0,0), cameraPlane);

        //Scene Event
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    yBackward = true; break;
                    case DOWN:  yForward = true; break;
                    case RIGHT: xForward  = true; break;

                    case LEFT:  xBackward  = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    yBackward = false; break;
                    case DOWN:  yForward = false; break;
                    case LEFT:  xBackward  = false; break;
                    case RIGHT: xForward  = false; break;
                }
            }
        });


        stage.setTitle("Inverno engine");

        stage.setScene(scene);
        stage.show();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;


                //Cube cube = new Cube(Cube.rotateCubeOnY(cube1, -(float)(Math.PI/t)));


                //Point3D rotatedA = Rotations.rotatePointOnYByThetaAngle(pointA, (float)(0));

                double deltaMoveY = 5;
                double deltaMoveX = 5;

                if(yForward){
                    cameraScreen.setPlaneConstant( cameraScreen.getScreenPlane().getConstant() + deltaMoveY);
                }
                if(yBackward){
                    cameraScreen.setPlaneConstant( cameraScreen.getScreenPlane().getConstant() - deltaMoveY);
                }


                Point3D oldCameraCoordinates = cameraScreen.getCameraCoordinates();
                double newCameraX = xBackward ? oldCameraCoordinates.x - deltaMoveX : oldCameraCoordinates.x;

                double newCameraY = yForward ? oldCameraCoordinates.y + deltaMoveY : oldCameraCoordinates.y;
                newCameraY = yForward ? oldCameraCoordinates.y - deltaMoveY : oldCameraCoordinates.y;

                if(xForward){
                    newCameraX = oldCameraCoordinates.x + deltaMoveX;
                }

                cameraScreen.setCameraCoordinates(newCameraX,newCameraY, oldCameraCoordinates.z );

                LinkedList<Point3D> linkedListProjectedPoints = new LinkedList();

                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointA, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointB, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointC, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointD, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointE, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointF, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointG, cameraScreen,true));
                linkedListProjectedPoints.add(CameraScreen.perspectiveProjectionOntoCameraScreen(cube.pointH, cameraScreen,true));









                Line lineAB = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(0)), To2DProjection(linkedListProjectedPoints.get(1)));
                Line lineCD = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(2)), To2DProjection(linkedListProjectedPoints.get(3)));
                Line lineAD = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(0)), To2DProjection(linkedListProjectedPoints.get(3)));
                Line lineCB = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(2)), To2DProjection(linkedListProjectedPoints.get(1)));
                Line lineGH = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(6)), To2DProjection(linkedListProjectedPoints.get(7)));
                Line lineFE = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(5)), To2DProjection(linkedListProjectedPoints.get(4)));
                Line lineAE = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(0)), To2DProjection(linkedListProjectedPoints.get(4)));
                Line lineBF = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(1)), To2DProjection(linkedListProjectedPoints.get(5)));
                Line lineDH = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(3)), To2DProjection(linkedListProjectedPoints.get(7)));
                Line lineCG = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(2)), To2DProjection(linkedListProjectedPoints.get(6)));
                Line lineHE = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(7)), To2DProjection(linkedListProjectedPoints.get(4)));
                Line lineGF = lineBetweenPoints(To2DProjection(linkedListProjectedPoints.get(6)), To2DProjection(linkedListProjectedPoints.get(5)));




                Group root = new Group(lineAB, lineCD, lineAD, lineCB, lineAE, lineDH, lineCG, lineBF, lineGH, lineFE, lineHE, lineGF  );

                scene.setRoot(root);




            }
        }.start();
    }

    public static Line lineBetweenPoints(Point2D a, Point2D b){

        return new Line(a.x, a.y, b.x, b.y);

    }

    public static Point2D To2DProjection(Point3D a){

        return new Point2D(a.x, a.z);

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Point2D {
    public final double x;
    public final double y;
    Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Point3D {
    public final double x;
    public final double y;
    public final double z;
    Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z=z;
    }


}

class Cube{
    public final Point3D pointA;
    public final Point3D pointB;
    public final Point3D pointC;
    public final Point3D pointD;
    public final Point3D pointE;
    public final Point3D pointF;
    public final Point3D pointG;
    public final Point3D pointH;
    public final Point3D pointArray[];


    Cube(Point3D... points) {
        this.pointArray = points;
        this.pointA = points[0];
        this.pointB = points[1];
        this.pointC = points[2];
        this.pointD = points[3];
        this.pointE = points[4];
        this.pointF = points[5];
        this.pointG = points[6];
        this.pointH = points[7];
        //Set other points based on this points.
    }

    public static Point3D[] rotateCubeOnZ(Cube cube, float theta){

        Point3D[] rotatedPoints = new Point3D[8];

        for(int i=0; i<cube.pointArray.length;i++){
            rotatedPoints[i] = Rotations.rotatePointOnZByThetaAngle(cube.pointArray[i], theta);
        }

        return rotatedPoints;
    }

    public static Point3D[] rotateCubeOnX(Cube cube, float theta){

        Point3D[] rotatedPoints = new Point3D[8];

        for(int i=0; i<cube.pointArray.length;i++){
            rotatedPoints[i] = Rotations.rotatePointOnXByThetaAngle(cube.pointArray[i], theta);
        }


        return rotatedPoints;
    }

    public static Point3D[] rotateCubeOnY(Cube cube, float theta){

        Point3D[] rotatedPoints = new Point3D[8];

        for(int i=0; i<cube.pointArray.length;i++){
            rotatedPoints[i] = Rotations.rotatePointOnYByThetaAngle(cube.pointArray[i], theta);
        }

        return rotatedPoints;
    }


}


class Rotations {

    public static Point3D rotatePointOnZByThetaAngle(Point3D inputPoint, float theta) {

        Point3D rotatedPoint;

        double rotatedX = inputPoint.x * Math.cos(theta) + (-Math.sin(theta)) * inputPoint.y;
        double rotatedY = Math.sin(theta) * inputPoint.x + Math.cos(theta) * inputPoint.y;
        double rotatedZ = inputPoint.z;

        return rotatedPoint = new Point3D(rotatedX, rotatedY, rotatedZ);


    }

    public static Point3D rotatePointOnYByThetaAngle(Point3D inputPoint, float theta) {

        Point3D rotatedPoint;

        double rotatedX = Math.cos(theta) * inputPoint.x + inputPoint.y * Math.sin(theta);

        double rotatedY = inputPoint.y;
        double rotatedZ = -Math.sin(theta) * inputPoint.x + Math.cos(theta) * inputPoint.z;

        return rotatedPoint = new Point3D(rotatedX, rotatedY, rotatedZ);


    }

    public static Point3D rotatePointOnXByThetaAngle(Point3D inputPoint, float theta) {

        Point3D rotatedPoint;

        double rotatedX = inputPoint.x;
        double rotatedY = -Math.sin(theta) * inputPoint.z + Math.cos(theta) * inputPoint.y;
        double rotatedZ = Math.sin(theta) * inputPoint.y + Math.cos(theta) * inputPoint.z;

        return rotatedPoint = new Point3D(rotatedX, rotatedY, rotatedZ);


    }

}
