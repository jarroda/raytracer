using System.Numerics;
using System.Runtime.InteropServices;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    [StructLayout(LayoutKind.Sequential)]
    public struct HitRecord
    {
        public Vector3 Position;
        public Vector3 Normal;
        public Vector3 Color;
        public float T;

        public HitRecord(Vector3 position, float t, Vector3 normal)
        {
            Position = position;
            T = t;
            Normal = normal;
            Color = new Vector3();
        }
    }
}