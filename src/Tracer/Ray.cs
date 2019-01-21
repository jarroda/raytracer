

using System.Numerics;
using System.Runtime.InteropServices;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    // public class Ray
    // {
    //     public Ray(Vector<double> point1, Vector<double> point2)
    //     {
    //         SetPoints(point1, point2);
    //     }

    //     public void SetPoints(Vector<double> point1, Vector<double> point2)
    //     {
    //         Origin = point1;
    //         Direction = point2.Subtract(point1).Normalize();
    //     }

    //     public void SetPointDirection(Vector<double> point, Vector<double> direction)
    //     {
    //         Origin = point;
    //         Direction = direction.Normalize();
    //     }

    //     public Vector<double> PointAt(double t)
    //         => Origin.Add(Direction.Multiply(t));

    //     public Vector<double> Origin { get; private set; }
    //         = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 0 });

    //     public Vector<double> Direction { get; private set; }
    //         = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 1 });
    // }

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

        // public void SetPointDirection(Vector<double> point, Vector<double> direction)
        // {
        //     Origin = point;
        //     Direction = direction.Normalize();
        // }

        public Vector3 PointAt(float t)
            => Origin + Vector3.Multiply(Direction, t);

        // public Vector<double> Origin { get; private set; }
        //     = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 0 });

        // public Vector<double> Direction { get; private set; }
        //     = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 1 });
    }
}