import java.awt.*;
import java.util.Vector;

public class Plane {

    private Point3D pointA;
    private Point3D pointB;
    private Point3D pointC;
    private Vector3D vector1;
    private Vector3D vector2;
    private Vector3D orthogonalVector;
    private double constant;

    private Point3D originPoint;





    public Plane(Point3D a, Point3D b, Point3D c){

        this.pointA = a;
        this.pointB = b;
        this.pointC = c;

    }

    /**
     * Create a plan with set origin point and two non colinear vectors.
     * If vector are colinear or non.
     * @param originPoint
     * @param vector1
     * @param vector2
     */
    public Plane(Point3D originPoint, Vector3D vector1, Vector3D vector2){

        //Check if vectors are orthogonal
        double scalarProduct = Vector3D.scalarProduct(vector1, vector2);

        if(scalarProduct != 0){
            System.out.println("Got non orthogonal vectors when creating Plane;");
            throw new Error();
        }


        this.originPoint = originPoint;
        this.vector1 = vector1;
        this.vector2 = vector2;


    }

    /**
     * Create a plan with set origin point and orthogonal vector.
     *
     * @param originPoint
     * @param orthogonalVector
     */
    public Plane(Point3D originPoint, Vector3D orthogonalVector){

        this.originPoint = originPoint;
        this.orthogonalVector = orthogonalVector;

        //Constant in plane equation
        this.constant = (originPoint.x * orthogonalVector.x)
                        + (originPoint.y * orthogonalVector.y)
                        + (originPoint.z * orthogonalVector.z) ;



        System.out.println("Got constanst d: " + constant);

    }



    public void setConstant(double newConstant){
        this.constant = newConstant;
    }



    /**
     * Returns Projection of point onto a plane.
     * OrthogonalVector(a,b,c) Point(x0,y0,z0) and constant d.
     * Using the equation t =  (ax0 + yx0 + cz0 +d) / (aˆ2 + bˆ2 + cˆ2)
     * Then insert d in line equation.
     * @param plane
     */
    public static Point3D projOfPointOnPlane(Point3D P, Plane plane){

        Vector3D orthogonalVector = plane.getOrthogonalVector();
        double constant = plane.getConstant();


        double a = orthogonalVector.x;
        double b = orthogonalVector.y;
        double c = orthogonalVector.z;

        double t = (a * P.x + b * P.y + c * P.z + constant) / ( a*a + b*b + c*c);

        double projx = P.x + a * t;
        double projy = P.y + b * t;
        double projz = P.z + c * t;

        Point3D projPoint = new Point3D(projx, projy, projz);


        return projPoint;
    }

    public static Point3D intersectionOfLineWithPlane(Vector3D directorVector, Point3D P, Plane plane){

        double constant = plane.getConstant();
        Vector3D orthogonalVectorOfPlane = plane.getOrthogonalVector();

        //
        double a = orthogonalVectorOfPlane.x;
        double b = orthogonalVectorOfPlane.y;
        double c = orthogonalVectorOfPlane.z;

        //
        double alpha = directorVector.x;
        double beta = directorVector.y;
        double gamma = directorVector.z;

        double t = ( - (a * P.x) - (b * P.y) - (c * P.z) + constant) / ( alpha*a + beta*b + gamma*c);

        double projx = P.x + alpha * t;
        double projy = P.y + beta * t;
        double projz = P.z + gamma * t;

        Point3D intersectionPoint = new Point3D(projx, projy, projz);

        return intersectionPoint;

    }

    public double getConstant() {
        return constant;
    }

    public Vector3D getOrthogonalVector(){
        return this.orthogonalVector;
    }

    public Point3D getOriginPoint(){
        return this.originPoint;
    }

}





