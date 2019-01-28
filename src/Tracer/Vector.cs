using System;
using System.Linq;
using System.Numerics;
using VectorOld = MathNet.Numerics.LinearAlgebra.Vector<double>;

namespace Tracer
{
    public static class Vector
    {
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

        public static Vector3 UnitVector(this Vector3 vector)
            => vector / vector.Length();
    }
}