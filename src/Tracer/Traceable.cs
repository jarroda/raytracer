using System.Drawing;
using System.Numerics;

namespace Tracer
{    
    public abstract class Traceable
    {
        public MatrixPair Model { get; private set; } = new MatrixPair();
        public string Name { get; set; }

        public Color BaseColor { get; set; } = Color.White;
        public Color SpecularColor { get; set; } = Color.White;
        public double SpecularExponent { get; set; } = 0;

        public Ray GetLocalRay(Ray ray)
        {
            var worldOrigin = ray.Origin;
            var worldPoint = ray.PointAt(1);
            var localOrigin = Model.Inverse.Image(Vector.CreateVector3(worldOrigin.X, worldOrigin.Y, worldOrigin.Z));
            var localPoint = Model.Inverse.Image(Vector.CreateVector3(worldPoint.X, worldPoint.Y, worldPoint.Z));
            
            return Ray.CreateFromPoints(localOrigin.ToVector(), localPoint.ToVector());
        }

        public abstract float[] Intersections(Ray ray);
        public abstract Vector3 GetNormalAt(Vector3 p);
        public abstract Color GetBaseColorAt(Vector3 p);
    }
}