using System;
using System.Numerics;
using MatrixOld = MathNet.Numerics.LinearAlgebra.Matrix<double>;

namespace Tracer
{
    public static class Matrix
    {
        /// <summary>
        /// Initializes a new matrix to the identity.
        /// </summary>
        /// <returns>The identity matrix</returns>
        public static MatrixOld Create()
            => MatrixOld.Build.DenseIdentity(4, 4);

        /// <summary>
        /// Returns the translation matrix for translating by tx units in the
        /// x-direction, ty units in the y-direction, and tz units in the
        /// z-direction.
        /// </summary>
        /// <param name="tx">Amount to translate in the x-direction</param>
        /// <param name="ty">Amount to translate in the y-direction</param>
        /// <param name="tz">Amount to translate in the z-direction</param>
        /// <returns>The translation matrix</returns>
        public static Matrix4x4 CreateTranslation(float tx, float ty, float tz)
        {
            var m = Matrix4x4.Identity;

            m.M14 = tx;
            m.M24 = ty;
            m.M34 = tz;

            return m;
        }

        /// <summary>
        /// Returns a shearing matrix which causes a shearing by a factor of a
        /// in the y-direction and b in the z-direction (and leaves x coordinates
        /// unchanged). This maps the line
        ///
        ///                    y = - a * x
        ///                    z = - b * x
        ///
        /// on to the x-axis in such a way that x coordinates are preserved.
        /// </summary>
        /// <param name="y">Shear factor for the y-direction</param>
        /// <param name="z">Shear factor for the z-direction</param>
        /// <returns>The shearing matrix</returns>
        public static MatrixOld CreateShearX(double y, double z)
        {
            var m = Create();

            m[1,0] = y;
            m[2,0] = z;

            return m;
        }

        /// <summary>
        /// Returns a shearing matrix which causes a shearing by a factor of a
        /// in the x-direction and b in the z-direction (and leaves y coordinates
        /// unchanged). This maps the line
        ///
        ///      x = - a * y
        ///      z = - b * y
        ///
        /// on to the y-axis in such a way that y coordinates are preserved.
        /// </summary>
        /// <param name="x">Shear factor for the x-direction</param>
        /// <param name="z">Shear factor for the z-direction</param>
        /// <returns>The shearing matrix</returns>
        public static MatrixOld CreateShearY(double x, double z)
        {
            var m = Create();

            m[0,1] = x;
            m[2,1] = z;

            return m;
        }

        /// <summary>
        /// Returns a shearing matrix which causes a shearing by a factor of a
        /// in the x-direction and b in the y-direction (and leaves z coordinates
        /// unchanged). This maps the line
        ///
        ///        x = - a * z
        ///        y = - b * z
        ///
        /// on to the z-axis in such a way that z coordinates are preserved.
        /// </summary>
        /// <param name="x">Shear factor for the x-direction</param>
        /// <param name="y">Shear factor for the y-direction</param>
        /// <returns>The shearing matrix</returns>
        public static MatrixOld CreateShearZ(double x, double y)
        {
            var m = Create();

            m[0,2] = x;
            m[1,2] = y;

            return m;
        }

