using System;
using MathNet.Numerics;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public static class Matrix
    {
        /// <summary>
        /// Initializes a new matrix to the identity.
        /// </summary>
        /// <returns>The identity matrix</returns>
        public static Matrix<double> Create()
            => Matrix<double>.Build.DenseIdentity(4, 4);

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
        public static Matrix<double> CreateShearX(double y, double z)
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
        public static Matrix<double> CreateShearY(double x, double z)
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
        public static Matrix<double> CreateShearZ(double x, double y)
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
        public static Matrix<double> CreateScale(double sx, double sy, double sz)
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
        public static Matrix<double> CreateReflectOrigin()
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
        public static Matrix<double> CreateReflectXY()
        {
            var m = Create();

            m[2,2] = -1;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting in the XZ-plane.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static Matrix<double> CreateReflectXZ()
        {
            var m = Create();

            m[1,1] = -1;

            return m;
        }

        /// <summary>
        /// Returns the reflection matrix for reflecting in the YZ-plane.
        /// </summary>
        /// <returns>The reflection matrix</returns>
        public static Matrix<double> CreateReflectYZ()
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
        public static Matrix<double> CreateRotationSC(double s, double c, double x, double y, double z)
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
        public static Matrix<double> CreateRotation(double theta, double x, double y, double z)
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
        public static Matrix<double> CreateRotationDeg(double deg, double x, double y, double z)
            => CreateRotation(Trig.DegreeToRadian(deg), x, y, z);

        public static Matrix<double> Translate(this Matrix<double> m, double tx, double ty, double tz)
        {
            m[0,3] += tx;
            m[1,3] += ty;
            m[2,3] += tz;
            return m;
        }

        public static Matrix<double> Scale(this Matrix<double> m, double sx, double sy, double sz)
            => m.Multiply(CreateScale(sx, sy, sz));

        public static Matrix<double> RotateSC(this Matrix<double> m, double s, double c, double x, double y, double z)
            => m.Multiply(CreateRotationSC(s, c, x, y, z));

        public static Matrix<double> Rotate(this Matrix<double> m, double theta, double x, double y, double z)
            => m.Multiply(CreateRotation(theta, x, y, z));

        public static Matrix<double> RotateDeg(this Matrix<double> m, double deg, double x, double y, double z)
            => m.Multiply(CreateRotationDeg(deg, x, y, z));

        public static Matrix<double> ReflectOrigin(this Matrix<double> m)
            => m.Multiply(CreateReflectOrigin());

        public static Matrix<double> ReflectXY(this Matrix<double> m)
            => m.Multiply(CreateReflectXY());
        
        public static Matrix<double> ReflectXZ(this Matrix<double> m)
            => m.Multiply(CreateReflectXZ());

        public static Matrix<double> ReflectYZ(this Matrix<double> m)
            => m.Multiply(CreateReflectYZ());

        public static Matrix<double> ShearX(this Matrix<double> m, double y, double z)
            => m.Multiply(CreateShearX(y, z));

        public static Matrix<double> ShearY(this Matrix<double> m, double x, double z)
            => m.Multiply(CreateShearY(x, z));

        public static Matrix<double> ShearZ(this Matrix<double> m, double x, double y)
            => m.Multiply(CreateShearZ(x, y));

        public static Vector<double> Image(this Matrix<double> m, Vector<double> vector)
        {
            var result = Vector<double>.Build.Dense(3);

            for (var i = 0; i < 3; i++)
            {
                result[i] = 0;
                for (var j = 0; j < 3; j++)
                {
                    result[i] += m[i,j] * vector[j];
                }
                result[i] += m[i,3];
            }
            return result;
        }

        public static Matrix<double> Multiply(Matrix<double> m1, Matrix<double> m2)
        {
            var m = Matrix.Create();

            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                {
                    m[i,j] = 0;
                    for (int k = 0; k < 4; k++)
                        m[i,j] += m1[i,k] * m2[k,j];
                }
            return m;
        }
    }
}