/**
  A class supporting pairs of affine transformations
  (a matrix and its inverse) for 3D graphics - 4 x 4 matrices.

  @version 1 June 2002
  @author Bob Crawford

*/

public class MatrixPair extends Matrix
{
  Matrix Inv;

  /**
    Initializes a new matrix pair to the identity.
  */
  MatrixPair()
  {
    super();
    Inv = new Matrix();
  }

  // Translation stuff

  /**
    Translates the current matrix by tx units in the x-direction,
    ty units in the y-direction, and tz units in the z-direction.
    Equivalent to left multiplying by a translation matrix.
    Keeps the inverse in sync.

    @param tx Amount to translate in the x-direction
    @param ty Amount to translate in the y-direction
    @param tz Amount to translate in the z-direction
  */
  public void translate(double tx, double ty, double tz)
  {
    super.translate(tx, ty, tz);
    Inv.translate(-tx, -ty, -tz);
  }

  // Scaling stuff

  /**
    Scales the current matrix by sx units in the x-direction,
    sy units in the y-direction, and sz units in the z-direction.
    Equivalent to left multiplying by a scale matrix.
    Keeps the inverse in sync.

    @param sx Amount to scale in the x-direction
    @param sy Amount to scale in the y-direction
    @param sz Amount to scale in the z-direction
  */
  public void scale(double sx, double sy, double sz)
  {
    super.scale(sx, sy, sz);
    Inv.scale(1 / sx, 1 / sy, 1 / sz);
  }

  // Rotation stuff

  /**
    Rotates the current matrix about the line from the
    origin through (x, y, z). The rotation is through an angle
    whose sine and cosine are given. A positive rotation is
    counterclockwise when viewed from the point (x, y, z)
    back toward the origin.
    Keeps the inverse in sync.

    @param s The sine of the rotation angle
    @param c The cosine of the rotation angle
    @param x, y, z Coordinates of point determining the rotation axis
  */
  public void rotateSC(double s, double c, double x, double y, double z)
  {
    super.rotateSC(s, c, x, y, z);
    Inv.rotateSC(-s, c, x, y, z);
  }

  /**
    Rotates the current matrix about the line from the
    origin through (x, y, z). The rotation is through the angle
    theta (in radians). A positive rotation is counterclockwise
    when viewed from the point (x, y, z) back toward the origin.
    Keeps the inverse in sync.

    @param theta The rotation angle (in radians)
    @param x, y, z Coordinates of point determining the rotation axis
  */
  public void rotate(double theta, double x, double y, double z)
  {
    rotateSC(Math.sin(theta), Math.cos(theta), x, y, z);
  }

  /**
    Rotates the current matrix about the line from the
    origin through (x, y, z). The rotation is through the angle
    deg (in degrees). A positive rotation is counterclockwise
    when viewed from the point (x, y, z) back toward the origin.
    Keeps the inverse in sync.

    @param deg The rotation angle (in degrees)
    @param x, y, z Coordinates of point determining the rotation axis
  */
  public void rotateDeg(double deg, double x, double y, double z)
  {
    rotate(Math.toRadians(deg), x, y, z);
  }

  // Reflection stuff

  /**
    Reflects the current matrix about the XY-plane.
    Keeps the inverse in sync.

    @return The reflection matrix
  */
  public void reflectXY()
  {
    super.reflectXY();
    Inv.reflectXY();
  }

  /**
    Reflects the current matrix in the XZ-plane.
    Keeps the inverse in sync.

    @return The reflection matrix
  */
  public void reflectXZ()
  {
    super.reflectXZ();
    Inv.reflectXZ();
  }

  /**
    Reflects the current matrix in the YZ-plane.
    Keeps the inverse in sync.

    @return The reflection matrix
  */
  public void reflectYZ()
  {
    super.reflectYZ();
    Inv.reflectYZ();
  }

  /**
    Reflects the current matrix about the origin.
    Keeps the inverse in sync.

    @return The reflection matrix
  */
  public void reflectOrigin()
  {
    super.reflectOrigin();
    Inv.reflectOrigin();
  }

  // Shear stuff

  /**
    Shears the current matrix by a factor of a in the y-direction and
    b in the z-direction (leaving x coordinates unchanged). This maps
    the line

                           y = - a * x
                           z = - b * x

    on to the x-axis in such a way that x coordinates are preserved.
    Keeps the inverse in sync.

    @param a Shear factor for the y-direction
    @param b Shear factor for the z-direction
  */
  public void shearX(double a, double b)
  {
    super.shearX(a, b);
    Inv.shearX(-a, -b);
  }

  /**
    Shears the current matrix by a factor of a in the x-direction and
    b in the z-direction (leaving y coordinates unchanged). This maps
    the line

                           x = - a * y
                           z = - b * y

    on to the y-axis in such a way that y coordinates are preserved.
    Keeps the inverse in sync.

    @param a Shear factor for the x-direction
    @param b Shear factor for the z-direction
  */
  public void shearY(double a, double b)
  {
    super.shearY(a, b);
    Inv.shearY(-a, -b);
  }

  /**
    Shears the current matrix by a factor of a in the x-direction and
    b in the y-direction (leaving z coordinates unchanged). This maps
    the line

                           x = - a * z
                           y = - b * z

    on to the z-axis in such a way that z coordinates are preserved.
    Keeps the inverse in sync.

    @param a Shear factor for the x-direction
    @param b Shear factor for the y-direction
  */
  public void shearZ(double a, double b)
  {
    super.shearZ(a, b);
    Inv.shearZ(-a, -b);
  }

  // Matrix multiplication

  /**
    Returns the product of the two input matrices,
    in the order given. Keeps the inverses in sync
    by multiplying them in the reverse order.

    @param m1, m2 The matrix pairs to multiply
    @return The product of the matrix pairs
  */
  public static MatrixPair multiply(MatrixPair m1, MatrixPair m2)
  {
    MatrixPair m = new MatrixPair();

    Matrix temp = Matrix.multiply(m1, m2);
    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
        m.body[i][j] = temp.body[i][j];
    m.Inv = Matrix.multiply(m2.Inv, m1.Inv);

    return m;
  }

  // Vector images

  /**
    Returns the result of applying the inverse of the
    current matrix to the given vector.

    @param v The vector whose image is desired
    @return The inverse image of the vector under the current matrix
  */
  public Vector3 inverseImage(Vector3 v)
  {
    return Inv.image(v);
  }

  /**
    Transforms the given normal vector via the current matrix. This
    uses the transpose of the inverse of the current matrix.

    @param v A normal vector to be transformed
    @return The transformed normal
  */
  public Vector3 transformNormal(Vector3 v)
  {
    Vector3 result = new Vector3();

    for ( int i = 0; i < 3; i++ )
    {
      result.body[i] = 0;
      for ( int j = 0; j < 3; j++ )
        result.body[i] += Inv.body[j][i] * v.body[j];
    }
    return result.makeNormalized();
  }

  // Output stuff

  /**
    Writes the current matrix pair out to the console.
    Primarily for debugging.
  */
  public void dump()
  {
    super.dump();
    System.out.println("Inverse:");
    Inv.dump();
  }


}
