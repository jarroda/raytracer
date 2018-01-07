/**
  A class supporting viewing matrices.

  @version 1 June 2002
  @author Bob Crawford

*/

public class Viewing
{
  /**
    Returns a matrix which will convert from world coordinates
    (a right-handed system) to view coordinates (a left-
    handed system).

    @param eye* The world coordinates of the eye - the
                origin of the view coordinate system
    @param center* The world coordinates of the point
                   the camera is looking at or the eye
                   is pointed toward
    @param up* A vector giving an indication of the
               desired vertical direction - where the
               top of the camera is pointing
    @return The transformation matrix
  */
  public static Matrix viewingTransform(
                         double eyeX, double eyeY, double eyeZ,
                         double centerX, double centerY, double centerZ,
                         double upX, double upY, double upZ)
  {
    return viewingTransform(
             new Vector3(eyeX, eyeY, eyeZ),
             new Vector3(centerX, centerY, centerZ),
             new Vector3(upX, upY, upZ));
  }


  /**
    Returns a matrix which will convert from world coordinates
    (a right-handed system) to view coordinates (a left-
    handed system).

    @param eye The world location of the eye - the
               origin of the view coordinate system
    @param center The world location of the point
                  the camera is looking at or the eye
                  is pointed toward
    @param up A vector giving an indication of the
              desired vertical direction - where the
              top of the camera is pointing
    @return The transformation matrix
  */
  public static Matrix viewingTransform(
                         Vector3 eye,
                         Vector3 center,
                         Vector3 up)
  {
    Vector3 F = Vector3.Difference(center, eye);
    Vector3 f = F.makeNormalized();
    Vector3 upp = up.makeNormalized();
    Vector3 s = Vector3.crossProduct(f, upp);
    Vector3 u = Vector3.crossProduct(s, f);
    Vector3 sp = s.makeNormalized();
    Vector3 uprime = u.makeNormalized();

    Matrix m = new Matrix();
    m.body[0][0] = sp.body[0];
    m.body[0][1] = sp.body[1];
    m.body[0][2] = sp.body[2];
    m.body[1][0] = uprime.body[0];
    m.body[1][1] = uprime.body[1];
    m.body[1][2] = uprime.body[2];
    m.body[2][0] = f.body[0];
    m.body[2][1] = f.body[1];
    m.body[2][2] = f.body[2];

    Matrix t = Matrix.translationMatrix(-eye.body[0], -eye.body[1], -eye.body[2]);

    return Matrix.multiply(m, t);
  }

  /**
    Returns a matrix which will convert from view coordinates
    (a left-handed system) to world coordinates a right-handed
    system). This takes advantage of the fact that the viewing
    transform (vt) has a special structure - it is the product
    of a pure rotation (which can be inverted by transposing it)
    and a translation (which can be inverted by negating the
    translation amounts). The desired invers transform is the
    product of these inverses in the reverse order.

    @param vt The world coordinate to view coordinate transform
    @param eye The world location of the eye - the
               origin of the view coordinate system
    @return The inverse transformation matrix
  */
  public static Matrix inverseViewingTransform(Matrix vt, Vector3 eye)
  {
    Matrix m = new Matrix();

    for ( int i = 0; i < 3; i++ )
      for ( int j = 0; j < 3; j++ )
        m.body[i][j] = vt.body[j][i];

    Matrix t =
      Matrix.translationMatrix(eye.body[0], eye.body[1], eye.body[2]);

    return Matrix.multiply(t, m);

  }

}


