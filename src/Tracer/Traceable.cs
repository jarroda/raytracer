using System.Numerics;

namespace Tracer
{    
    public abstract class Traceable
    {
        //private MatrixPair _model = new MatrixPair();
        public string Name { get; set; }
        public Vector3 BaseColor { get; set; } = new Vector3(1, 1, 1);
        public Vector3 SpecularColor { get; set; } = new Vector3(1, 1, 1);
        public float SpectularExponent { get; set; } = 0;

        public Ray GetLocalRay(Ray ray)
        {
            var worldOrigin = ray.Origin;
            var worldPoint = ray.PointAt(1);
            var localOrigin = Vector3.Transform(worldOrigin, Matrix4x4.Identity);
            var localPoint = Vector3.Transform(worldPoint, Matrix4x4.Identity);
            
            return new Ray(localOrigin, localPoint);
        }

        public abstract float[] Intersections(Ray ray);
    }
}