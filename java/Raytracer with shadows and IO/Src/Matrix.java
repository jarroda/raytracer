/**
  A class supporting affine transformations for 3D graphics -
  4 x 4 matrices.

  @version 2 September 2006
  @author Bob Crawford

*/

public class Matrix
{
  double[][] body;

  /**
    Initializes a new matrix to the identity.
  */
  Matrix()
  {
    body = new double[4][4];
    for ( int i = 0; i < 4; i++ )
      body[i][i] = 1;
  }

  /**
    Initializes a new matrix equal to m.
	 Essentially a clone.

    @param m The matrix to be copied.
  */
  Matrix(Matrix m)
  {
    body = new double[4][4];
    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
        body[i][j] = m.body[i][j];
  }

  // Translation stuff

  /**
    Translates the current matrix by tx units in the x-direction,
    ty units in the y-direction, and tz units in the z-direction.
    Equivalent to right multiplying by a translation matrix.

    @param tx Amount to translate in the x-direction
    @param ty Amount to translate in the y-direction
    @param tz Amount to translate in the z-direction
  */
  public void translate(double tx, double ty, double tz)
  {
    Matrix m = translate(this, tx, ty, tz);
	 this.body = m.body;
  }

  /**
    Returns the translation matrix for translating by tx units in the
    x-direction, ty units in the y-direction, and tz units in the
    z-direction.

    @param tx Amount to translate in the x-direction
    @param ty Amount to translate in the y-direction
    @param tz Amount to translate in the z-direction
    @return The translation matrix
  */
  public static Matrix translationMatrix(double tx, double ty, double tz)
  {
    Matrix m = new Matrix();

    m.body[0][3] = tx;
    m.body[1][3] = ty;
    m.body[2][3] = tz;

    return m;
  }

  /**
    Returns the input matrix translated by tx units in the
    x-direction, ty units in the y-direction, and tz units in the
    z-direction.

    @param m The matrix to be translated
    @param tx Amount to translate in the x-direction
    @param ty Amount to translate in the y-direction
    @param tz Amount to translate in the z-direction
    @return The translated matrix
  */
  public static Matrix translate(Matrix m, double tx, double ty, double tz)
  {
    Matrix trans = translationMatrix(tx, ty, tz);
    return multiply(m, trans);
  }

  // Scaling stuff

  /**
    Scales the current matrix by sx units in the x-direction,
    sy units in the y-direction, and sz units in the z-direction.
    Equivalent to right multiplying by a scale matrix.

    @param sx Amount to scale in the x-direction
    @param sy Amount to scale in the y-direction
    @param sz Amount to scale in the z-direction
  */
  public void scale(double sx, double sy, double sz)
  {
    Matrix m = scale(this, sx, sy, sz);
	 this.body = m.body;
  }

  /**
    Returns the scale matrix for scaling by sx units in the
    x-direction, sy units in the y-direction, and sz units in the
    z-direction.

    @param sx Amount to scale in the x-direction
    @param sy Amount to scale in the y-direction
    @param sz Amount to scale in the z-direction
    @return The scale matrix
  */
  public static Matrix scaleMatrix(double sx, double sy, double sz)
  {
    Matrix m = new Matrix();

    m.body[0][0] = sx;
    m.body[1][1] = sy;
    m.body[2][2] = sz;

    return m;
  }

  /**
    Returns the input matrix scaled by sx units in the x-direction,
    sy units in the y-direction, and sz units in the z-direction.

    @param m Matrix to be scaled
    @param sx Amount to scale in the x-direction
    @param sy Amount to scale in the y-direction
    @param sz Amount to scale in the z-direction
    @return The scaled matrix
  */
  public static Matrix scale(Matrix m, double sx, double sy, double sz)
  {
    Matrix scl = scaleMatrix(sx, sy, sz);
    return multiply(m, scl);
  }

  // Rotation stuff ***** RRC

  /**
    Returns a rotation matrix for rotating about the line from the
    origin through (x, y, z). The rotation is through an angle
    whose sine and cosine are input. A positive rotation is
    counterclockwise when viewed from the point (x, y, z) back
    toward the origin.

    @param s The sine of the rotation angle
    @param c The cosine of the rotation angle
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotation matrix
  */
  public static Matrix rotationMatrixSC(double s, double c,
                                        double x, double y, double z)
  {
    Matrix m = new Matrix();
    double oneMinusC = 1 - c;
	 
	 Vector3 v = new Vector3(x, y, z);
	 v = Vector3Util.normalize(v);

    m.body[0][0] = v.body[0] * v.body[0] * oneMinusC + c;
    m.body[0][1] = v.body[0] * v.body[1] * oneMinusC - v.body[2] * s;
    m.body[0][2] = v.body[0] * v.body[2] * oneMinusC + v.body[1] * s;
    m.body[1][0] = v.body[1] * v.body[0] * oneMinusC + v.body[2] * s;
    m.body[1][1] = v.body[1] * v.body[1] * oneMinusC + c;
    m.body[1][2] = v.body[1] * v.body[2] * oneMinusC - v.body[0] * s;
    m.body[2][0] = v.body[0] * v.body[2] * oneMinusC - v.body[1] * s;
    m.body[2][1] = v.body[1] * v.body[2] * oneMinusC + v.body[0] * s;
    m.body[2][2] = v.body[2] * v.body[2] * oneMinusC + c;

    return m;
  }

