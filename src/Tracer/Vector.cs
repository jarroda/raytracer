using System;
using System.Linq;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public static class Vector
    {
        /// <summary>
        /// Returns the magnitude of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose magnitude is desired.</param>
        /// <returns>The magnitude of the input vector.</returns>
        public static double Magnitude(this Vector<double> vector)
            => Math.Sqrt(vector.Sum(v => v * v));

        /// <summary>
        /// Returns the square of the magnitude of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose magnitude squared is desired</param>
        /// <returns>The square of the magnitude of the input vector</returns>
        public static double MagnitudeSquared(this Vector<double> vector)
            => vector.Sum(v => v * v);

        /// <summary>
        /// Returns the normalized version of the input vector.
        /// </summary>
        /// <param name="vector">The vector whose normalized version is desired.</param>
        /// <returns>The normalized version of the input vector</returns>
        public static Vector<double> Normalize(this Vector<double> vector)
        {
            var m = vector.Magnitude();
            return Vector<double>.Build.DenseOfEnumerable(
                vector.Select(v => v / m)
            );
        }

        
    }
}