        /// <summary>
        /// Returns the scale matrix for scaling by sx units in the
        /// x-direction, sy units in the y-direction, and sz units in the
        /// z-direction.
        /// </summary>
        /// <param name="sx">Amount to scale in the x-direction</param>
        /// <param name="sy">Amount to scale in the y-direction</param>
        /// <param name="sz">Amount to scale in the z-direction</param>
        /// <returns>The scale matrix</returns>
        public static MatrixOld CreateScale(double sx, double sy, double sz)
        {
            var m = Create();

            m[0,0] = sx;
            m[1,1] = sy;
            m[2,2] = sz;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting about the origin.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static MatrixOld CreateReflectOrigin()
        {
            var m = Create();
            
            m[0,0] = -1;
            m[1,1] = -1;
            m[2,2] = -1;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting in the XY-plane.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static MatrixOld CreateReflectXY()
        {
            var m = Create();

            m[2,2] = -1;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting in the XZ-plane.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static MatrixOld CreateReflectXZ()
        {
            var m = Create();

            m[1,1] = -1;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting in the YZ-plane.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static MatrixOld CreateReflectYZ()
        {
            var m = Create();

            m[0,0] = -1;

            return m;
        }

        /// <summary>
        /// Returns a rotation matrix for rotating about the line from the
        /// origin through (x, y, z). The rotation is through an angle
        /// whose sine and cosine are input. A positive rotation is
        /// counterclockwise when viewed from the point (x, y, z) back
        /// toward the origin.
        /// </summary>
        /// <param name="s">The sine of the rotation angle</param>
        /// <param name="c">The cosine of the rotation angle</param>
        /// <param name="x">The x coordinate of point determining the rotation axis</param>
        /// <param name="y">The y coordinate of point determining the rotation axis</param>
        /// <param name="z">The z coordinate of point determining the rotation axis</param>
        /// <returns>The rotation matrix</returns>
        public static MatrixOld CreateRotationSC(double s, double c, double x, double y, double z)
        {
            var m = Create();
            double oneMinusC = 1 - c;

            m[0,0] = x * x * oneMinusC + c;
            m[0,1] = x * y * oneMinusC - z * s;
            m[0,2] = x * z * oneMinusC + y * s;
            m[1,0] = y * x * oneMinusC + z * s;
            m[1,1] = y * y * oneMinusC + c;
            m[1,2] = y * z * oneMinusC - x * s;
            m[2,0] = x * z * oneMinusC - y * s;
            m[2,1] = y * z * oneMinusC + x * s;
            m[2,2] = z * z * oneMinusC + c;

            return m;
        }

        /// <summary>
        /// Returns a rotation matrix for rotating about the line from the
        /// origin through (x, y, z). The rotation is through the angle
        /// theta (in radians). A positive rotation is counterclockwise when
        /// viewed from the point (x, y, z) back toward the origin.
        /// </summary>
        /// <param name="theta">The rotation angle (in radians)</param>
        /// <param name="x">The x coordinate of point determining the rotation axis</param>
        /// <param name="y">The y coordinate of point determining the rotation axis</param>
        /// <param name="z">The z coordinate of point determining the rotation axis</param>
        /// <returns>The rotation matrix</returns>
        public static MatrixOld CreateRotation(double theta, double x, double y, double z)
            => CreateRotationSC(Math.Sin(theta), Math.Cos(theta), x, y, z);

        /// <summary>
        /// Returns a rotation matrix for rotating about the line from the
        /// origin through (x, y, z). The rotation is through the angle
        /// theta (in radians). A positive rotation is counterclockwise when
        /// viewed from the point (x, y, z) back toward the origin.
        /// </summary>
        /// <param name="deg">The rotation angle (in degrees)</param>
        /// <param name="x">The x coordinate of point determining the rotation axis</param>
        /// <param name="y">The y coordinate of point determining the rotation axis</param>
        /// <param name="z">The z coordinate of point determining the rotation axis</param>
        /// <returns>The rotation matrix</returns>
        public static MatrixOld CreateRotationDeg(double deg, double x, double y, double z)
            => CreateRotation(MathNet.Numerics.Trig.DegreeToRadian(deg), x, y, z);

        public static Matrix4x4 Translate(this Matrix4x4 m, float tx, float ty, float tz)
        {
            m.M14 += tx;
            m.M24 += ty;
            m.M34 += tz;
            return m;
        }
            // => new System.Numerics.Matrix4x4(
            //     m.M11, m.M12, m.M13, m.M14 + tx,
            //     m.M21, m.M22, m.M23, m.M24 + ty,
            //     m.M31, m.M32, m.M33, m.M34 + tz,
            //     m.M41, m.M42, m.M43, m.M44
            // );

        public static MatrixOld Scale(this MatrixOld m, double sx, double sy, double sz)
            => m.Multiply(CreateScale(sx, sy, sz));

        public static MatrixOld RotateSC(this MatrixOld m, double s, double c, double x, double y, double z)
            => m.Multiply(CreateRotationSC(s, c, x, y, z));

        public static MatrixOld Rotate(this MatrixOld m, double theta, double x, double y, double z)
            => m.Multiply(CreateRotation(theta, x, y, z));

        public static MatrixOld RotateDeg(this MatrixOld m, double deg, double x, double y, double z)
            => m.Multiply(CreateRotationDeg(deg, x, y, z));

        public static MatrixOld ReflectOrigin(this MatrixOld m)
            => m.Multiply(CreateReflectOrigin());

        public static MatrixOld ReflectXY(this MatrixOld m)
            => m.Multiply(CreateReflectXY());
        
        public static MatrixOld ReflectXZ(this MatrixOld m)
            => m.Multiply(CreateReflectXZ());

        public static MatrixOld ReflectYZ(this MatrixOld m)
            => m.Multiply(CreateReflectYZ());

        public static MatrixOld ShearX(this MatrixOld m, double y, double z)
            => m.Multiply(CreateShearX(y, z));

        public static MatrixOld ShearY(this MatrixOld m, double x, double z)
            => m.Multiply(CreateShearY(x, z));

        public static MatrixOld ShearZ(this MatrixOld m, double x, double y)
            => m.Multiply(CreateShearZ(x, y));

        public static Vector3 Image(this Matrix4x4 m, Vector3 v)
        {
            return new Vector3(
                (m.M11 * v.X) + (m.M12 * v.Y) + (m.M13 * v.Z) + m.M14,
                (m.M21 * v.X) + (m.M22 * v.Y) + (m.M23 * v.Z) + m.M24,
                (m.M31 * v.X) + (m.M32 * v.Y) + (m.M33 * v.Z) + m.M34
            );
        }
    }
}