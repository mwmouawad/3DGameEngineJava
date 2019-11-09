import java.awt.*;

public class Test {

    public static void main(String args[]){


        //Scalar Proudct test
        boolean scalarProductTest = scalarProductTest();

        assert(scalarProductTest == true);
        assert(planeConstructorTest() == true);
        assert(planeConstructorTest2() == true);
        assert(testIntersectionLineWithPlane() == true);
        assert(testIntersectionWithCameraScreen() == true);




    }


    public static boolean scalarProductTest(){

        System.out.println("Running scalar product test.");
        double expectedResult = 0;

        //Testing two orthogonal vectors
        Vector3D vector1 =  new Vector3D(1,1,1);
        Vector3D vector2 =  new Vector3D(-1,-2,3);

        double result = Vector3D.scalarProduct(vector1, vector2);

        System.out.println("Expected: " + expectedResult + " Got: " + result);

        if(result == (double)0){
            return true;
        }

        return false;

    }

    public static boolean planeConstructorTest(){
        //Testing non orthogonal vectors
        Vector3D vector1 =  new Vector3D(3,1,1);
        Vector3D vector2 =  new Vector3D(-1,-2,3);

        try{
            Plane plane = new Plane(new Point3D(0,0,0), vector1, vector2);
        }
        catch (Error error){
            //Test worked returns true.
            System.out.println("Test plane passed");
            return true;
        }


        return false;
    }

    public static boolean planeConstructorTest2(){

        //Testing non orthogonal vectors
        Vector3D vectorOrthogonal =  new Vector3D(-4,4,-3);
        Point3D pointOrigin = new Point3D(0, 2, 4);

        Plane plane = new Plane(pointOrigin, vectorOrthogonal);
        double result = plane.getConstant();
        double expected = -4;

        System.out.println("Expected: " + expected + " Got: " + result);

        if(result == expected){
            return true;
        }

        return false;
    }

    public static boolean testIntersectionLineWithPlane(){

        Vector3D directorVectorOfLine = new Vector3D(0.12,-0.48, 0.2);
        Point3D P = new Point3D(-0.11,1.18,0);

        Plane plane = new Plane(new Point3D(0,-0.3, 0), new Vector3D(0,1,0));



        Point3D result = Plane.intersectionOfLineWithPlane(directorVectorOfLine, P, plane);

        Point3D expected = new Point3D(0.25f,-0.3f,0.61f);

        System.out.println("Expected : " + expected.x + " " + expected.y + " " + expected.z);
        System.out.println("Got : " + result.x + " " + result.y + " " + result.z);



        if(        Math.abs(expected.x-result.x) <= 0.02
                && Math.abs(expected.y-result.y) <= 0.02
                && Math.abs(expected.z-result.z) <= 0.02){

            System.out.println("Got result");
            return true;
        }
        return false;


    }

    public static boolean testIntersectionWithCameraScreen(){

        Point3D expected = new Point3D(0.25f, -0.3f, 0.61f);

        Point3D P = new Point3D(-0.11,1.18,0);

        Plane plane = new Plane(new Point3D(0f,-0.3f, 0f), new Vector3D(0f,1f,0f));
        Point3D cameraPoint = new Point3D(0.01f, 0.7f,0.2f);

        CameraScreen cameraScreen = new CameraScreen(cameraPoint, plane);

        Point3D result = CameraScreen.perspectiveProjectionOntoCameraScreen(P, cameraScreen, false);


        System.out.println("Expected : " + expected.x + " " + expected.y + " " + expected.z);
        System.out.println("Got : " + result.x + " " + result.y + " " + result.z);



        if(        Math.abs(expected.x-result.x) <= 0.01
                && Math.abs(expected.y-result.y) <= 0.01
                && Math.abs(expected.z-result.z) <= 0.01){

            System.out.println("Got result");
            return true;
        }
        return false;


    }
}