  /**
    Returns a rotation matrix for rotating about the line from the
    origin through (x, y, z). The rotation is through the angle
    theta (in radians). A positive rotation is counterclockwise when
    viewed from the point (x, y, z) back toward the origin.

    @param theta The rotation angle (in radians)
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotation matrix
  */
  public static Matrix rotationMatrix(double theta, double x, double y, double z)
  {
    double s = Math.sin(theta);
    double c = Math.cos(theta);

    return rotationMatrixSC(s, c, x, y, z);
  }

  /**
    Returns a rotation matrix for rotating about the line from the
    origin through (x, y, z). The rotation is through the angle
    deg (in degrees). A positive rotation is counterclockwise when
    viewed from the point (x, y, z) back toward the origin.

    @param deg The rotation angle (in degrees)
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotation matrix
  */
  public static Matrix rotationMatrixDeg(double deg, double x, double y, double z)
  {
    return rotationMatrix(Math.toRadians(deg), x, y, z);
  }

  /**
    Rotates the current matrix about the line from the
    origin through (x, y, z). The rotation is through an angle
    whose sine and cosine are given. A positive rotation is
    counterclockwise when viewed from the point (x, y, z)
    back toward the origin.

    @param s The sine of the rotation angle
    @param c The cosine of the rotation angle
    @param x, y, z Coordinates of point determining the rotation axis
  */
  public void rotateSC(double s, double c, double x, double y, double z)
  {
    Matrix rot = rotationMatrixSC(s, c, x, y, z);
    Matrix result = multiply(this, rot);
	 this.body = result.body;
  }

  /**
    Rotates the current matrix about the line from the
    origin through (x, y, z). The rotation is through the angle
    theta (in radians). A positive rotation is counterclockwise
    when viewed from the point (x, y, z) back toward the origin.

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

    @param deg The rotation angle (in degrees)
    @param x, y, z Coordinates of point determining the rotation axis
  */
  public void rotateDeg(double deg, double x, double y, double z)
  {
    rotate(Math.toRadians(deg), x, y, z);
  }


  /**
    Returns the input matrix rotated about the line from the
    origin through (x, y, z). The rotation is through an angle
    whose sine and cosine are given. A positive rotation is
    counterclockwise when viewed from the point (x, y, z)
    back toward the origin.

    @param m Matrix to be rotated
    @param s The sine of the rotation angle
    @param c The cosine of the rotation angle
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotated matrix
  */
  public static Matrix rotateSC(Matrix m, double s, double c,
                                double x, double y, double z)
  {
    Matrix rot = rotationMatrixSC(s, c, x, y, z);
    return multiply(m, rot);
  }

  /**
    Returns the input matrix rotated about the line from the
    origin through (x, y, z). The rotation is through an angle
    theta (in radians). A positive rotation is counterclockwise
    when viewed from the point (x, y, z) back toward the origin.

    @param m Matrix to be rotated
    @param theta The rotation angle (in radians)
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotated matrix
  */
  public static Matrix rotate(Matrix m, double theta, double x, double y, double z)
  {
    Matrix rot = rotationMatrix(theta, x, y, z);
    return multiply(m, rot);
  }

  /**
    Returns the input matrix rotated about the line from the
    origin through (x, y, z). The rotation is through an angle
    deg (in degrees). A positive rotation is counterclockwise
    when viewed from the point (x, y, z) back toward the origin.

    @param m Matrix to be rotated
    @param deg The rotation angle (in degrees)
    @param x, y, z Coordinates of point determining the rotation axis
    @return The rotated matrix
  */
  public static Matrix rotateDeg(Matrix m, double deg, double x, double y, double z)
  {
    return rotate(m, Math.toRadians(deg), x, y, z);
  }

  // Reflection stuff

  /**
    Returns the reflection matrix for reflecting in the XY-plane.

    @return The reflection matrix
  */
  public static Matrix reflectionXYMatrix()
  {
    Matrix ref = new Matrix();
    ref.body[2][2] = -1;
    return ref;
  }

