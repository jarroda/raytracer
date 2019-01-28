using System.Numerics;
using System.Runtime.InteropServices;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    [StructLayout(LayoutKind.Sequential)]
    public struct Camera
    {
        public Vector3 Origin;
        public Vector3 LowerLeftCorner;
        public Vector3 Horizontal;
        public Vector3 Vertical;

        public static Camera Default = new Camera
        {
            Origin = new Vector3(0, 0, 0),
            LowerLeftCorner = new Vector3(-2, -1, -1),
            Horizontal = new Vector3(4, 0, 0),
            Vertical = new Vector3(0, 2, 0),
        };


        public Camera(Vector3 origin, Vector3 lowerLeftCorner, Vector3 horizontal, Vector3 vertical)
        {
            Origin = origin;
            LowerLeftCorner = lowerLeftCorner;
            Horizontal = horizontal;
            Vertical = vertical;
        }

        public Ray GetRay(float u, float v)
            => Ray.Create(Origin, LowerLeftCorner + u * Horizontal + v * Vertical - Origin);
    }
}