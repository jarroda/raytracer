using System.Drawing;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public abstract class Traceable
    {
        public MatrixPair Model { get; private set; } = new MatrixPair();
        public string Name { get; set; }

        public Vector<double> BaseColor { get; set; } 
            = Vector<double>.Build.DenseOfArray(new double[] { 1, 1, 1 });
        public Vector<double> SpecularColor { get; set; } 
            = Vector<double>.Build.DenseOfArray(new double[] { 1, 1, 1 });
        public double SpectularExponent { get; set; } = 0;

        public Ray GetLocalRay(Ray ray)
        {
            var worldOrigin = ray.Origin;
            var worldPoint = ray.PointAt(1);
            var localOrigin = Model.Inverse.Image(Vector.CreateVector3(worldOrigin.X, worldOrigin.Y, worldOrigin.Z));
            var localPoint = Model.Inverse.Image(Vector.CreateVector3(worldPoint.X, worldPoint.Y, worldPoint.Z));
            
            return Ray.CreateFromPoints(localOrigin.ToVector(), localPoint.ToVector());
        }

        public abstract double[] Intersections(Ray ray);
        public abstract Vector<double> GetNormalAt(Vector<double> p);
        public abstract Vector<double> GetBaseColorAt(Vector<double> p);
    }
}