  /**
    Returns the reflection matrix for reflecting in the XZ-plane.

    @return The reflection matrix
  */
  public static Matrix reflectionXZMatrix()
  {
    Matrix ref = new Matrix();
    ref.body[1][1] = -1;
    return ref;
  }

  /**
    Returns the reflection matrix for reflecting in the YZ-plane.

    @return The reflection matrix
  */
  public static Matrix reflectionYZMatrix()
  {
    Matrix ref = new Matrix();
    ref.body[0][0] = -1;
    return ref;
  }

  /**
    Returns the reflection matrix for reflecting about the origin.

    @return The reflection matrix
  */
  public static Matrix reflectionOriginMatrix()
  {
    Matrix ref = new Matrix();
    ref.body[0][0] = -1;
    ref.body[1][1] = -1;
    ref.body[2][2] = -1;
    return ref;
  }

  /**
    Reflects the current matrix about the XY-plane.
  */
  public void reflectXY()
  {
    for ( int i = 0; i < 4; i++ )
      body[2][i] = -body[2][i];
  }

  /**
    Reflects the current matrix about the XZ-plane.
  */
  public void reflectXZ()
  {
    for ( int i = 0; i < 4; i++ )
      body[1][i] = -body[1][i];
  }

  /**
    Reflects the current matrix about the YZ-plane.
  */
  public void reflectYZ()
  {
    for ( int i = 0; i < 4; i++ )
      body[0][i] = -body[0][i];
  }

  /**
    Reflects the current matrix about the origin.
  */
  public void reflectOrigin()
  {
    for ( int i = 0; i < 3; i++ )
      for ( int j = 0; j < 4; j++ )
        body[i][j] = -body[i][j];
  }

  /**
    Returns the given matrix reflected in the XY-plane.

    @param m The matrix to be relected
    @return The reflected matrix
  */
  public static Matrix reflectXY(Matrix m)
  {
    Matrix ref = reflectionXYMatrix();
    return multiply(ref, m);
  }

  /**
    Returns the given matrix reflected in the XZ-plane.

    @param m The matrix to be relected
    @return The reflected matrix
  */
  public static Matrix reflectXZ(Matrix m)
  {
    Matrix ref = reflectionXZMatrix();
    return multiply(ref, m);
  }

  /**
    Returns the given matrix reflected in the YZ-plane.

    @param m The matrix to be relected
    @return The reflected matrix
  */
  public static Matrix reflectYZ(Matrix m)
  {
    Matrix ref = reflectionYZMatrix();
    return multiply(ref, m);
  }

  /**
    Returns the given matrix reflected in the origin.

    @param m The matrix to be relected
    @return The reflected matrix
  */
  public static Matrix reflectOrigin(Matrix m)
  {
    Matrix ref = reflectionOriginMatrix();
    return multiply(ref, m);
  }



  // Shear stuff

  /**
    Returns a shearing matrix which causes a shearing by a factor of a
    in the y-direction and b in the z-direction (and leaves x coordinates
    unchanged). This maps the line

                           y = - a * x
                           z = - b * x

    on to the x-axis in such a way that x coordinates are preserved.

    @param a Shear factor for the y-direction
    @param b Shear factor for the z-direction
    @return The shearing matrix
  */
  public static Matrix shearXMatrix(double a, double b)
  {
    Matrix m = new Matrix();

    m.body[1][0] = a;
    m.body[2][0] = b;

    return m;
  }

  /**
    Returns a shearing matrix which causes a shearing by a factor of a
    in the x-direction and b in the z-direction (and leaves y coordinates
    unchanged). This maps the line

                           x = - a * y
                           z = - b * y

    on to the y-axis in such a way that y coordinates are preserved.

    @param a Shear factor for the x-direction
    @param b Shear factor for the z-direction
    @return The shearing matrix
  */
  public static Matrix shearYMatrix(double a, double b)
  {
    Matrix m = new Matrix();

    m.body[0][1] = a;
    m.body[2][1] = b;

    return m;
  }

  /**
    Returns a shearing matrix which causes a shearing by a factor of a
    in the x-direction and b in the y-direction (and leaves z coordinates
    unchanged). This maps the line

                           x = - a * z
                           y = - b * z

    on to the z-axis in such a way that z coordinates are preserved.

    @param a Shear factor for the x-direction
    @param b Shear factor for the y-direction
    @return The shearing matrix
  */
  public static Matrix shearZMatrix(double a, double b)
  {
    Matrix m = new Matrix();

    m.body[0][2] = a;
    m.body[1][2] = b;

    return m;
  }

