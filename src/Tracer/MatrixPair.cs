using System;
using System.Numerics;
using MatrixOld = MathNet.Numerics.LinearAlgebra.Matrix<double>;

namespace Tracer
{    
    public class MatrixPair
    {
        private Matrix4x4 _origin;
        private Matrix4x4 _inverse;

        /// <summary>
        /// Initializes a new matrix pair to the identity.
        /// </summary>
        public MatrixPair()
        {
            _origin = Matrix4x4.Identity;
            _inverse = Matrix4x4.Identity;
        }

        /// <summary>
        /// Initializes a new matrix pair using provivded origin and inverse matrices.
        /// </summary>
        /// <param name="origin">The original matrix</param>
        /// <param name="inverse">The inverse of the original matrix</param>
        public MatrixPair(Matrix4x4 origin, Matrix4x4 inverse)
        {
            _origin = origin;
            _inverse = inverse;
        }

        /// <summary>
        /// Translates the current matrix by tx units in the x-direction,
        /// ty units in the y-direction, and tz units in the z-direction.
        /// Equivalent to left multiplying by a translation matrix.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="tx">Amount to translate in the x-direction</param>
        /// <param name="ty">Amount to translate in the y-direction</param>
        /// <param name="tz">Amount to translate in the z-direction</param>
        public void Translate(float tx, float ty, float tz)
        {
            _origin = _origin.Translate(tx, ty, tz);
            _inverse = _inverse.Translate(tx, ty, tz);
        }

        /// <summary>
        /// Scales the current matrix by sx units in the x-direction,
        /// sy units in the y-direction, and sz units in the z-direction.
        /// Equivalent to left multiplying by a scale matrix.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="sx">Amount to scale in the x-direction</param>
        /// <param name="sy">Amount to scale in the y-direction</param>
        /// <param name="sz">Amount to scale in the z-direction</param>
        // public void Scale(double sx, double sy, double sz)
        //     => Apply(m => m.Scale(sx, sy, sz));

        /// <summary>
        /// Rotates the current matrix about the line from the
        /// origin through (x, y, z). The rotation is through an angle
        /// whose sine and cosine are given. A positive rotation is
        /// counterclockwise when viewed from the point (x, y, z)
        /// back toward the origin.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="s">The sine of the rotation angle</param>
        /// <param name="c">The cosine of the rotation angle</param>
        /// <param name="x">Coordinate of x point determining the rotation axis</param>
        /// <param name="y">Coordinate of y point determining the rotation axis</param>
        /// <param name="z">Coordinate of z point determining the rotation axis</param>
        // public void RotateSC(double s, double c, double x, double y, double z)
        //     => Apply(m => m.RotateSC(s, c, x, y, z));

        /// <summary>
        /// Rotates the current matrix about the line from the
        /// origin through (x, y, z). The rotation is through the angle
        /// theta (in radians). A positive rotation is counterclockwise
        /// when viewed from the point (x, y, z) back toward the origin.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="theta">The rotation angle (in radians)</param>
        /// <param name="x">Coordinate of x point determining the rotation axis</param>
        /// <param name="y">Coordinate of y point determining the rotation axis</param>
        /// <param name="z">Coordinate of z point determining the rotation axis</param>
        // public void Rotate(double theta, double x, double y, double z)
        //     => Apply(m => m.Rotate(theta, x, y, z));

        /// <summary>
        /// Rotates the current matrix about the line from the
        /// origin through (x, y, z). The rotation is through the angle
        /// deg (in degrees). A positive rotation is counterclockwise
        /// when viewed from the point (x, y, z) back toward the origin.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="deg">The rotation angle (in degrees)</param>
        /// <param name="x">Coordinate of x point determining the rotation axis</param>
        /// <param name="y">Coordinate of y point determining the rotation axis</param>
        /// <param name="z">Coordinate of z point determining the rotation axis</param>
        // public void RotateDeg(double deg, double x, double y, double z)
        //     => Apply(m => m.RotateDeg(deg, x, y, z));

        /// <summary>
        /// Reflects the current matrix about the XY-plane.
        /// Keeps the inverse in sync.
        /// </summary>
        // public void ReflectXY()
        //     => Apply(m => m.ReflectXY());

