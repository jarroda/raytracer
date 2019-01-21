using System.Numerics;
using System.Runtime.InteropServices;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    [StructLayout(LayoutKind.Sequential)]
    public struct Ray
    {
        public Vector3 Origin;
        public Vector3 Direction;

        public static Ray Create(Vector3 origin, Vector3 direction)
        {
            return new Ray 
            { 
                Origin = origin,
                Direction = direction,
            };
        }

        public static Ray CreateFromPoints(Vector3 point1, Vector3 point2)
        {
            return new Ray
            {
                Origin = point1,
                Direction = Vector3.Normalize(point2 - point1)
            };
        }

        public Vector3 PointAt(float t)
            => Origin + Vector3.Multiply(Direction, t);
    }
}