  /**
    Shears the current matrix by a factor of a in the y-direction and
    b in the z-direction (leaving x coordinates unchanged). This maps
    the line

                           y = - a * x
                           z = - b * x

    on to the x-axis in such a way that x coordinates are preserved.

    @param a Shear factor for the y-direction
    @param b Shear factor for the z-direction
  */
  public void shearX(double a, double b)
  {
    Matrix sh = shearXMatrix(a, b);
    Matrix result = multiply(sh, this);
    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
        body[i][j] = result.body[i][j];
  }

  /**
    Shears the current matrix by a factor of a in the x-direction and
    b in the z-direction (leaving y coordinates unchanged). This maps
    the line

                           x = - a * y
                           z = - b * y

    on to the y-axis in such a way that y coordinates are preserved.

    @param a Shear factor for the x-direction
    @param b Shear factor for the z-direction
  */
  public void shearY(double a, double b)
  {
    Matrix sh = shearYMatrix(a, b);
    Matrix result = multiply(sh, this);
    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
        body[i][j] = result.body[i][j];
  }

  /**
    Shears the current matrix by a factor of a in the x-direction and
    b in the y-direction (leaving z coordinates unchanged). This maps
    the line

                           x = - a * z
                           y = - b * z

    on to the z-axis in such a way that z coordinates are preserved.

    @param a Shear factor for the x-direction
    @param b Shear factor for the y-direction
  */
  public void shearZ(double a, double b)
  {
    Matrix sh = shearZMatrix(a, b);
    Matrix result = multiply(sh, this);
    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
        body[i][j] = result.body[i][j];
  }

  // Matrix multiplication

  /**
    Returns the product of the two input matrices,
    in the order given.

    @param m1, m2 The matrices to multiply
    @return The product of the matrices
  */
  public static Matrix multiply(Matrix m1, Matrix m2)
  {
    Matrix m = new Matrix();

    for ( int i = 0; i < 4; i++ )
      for ( int j = 0; j < 4; j++ )
      {
        m.body[i][j] = 0;
        for ( int k = 0; k < 4; k++ )
          m.body[i][j] += m1.body[i][k] * m2.body[k][j];
      }
    return m;
  }

  // Vector images

  /**
    Returns the result of applying the given matrix to the given vector.

    @param m The mapping matrix to be used
    @param v The vector whose image is desired
    @return The image of the vector under the matrix
  */
  public static Vector3 image(Matrix m, Vector3 v)
  {
    Vector3 result = new Vector3();

    for ( int i = 0; i < 3; i++ )
    {
      result.body[i] = 0;
      for ( int j = 0; j < 3; j++ )
        result.body[i] += m.body[i][j] * v.body[j];
      result.body[i] += m.body[i][3];
    }

    return result;
  }

  /**
    Returns the result of applying the current matrix to the given vector.

    @param v The vector whose image is desired
    @return The image of the vector under the current matrix
  */
  public Vector3 image(Vector3 v)
  {
    return image(this, v);
  }

  /**
    Returns the result of applying the given matrix to the given direction vector.
    As is appropriate for directions, the translation components are not used.

    @param m The mapping matrix to be used
    @param v The vector whose image is desired
    @return The image of the vector under the matrix
  */
  public static Vector3 directionImage(Matrix m, Vector3 v)
  {
    Vector3 result = new Vector3();

    for ( int i = 0; i < 3; i++ )
    {
      result.body[i] = 0;
      for ( int j = 0; j < 3; j++ )
        result.body[i] += m.body[i][j] * v.body[j];
    }

    return result;
  }

  /**
    Returns the result of applying the current matrix to the given direction vector.

    @param v The vector whose image is desired
    @return The image of the vector under the current matrix
  */
  public Vector3 directionImage(Vector3 v)
  {
    return directionImage(this, v);
  }

  // Output stuff

  /**
    Writes the given matrix out to the console.
    Primarily for debugging.

    @param m The matrix to be output
  */
  public static void dump(Matrix m)
  {
    int[] columnWidth = new int[4];

    for ( int j = 0; j < 4; j++ )
    {
      columnWidth[j] = 0;
      for ( int i = 0; i < 4; i++ )
        columnWidth[i] = Math.max(columnWidth[i], ("" + m.body[i][j]).length());
    }
    System.out.println();
    for ( int i = 0; i < 4; i++ )
    {
      for ( int j = 0; j < 4; j++ )
        System.out.print(Util.padCenter("" + m.body[i][j], ' ', columnWidth[j] + 2));
      System.out.println();
      System.out.println();
    }
  }

  /**
    Writes the current matrix out to the console.
    Primarily for debugging.
  */
  public void dump()
  {
    dump(this);
  }

}

