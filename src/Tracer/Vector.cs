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
        public static double Magnitude(this VectorOld vector)
            => Math.Sqrt(vector.Sum(v => v * v));
        
        public static float Magnitude(this Vector3 vector)
            => (float)Math.Sqrt(vector.MagnitudeSquared());

        public static float MagnitudeSquared(this Vector3 vector)
            => (vector.X * vector.X) + (vector.Y * vector.Y) + (vector.Z * vector.Z);

        /// <summary>
        /// Returns the square of the magnitude of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose magnitude squared is desired</param>
        /// <returns>The square of the magnitude of the input vector</returns>
        public static double MagnitudeSquared(this VectorOld vector)
            => vector.Sum(v => v * v);

        /// <summary>
        /// Returns the cross product of the two input vectors in
        /// the order given.
        /// </summary>
        /// <param name="v1"> The vectors to be multiplied</param>
        /// <param name="v2"> The vectors to be multiplied</param>
        /// <returns>The cross product of the two input vectors</returns>
        public static VectorOld CrossProduct(this VectorOld v1, VectorOld v2)
            => CreateVector3(
                v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0]
            );

        /// <summary>
        /// Returns the term product of the two input vectors. This is
        /// primarliy useful with color calculations.
        /// </summary>
        /// <param name="v1">The vectors to be multiplied</param>
        /// <param name="v2">The vectors to be multiplied</param>
        /// <returns>The term by term product of the two input vectors</returns>
        public static Vector3 TermMultiple(this Vector3 v1, Vector3 v2)
            => new Vector3(
                v1.X * v2.X,
                v1.Y * v2.Y,
                v1.Z * v2.Z
            );
        public static VectorOld TermMultiple(this VectorOld v1, VectorOld v2)
            => CreateVector3(
                v1[0] * v2[0],
                v1[1] * v2[1],
                v1[2] * v2[2]
            );

        /// <summary>
        /// Returns the scalar multiple of a vector and a scalar.
        /// </summary>
        /// <param name="v">The vector</param>
        /// <param name="k">The scalar to multiply by</param>
        /// <returns>The scalar multiple of the input vector and scalar</returns>
        public static Vector3 ScalarMultiple(this Vector3 v, float k)
            => new Vector3(k * v.X, k * v.Y, k * v.Z);
        public static VectorOld ScalarMultiple(this VectorOld v, double k)
            => CreateVector3(k * v[0], k * v[1], k * v[2]);

        /// <summary>
        /// Returns the dot product of the two input vectors.
        /// </summary>
        /// <param name="v1">The vectors whose dot product is desired</param>
        /// <param name="v2">The vectors whose dot product is desired</param>
        /// <returns>The dot product of the two vectors</returns>
        public static double DotProduct(this VectorOld v1, VectorOld v2)
            => v1[0] * v2[0] +
                v1[1] * v2[1] +
                v1[2] * v2[2];

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

        public static void Clamp(this VectorOld vector)
        {
            for (var i = 0; i < vector.Count; i++)
            {
                if (vector[i] < 0)
                    vector[i] = 0;
                else if (vector[i] > 1)
                    vector[i] = 1;
            }
        }
    }
}