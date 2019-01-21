using System;
using System.Linq;
using System.Numerics;
using VectorOld = MathNet.Numerics.LinearAlgebra.Vector<double>;

namespace Tracer
{
    public static class Vector
    {
        public static VectorOld CreateVector3(double x, double y, double z)
            => VectorOld.Build.DenseOfArray(new double[] { x, y, z });

        /// <summary>
        /// Returns the magnitude of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose magnitude is desired.</param>
        /// <returns>The magnitude of the input vector.</returns>
        public static float Magnitude(this Vector3 vector)
            => (float)Math.Sqrt(vector.MagnitudeSquared());
        public static double Magnitude(this VectorOld vector)
            => Math.Sqrt(vector.Sum(v => v * v));
        

        /// <summary>
        /// Returns the square of the magnitude of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose magnitude squared is desired</param>
        /// <returns>The square of the magnitude of the input vector</returns>
        public static float MagnitudeSquared(this Vector3 vector)
            => (vector.X * vector.X) + (vector.Y * vector.Y) + (vector.Z * vector.Z);

        /// <summary>
        /// Returns the normalized version of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose normalized version is desired.</param>
        /// <returns>The normalized version of the input vector</returns>
        public static VectorOld Normalize(this VectorOld vector)
        {
            var m = vector.Magnitude();
            return VectorOld.Build.DenseOfEnumerable(
                vector.Select(v => v / m)
            );
        }

        /// <summary>
        /// Clamps the entires of the current vector to the range 0 - 1
        /// </summary>
        /// <param name="vector">The vector to clamp</param>
        public static Vector3 Clamp(this Vector3 vector)
            => new Vector3(
                vector.X > 1 ? 1 : (vector.X < 0 ? 0 : vector.X),
                vector.Y > 1 ? 1 : (vector.Y < 0 ? 0 : vector.Y),
                vector.Z > 1 ? 1 : (vector.Z < 0 ? 0 : vector.Z)
            );
    }
}