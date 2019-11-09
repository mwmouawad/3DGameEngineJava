public class Vector3D{

    double x;
    double y;
    double z;

    public Vector3D(double x, double y, double z){

        this.x = x;
        this.y = y;
        this.z = z;

    }

    static public double scalarProduct(Vector3D vectorA, Vector3D vectorB){

        double scalarProduct = vectorA.x * vectorB.x + vectorA.y * vectorB.y + vectorA.z * vectorB.z;

        return scalarProduct;


    }

    static public Vector3D getDirectorVectorBetweenTwoPoints(Point3D a, Point3D b){

        Vector3D directorVector = new Vector3D(
                (a.x - b.x),
                (a.y - b.y),
                (a.z - b.z)
        );

        return directorVector;

    }

}