        /// <summary>
        /// Reflects the current matrix about the XZ-plane.
        /// Keeps the inverse in sync.
        /// </summary>
        // public void ReflectXZ()
        //     => Apply(m => m.ReflectXZ());

        /// <summary>
        /// Reflects the current matrix in the YZ-plane.
        /// Keeps the inverse in sync.
        /// </summary>
        // public void ReflectYZ()
        //     => Apply(m => m.ReflectYZ());

        /// <summary>
        /// Reflects the current matrix about the origin.
        /// Keeps the inverse in sync.
        /// </summary>
        // public void ReflectOrigin()
        //     => Apply(m => m.ReflectOrigin());

        /// <summary>
        /// Shears the current matrix by a factor of a in the y-direction and
        /// b in the z-direction (leaving x coordinates unchanged). This maps
        /// the line
        ///
        ///     y = - a * x
        ///     z = - b * x
        ///
        /// on to the x-axis in such a way that x coordinates are preserved.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="y">Shear factor for the y-direction</param>
        /// <param name="z">Shear factor for the z-direction</param>
        // public void ShearX(double y, double z)
        //     => Apply(m => m.ShearX(y, z));

        /// <summary>
        /// Shears the current matrix by a factor of a in the x-direction and
        /// b in the z-direction (leaving y coordinates unchanged). This maps
        /// the line
        /// 
        ///     x = - a * y
        ///     z = - b * y
        /// 
        /// on to the y-axis in such a way that y coordinates are preserved.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="x">Shear factor for the x-direction</param>
        /// <param name="z">Shear factor for the z-direction</param>
        // public void ShearY(double x, double z)
        //     => Apply(m => m.ShearY(x, z));

        /// <summary>
        /// Shears the current matrix by a factor of a in the x-direction and
        /// b in the y-direction (leaving z coordinates unchanged). This maps
        /// the line
        /// 
        ///     x = - a * z
        ///     y = - b * z
        /// 
        /// on to the z-axis in such a way that z coordinates are preserved.
        /// Keeps the inverse in sync.
        /// </summary>
        /// <param name="x">Shear factor for the x-direction</param>
        /// <param name="y">Shear factor for the y-direction</param>
        // public void ShearZ(double x, double y)
        //     => Apply(m => m.ShearZ(x, y));

        /// <summary>
        /// Returns the result of applying the inverse of the
        /// current matrix to the given vector.
        /// </summary>
        /// <param name="vector">The vector whose image is desired</param>
        /// <returns>The inverse image of the vector under the current matrix</returns>
        public Vector3 InverseImage(Vector3 vector)
            => Inverse.Image(vector);

        /// <summary>
        /// Transforms the given normal vector via the current matrix. This
        /// uses the transpose of the inverse of the current matrix.
        /// </summary>
        /// <param name="vector">A normal vector to be transformed</param>
        /// <returns>The transformed normal</returns>
        public Vector3 TransformNormal(Vector3 v)
        {
            return Vector3.Normalize(new Vector3(
                (Inverse.M11 * v.X) + (Inverse.M12 * v.Y) + (Inverse.M13 * v.Z),
                (Inverse.M21 * v.X) + (Inverse.M22 * v.Y) + (Inverse.M23 * v.Z),
                (Inverse.M31 * v.X) + (Inverse.M32 * v.Y) + (Inverse.M33 * v.Z)
            ));
        }

        private void Apply(Func<Matrix4x4, Matrix4x4> fun)
        {
            _origin = fun(_origin);
            _inverse = fun(_inverse);
        }

        public Matrix4x4 Origin => _origin;
        public Matrix4x4 Inverse => _inverse;

        /// <summary>
        /// Returns the product of the two input matrices,
        /// in the order given. Keeps the inverses in sync
        /// by multiplying them in the reverse order.
        /// </summary>
        /// <param name="m1">The matrix pair to multiply</param>
        /// <param name="m2">The matrix pair to multiply</param>
        /// <returns>The product of the matrix pairs</returns>
        public static MatrixPair Multiply(MatrixPair m1, MatrixPair m2)
        {
            return new MatrixPair(
                Matrix4x4.Multiply(m1.Origin, m2.Origin),
                Matrix4x4.Multiply(m1.Inverse, m2.Inverse)
            );
        }
    }
}